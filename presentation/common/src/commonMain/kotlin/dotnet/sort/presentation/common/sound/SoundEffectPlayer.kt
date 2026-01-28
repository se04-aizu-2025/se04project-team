package dotnet.sort.presentation.common.sound

expect class SoundEffectPlayer() {
    fun play(effect: SoundEffect, volume: Float)

    /**
     * 値に基づいた周波数でサウンドを再生する。
     * @param value 配列の値（音の高さに変換される）
     * @param maxValue 配列内の最大値（周波数範囲の正規化用）
     * @param volume 音量 (0.0〜1.0)
     * @param duration 再生時間（ミリ秒）
     */
    fun playWithValue(value: Int, maxValue: Int, volume: Float, duration: Int = 50)
}
