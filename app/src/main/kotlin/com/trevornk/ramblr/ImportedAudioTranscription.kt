package com.trevornk.ramblr

import java.io.File
import java.util.concurrent.CancellationException

/** Decodes an imported PCM file in bounded pieces so meeting length does not set peak memory. */
object ImportedAudioTranscription {
    private const val SAMPLE_RATE = 16000
    private const val FALLBACK_CHUNK_SAMPLES = 15 * SAMPLE_RATE

    fun transcribe(
        transcriber: LocalTranscriber,
        pcmFile: File,
        vad: VadHandle?,
        cancelled: () -> Boolean,
        progress: (Int) -> Unit,
    ): String {
        val totalSamples = (pcmFile.length() / 2).coerceAtLeast(1)
        var processed = 0L
        val parts = ArrayList<String>()
        fun checkCancelled() { if (cancelled()) throw CancellationException("Audio import cancelled") }
        fun decode(samples: FloatArray) {
            checkCancelled()
            val text = transcriber.transcribe(samples, SAMPLE_RATE).trim()
            if (text.isNotEmpty()) parts += text
            checkCancelled()
        }

        if (vad != null) {
            val segmenter = SpeechSegmenter(vad)
            PcmFileBuffer.forEachChunk(pcmFile, LocalTranscriber.SEGMENT_READ_CHUNK_SAMPLES) { chunk ->
                checkCancelled()
                segmenter.accept(chunk, ::decode)
                processed += chunk.size
                progress((processed * 100 / totalSamples).toInt().coerceIn(0, 100))
            }
            checkCancelled()
            segmenter.finish(::decode)
        } else {
            // Safe fallback while the optional VAD model is downloading. A 15-second native
            // stream has bounded memory even for a multi-hour recording.
            PcmFileBuffer.forEachChunk(pcmFile, FALLBACK_CHUNK_SAMPLES) { chunk ->
                decode(chunk)
                processed += chunk.size
                progress((processed * 100 / totalSamples).toInt().coerceIn(0, 100))
            }
        }
        checkCancelled()
        return SegmentedTranscript.join(parts)
    }
}
