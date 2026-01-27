package dotnet.sort.presentation.common.sound

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.math.sin

actual class SoundEffectPlayer actual constructor() {
    actual fun playCompare(value: Int, volume: Float) {
        playTone(
            frequencyHz = mapValueToFrequency(value),
            durationMs = 60,
            volume = volume,
        )
    }

    actual fun playSwap(value: Int, volume: Float) {
        playTone(
            frequencyHz = mapValueToFrequency(value) * 1.25,
            durationMs = 100,
            volume = volume,
        )
    }

    actual fun dispose() = Unit

    private fun playTone(frequencyHz: Double, durationMs: Int, volume: Float) {
        if (volume <= 0f) return
        val safeVolume = volume.coerceIn(0f, 1f)
        thread(start = true, isDaemon = true) {
            val sampleRate = 44100
            val numSamples = (durationMs * sampleRate) / 1000
            val buffer = ByteArray(numSamples * 2)
            val amplitude = (Short.MAX_VALUE * safeVolume).toInt()
            for (i in 0 until numSamples) {
                val angle = 2.0 * PI * i * frequencyHz / sampleRate
                val sample = (sin(angle) * amplitude).toInt().toShort()
                val index = i * 2
                buffer[index] = (sample.toInt() and 0xFF).toByte()
                buffer[index + 1] = ((sample.toInt() shr 8) and 0xFF).toByte()
            }

            val format = AudioFormat(sampleRate.toFloat(), 16, 1, true, false)
            AudioSystem.getSourceDataLine(format).use { line ->
                line.open(format)
                line.start()
                line.write(buffer, 0, buffer.size)
                line.drain()
                line.stop()
            }
        }
    }

    private fun mapValueToFrequency(value: Int): Double {
        val clamped = value.coerceIn(1, 100)
        val ratio = (clamped - 1) / 99.0
        return 220.0 + (660.0 * ratio)
    }
}
