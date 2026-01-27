package dotnet.sort.presentation.common.sound

import kotlin.math.max
import kotlin.math.min

actual class SoundEffectPlayer actual constructor() {
    private val audioContext: dynamic = createAudioContext()

    actual fun playCompare(value: Int, volume: Float) {
        playTone(
            frequencyHz = mapValueToFrequency(value),
            durationMs = 60,
            volume = volume,
            waveform = "sine",
        )
    }

    actual fun playSwap(value: Int, volume: Float) {
        playTone(
            frequencyHz = mapValueToFrequency(value) * 1.25,
            durationMs = 100,
            volume = volume,
            waveform = "square",
        )
    }

    actual fun dispose() = Unit

    private fun playTone(
        frequencyHz: Double,
        durationMs: Int,
        volume: Float,
        waveform: String,
    ) {
        val ctx = audioContext ?: return
        if (volume <= 0f) return
        val safeVolume = max(0f, min(1f, volume))
        try {
            if (ctx.state == "suspended") {
                ctx.resume()
            }
            val oscillator = ctx.createOscillator()
            val gain = ctx.createGain()
            oscillator.type = waveform
            oscillator.frequency.value = frequencyHz
            gain.gain.value = safeVolume
            oscillator.connect(gain)
            gain.connect(ctx.destination)
            val now = ctx.currentTime
            oscillator.start(now)
            oscillator.stop(now + durationMs / 1000.0)
        } catch (_: dynamic) {
            // Ignore audio failures on unsupported environments.
        }
    }

    private fun mapValueToFrequency(value: Int): Double {
        val clamped = value.coerceIn(1, 100)
        val ratio = (clamped - 1) / 99.0
        return 220.0 + (660.0 * ratio)
    }

    private fun createAudioContext(): dynamic {
        val window = js("window")
        return when {
            window.AudioContext != undefined -> js("new window.AudioContext()")
            window.webkitAudioContext != undefined -> js("new window.webkitAudioContext()")
            else -> null
        }
    }
}
