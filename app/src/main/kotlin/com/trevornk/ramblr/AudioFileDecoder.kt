package com.trevornk.ramblr

import android.content.Context
import android.media.AudioFormat
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.CancellationException
import kotlin.math.roundToInt

/** Converts a user-selected recording to the 16 kHz mono PCM consumed by LocalTranscriber. */
internal object AudioFileDecoder {
    private const val SAMPLE_RATE = 16000
    private const val MAX_INPUT_BYTES = 512L * 1024 * 1024
    private const val MAX_DURATION_SECONDS = 2 * 60 * 60

    fun decode(
        context: Context,
        uri: Uri,
        cancelled: () -> Boolean,
        progress: (Int) -> Unit,
    ): File {
        val source = File.createTempFile("ramblr-import-", ".audio", context.cacheDir)
        val pcm = File.createTempFile("ramblr-import-", ".pcm", context.cacheDir)
        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(source).use { output ->
                    val buffer = ByteArray(64 * 1024)
                    var total = 0L
                    while (true) {
                        if (cancelled()) throw CancellationException()
                        val n = input.read(buffer)
                        if (n < 0) break
                        total += n
                        require(total <= MAX_INPUT_BYTES) { "File is larger than 512 MB" }
                        output.write(buffer, 0, n)
                    }
                }
            } ?: error("Cannot open this audio file")
            if (source.length() == 0L) error("Audio file is empty")
            FileOutputStream(pcm).use { output ->
                if (isRiffWave(source)) decodeWave(source, output, cancelled, progress)
                else decodeWithAndroid(source, output, cancelled, progress)
            }
            require(pcm.length() >= 9600) { "No usable audio found" } // 300 ms
            return pcm
        } catch (error: Exception) {
            pcm.delete()
            throw error
        } finally {
            source.delete()
        }
    }

    private fun isRiffWave(file: File): Boolean = RandomAccessFile(file, "r").use { wav ->
        if (wav.length() < 12) return@use false
        val riff = wav.readFourCc()
        wav.readUnsignedIntLe() // RIFF container size
        riff == "RIFF" && wav.readFourCc() == "WAVE"
    }

    private fun decodeWave(
        file: File,
        output: FileOutputStream,
        cancelled: () -> Boolean,
        progress: (Int) -> Unit,
    ) {
        RandomAccessFile(file, "r").use { wav ->
            wav.seek(12)
            var encoding = 0
            var channels = 0
            var rate = 0
            var dataStart = -1L
            var dataLength = 0L
            while (wav.filePointer + 8 <= wav.length()) {
                val id = wav.readFourCc()
                val size = wav.readUnsignedIntLe()
                val start = wav.filePointer
                require(size <= wav.length() - start) { "Damaged WAV file" }
                if (id == "fmt ") {
                    require(size >= 16) { "Damaged WAV format" }
                    val kind = wav.readUnsignedShortLe()
                    channels = wav.readUnsignedShortLe()
                    rate = wav.readIntLe()
                    wav.skipBytes(6) // byte rate and block alignment
                    val bits = wav.readUnsignedShortLe()
                    encoding = when {
                        kind == 1 && bits == 16 -> AudioFormat.ENCODING_PCM_16BIT
                        kind == 3 && bits == 32 -> AudioFormat.ENCODING_PCM_FLOAT
                        else -> error("Unsupported WAV encoding")
                    }
                } else if (id == "data") {
                    dataStart = start
                    dataLength = size
                }
                wav.seek(start + size + size % 2)
                if (encoding != 0 && dataStart >= 0) break
            }
            require(encoding != 0 && dataStart >= 0) { "WAV has no audio track" }
            val normalizer = PcmNormalizer(output, rate, channels, encoding)
            wav.seek(dataStart)
            val frameBytes = channels * if (encoding == AudioFormat.ENCODING_PCM_FLOAT) 4 else 2
            val buffer = ByteArray((64 * 1024 / frameBytes) * frameBytes)
            var remaining = dataLength
            while (remaining > 0) {
                if (cancelled()) throw CancellationException()
                val n = wav.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
                if (n < 0) break
                normalizer.accept(ByteBuffer.wrap(buffer, 0, n))
                remaining -= n
                progress(((dataLength - remaining) * 100 / dataLength).toInt().coerceIn(0, 100))
            }
            normalizer.finish()
        }
    }

    private fun decodeWithAndroid(
        file: File,
        output: FileOutputStream,
        cancelled: () -> Boolean,
        progress: (Int) -> Unit,
    ) {
        val extractor = MediaExtractor()
        var codec: MediaCodec? = null
        var started = false
        try {
            extractor.setDataSource(file.absolutePath)
            val track = (0 until extractor.trackCount).firstOrNull {
                extractor.getTrackFormat(it).getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true
            } ?: error("No supported audio track found")
            extractor.selectTrack(track)
            val format = extractor.getTrackFormat(track)
            val duration = if (format.containsKey(MediaFormat.KEY_DURATION)) format.getLong(MediaFormat.KEY_DURATION) else 0L
            require(duration <= MAX_DURATION_SECONDS * 1_000_000L) { "Recording is longer than 2 hours" }
            val mime = format.getString(MediaFormat.KEY_MIME) ?: error("Unknown audio format")
            codec = MediaCodec.createDecoderByType(mime)
            codec.configure(format, null, null, 0)
            codec.start()
            started = true
            val info = MediaCodec.BufferInfo()
            var inputFinished = false
            var outputFinished = false
            var normalizer: PcmNormalizer? = null
            while (!outputFinished) {
                if (cancelled()) throw CancellationException()
                if (!inputFinished) {
                    val inputIndex = codec.dequeueInputBuffer(10_000)
                    if (inputIndex >= 0) {
                        val inputBuffer = codec.getInputBuffer(inputIndex) ?: error("Audio decoder input unavailable")
                        inputBuffer.clear()
                        val n = extractor.readSampleData(inputBuffer, 0)
                        if (n < 0) {
                            codec.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                            inputFinished = true
                        } else {
                            codec.queueInputBuffer(inputIndex, 0, n, extractor.sampleTime, 0)
                            extractor.advance()
                        }
                    }
                }
                when (val outputIndex = codec.dequeueOutputBuffer(info, 10_000)) {
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        check(normalizer == null) { "Audio format changed during decoding" }
                        val decoded = codec.outputFormat
                        val encoding = if (decoded.containsKey(MediaFormat.KEY_PCM_ENCODING))
                            decoded.getInteger(MediaFormat.KEY_PCM_ENCODING)
                        else AudioFormat.ENCODING_PCM_16BIT
                        normalizer = PcmNormalizer(
                            output,
                            decoded.getInteger(MediaFormat.KEY_SAMPLE_RATE),
                            decoded.getInteger(MediaFormat.KEY_CHANNEL_COUNT),
                            encoding,
                        )
                    }
                    in 0..Int.MAX_VALUE -> {
                        if (info.size > 0) {
                            val buffer = codec.getOutputBuffer(outputIndex) ?: error("Audio decoder output unavailable")
                            buffer.position(info.offset)
                            buffer.limit(info.offset + info.size)
                            if (normalizer == null) {
                                val decoded = codec.outputFormat
                                normalizer = PcmNormalizer(
                                    output,
                                    decoded.getInteger(MediaFormat.KEY_SAMPLE_RATE),
                                    decoded.getInteger(MediaFormat.KEY_CHANNEL_COUNT),
                                    if (decoded.containsKey(MediaFormat.KEY_PCM_ENCODING)) decoded.getInteger(MediaFormat.KEY_PCM_ENCODING)
                                    else AudioFormat.ENCODING_PCM_16BIT,
                                )
                            }
                            normalizer.accept(buffer)
                            if (duration > 0) progress((info.presentationTimeUs * 100 / duration).toInt().coerceIn(0, 100))
                        }
                        outputFinished = info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0
                        codec.releaseOutputBuffer(outputIndex, false)
                    }
                }
            }
            normalizer?.finish() ?: error("Audio decoder produced no samples")
        } finally {
            if (started) runCatching { codec?.stop() }
            codec?.release()
            extractor.release()
        }
    }

    private fun RandomAccessFile.readFourCc(): String = ByteArray(4).also { readFully(it) }.toString(Charsets.US_ASCII)
    private fun RandomAccessFile.readUnsignedShortLe(): Int = readUnsignedByte() or (readUnsignedByte() shl 8)
    private fun RandomAccessFile.readIntLe(): Int = readUnsignedShortLe() or (readUnsignedShortLe() shl 16)
    private fun RandomAccessFile.readUnsignedIntLe(): Long = readIntLe().toLong() and 0xffff_ffffL

    internal class PcmNormalizer(
        private val output: FileOutputStream,
        private val sourceRate: Int,
        private val channels: Int,
        private val encoding: Int,
    ) {
        private val bytesPerSample = when (encoding) {
            AudioFormat.ENCODING_PCM_16BIT -> 2
            AudioFormat.ENCODING_PCM_FLOAT -> 4
            else -> error("Unsupported decoded audio format")
        }
        private val frameBytes = bytesPerSample * channels
        private val pending = ByteArray(32)
        private var pendingSize = 0
        private val outputBuffer = ByteArray(8192)
        private var outputSize = 0
        private var sourceIndex = 0L
        private var nextOutputPosition = 0.0
        private var previous = 0f
        private var writtenSamples = 0L

        init {
            require(sourceRate in 8000..192000 && channels in 1..8) { "Unsupported audio channels or sample rate" }
        }

        fun accept(input: ByteBuffer) {
            input.order(ByteOrder.LITTLE_ENDIAN)
            while (input.hasRemaining()) {
                val n = minOf(frameBytes - pendingSize, input.remaining())
                input.get(pending, pendingSize, n)
                pendingSize += n
                if (pendingSize == frameBytes) {
                    var sum = 0f
                    repeat(channels) { channel ->
                        val offset = channel * bytesPerSample
                        val lo = pending[offset].toInt() and 0xff
                        val hi = pending[offset + 1].toInt() and 0xff
                        sum += if (encoding == AudioFormat.ENCODING_PCM_FLOAT) {
                            val bits = lo or (hi shl 8) or
                                ((pending[offset + 2].toInt() and 0xff) shl 16) or
                                ((pending[offset + 3].toInt() and 0xff) shl 24)
                            Float.fromBits(bits)
                        } else ((lo or (hi shl 8)).toShort().toFloat() / 32768f)
                    }
                    acceptSample(sum / channels)
                    pendingSize = 0
                }
            }
        }

        private fun acceptSample(current: Float) {
            val index = sourceIndex++
            if (index == 0L) {
                previous = current
                return
            }
            while (nextOutputPosition <= index.toDouble()) {
                val fraction = (nextOutputPosition - (index - 1)).coerceIn(0.0, 1.0)
                writeSample(previous + (current - previous) * fraction.toFloat())
                nextOutputPosition += sourceRate.toDouble() / SAMPLE_RATE
            }
            previous = current
        }

        private fun writeSample(value: Float) {
            require(writtenSamples < MAX_DURATION_SECONDS.toLong() * SAMPLE_RATE) { "Recording is longer than 2 hours" }
            val sample = (value.coerceIn(-1f, 1f) * 32768f).roundToInt().coerceIn(-32768, 32767)
            outputBuffer[outputSize++] = sample.toByte()
            outputBuffer[outputSize++] = (sample shr 8).toByte()
            if (outputSize == outputBuffer.size) flush()
            writtenSamples++
        }

        fun finish() {
            require(pendingSize == 0) { "Truncated audio sample" }
            flush()
        }

        private fun flush() {
            if (outputSize > 0) output.write(outputBuffer, 0, outputSize)
            outputSize = 0
        }
    }
}
