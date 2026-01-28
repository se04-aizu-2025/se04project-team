package dotnet.sort.presentation.common.sound

import java.awt.Toolkit
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import kotlin.math.PI
import kotlin.math.sin

actual class SoundEffectPlayer {
    actual fun play(effect: SoundEffect, volume: Float) {
        if (volume <= 0f) return
        playTone(effect.frequency, effect.duration, volume)
    }

    actual fun playWithValue(value: Int, maxValue: Int, volume: Float, duration: Int) {
        if (volume <= 0f || maxValue <= 0) return
        // 値を200Hz〜2000Hzの範囲にマッピング
        val minFreq = 200
        val maxFreq = 2000
        val frequency = minFreq + ((maxFreq - minFreq) * value / maxValue)
        playTone(frequency, duration, volume)
    }

    private fun playTone(frequency: Int, durationMs: Int, volume: Float) {
        runCatching {
            val sampleRate = 44100f
            val samples = (sampleRate * durationMs / 1000).toInt()
            val buffer = ByteArray(samples)

            for (i in 0 until samples) {
                val angle = 2.0 * PI * frequency * i / sampleRate
                buffer[i] = (sin(angle) * 127 * volume).toInt().toByte()
            }

            val format = AudioFormat(sampleRate, 8, 1, true, false)
            val line = AudioSystem.getSourceDataLine(format)
            line.open(format)
            line.start()
            line.write(buffer, 0, buffer.size)
            line.drain()
            line.close()
        }
    }
}
