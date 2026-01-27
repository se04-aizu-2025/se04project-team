package dotnet.sort.presentation.common.sound

import java.awt.Toolkit

actual class SoundEffectPlayer {
    actual fun play(effect: SoundEffect, volume: Float) {
        if (volume <= 0f) return
        runCatching {
            Toolkit.getDefaultToolkit().beep()
        }
    }
}
