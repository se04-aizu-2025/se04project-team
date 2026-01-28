package dotnet.sort.presentation.common.sound

enum class SoundEffect(val frequency: Int, val duration: Int) {
    COMPARE(frequency = 440, duration = 50),
    SWAP(frequency = 880, duration = 100),
    COMPLETE(frequency = 1320, duration = 300),
}
