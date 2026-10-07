package com.trevornk.ramblr

import android.media.AudioFormat
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AudioFileDecoderTest {
    @Test fun `stereo PCM is mixed to mono without losing frames`() {
        val file = File.createTempFile("ramblr-normalizer-", ".pcm")
        try {
            FileOutputStream(file).use { output ->
                val normalizer = AudioFileDecoder.PcmNormalizer(
                    output, 16000, 2, AudioFormat.ENCODING_PCM_16BIT
                )
                val source = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
                    .putShort(1000).putShort((-1000).toShort())
                    .putShort(2000).putShort(2000)
                source.flip()
                normalizer.accept(source)
                normalizer.finish()
            }
            assertArrayEquals(shortArrayOf(0, 2000), file.readSamples())
        } finally {
            file.delete()
        }
    }

    @Test fun `48 kHz PCM is reduced to 16 kHz`() {
        val file = File.createTempFile("ramblr-resample-", ".pcm")
        try {
            FileOutputStream(file).use { output ->
                val normalizer = AudioFileDecoder.PcmNormalizer(
                    output, 48000, 1, AudioFormat.ENCODING_PCM_16BIT
                )
                val source = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
                    .putShort(0).putShort(0).putShort(0).putShort(1000)
                source.flip()
                normalizer.accept(source)
                normalizer.finish()
            }
            assertArrayEquals(shortArrayOf(0, 1000), file.readSamples())
        } finally {
            file.delete()
        }
    }

    private fun File.readSamples(): ShortArray {
        val bytes = readBytes()
        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        return ShortArray(bytes.size / 2) { buffer.short }
    }
}
