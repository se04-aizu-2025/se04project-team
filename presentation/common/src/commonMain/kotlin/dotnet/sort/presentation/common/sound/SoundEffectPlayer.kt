package dotnet.sort.presentation.common.sound

expect class SoundEffectPlayer() {
    fun play(effect: SoundEffect, volume: Float)
}
