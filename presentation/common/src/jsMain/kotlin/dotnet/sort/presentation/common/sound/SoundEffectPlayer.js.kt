package dotnet.sort.presentation.common.sound

/**
 * JS向けのサウンドエフェクトプレイヤー実装。
 * Web Audio APIを使用してビープ音を生成します。
 */
actual class SoundEffectPlayer {
    actual fun play(effect: SoundEffect, volume: Float) {
        try {
            playBeep(effect.frequency.toDouble(), effect.duration.toDouble(), volume.toDouble())
        } catch (_: Throwable) {
            // サウンド再生に失敗した場合は無視
        }
    }

    actual fun playWithValue(value: Int, maxValue: Int, volume: Float, duration: Int) {
        if (volume <= 0f || maxValue <= 0) return
        try {
            // 値を200Hz〜2000Hzの範囲にマッピング
            val minFreq = 200
            val maxFreq = 2000
            val frequency = minFreq + ((maxFreq - minFreq) * value / maxValue)
            playBeep(frequency.toDouble(), duration.toDouble(), volume.toDouble())
        } catch (_: Throwable) {
            // サウンド再生に失敗した場合は無視
        }
    }
}

private fun playBeep(frequency: Double, duration: Double, volume: Double) {
    js("""
        (function() {
            try {
                var AudioContext = window.AudioContext || window.webkitAudioContext;
                if (!AudioContext) return;
                var ctx = new AudioContext();
                var oscillator = ctx.createOscillator();
                var gainNode = ctx.createGain();
                oscillator.connect(gainNode);
                gainNode.connect(ctx.destination);
                oscillator.frequency.value = frequency;
                oscillator.type = 'sine';
                gainNode.gain.value = volume * 0.3;
                oscillator.start();
                setTimeout(function() {
                    oscillator.stop();
                    ctx.close();
                }, duration);
            } catch(e) {}
        })()
    """)
}
