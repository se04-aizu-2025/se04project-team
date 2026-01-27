package dotnet.sort.presentation.common.sound

actual class SoundEffectPlayer actual constructor() {
    actual fun playCompare(value: Int, volume: Float) = Unit

    actual fun playSwap(value: Int, volume: Float) = Unit

    actual fun dispose() = Unit
}
