package dotnet.sort.presentation.common.sound

actual class SoundEffectPlayer {
    actual fun play(effect: SoundEffect, volume: Float) {
        // No-op for now (web audio can be added later).
    }
}
