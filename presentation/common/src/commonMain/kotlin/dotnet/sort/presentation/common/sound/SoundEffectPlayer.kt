package dotnet.sort.presentation.common.sound

/**
 * サウンドエフェクトの再生を行うプラットフォーム実装。
 *
 * 比較音・交換音の2種類を提供します。
 */
expect class SoundEffectPlayer() {
    fun playCompare(value: Int, volume: Float)
    fun playSwap(value: Int, volume: Float)
    fun dispose()
}
