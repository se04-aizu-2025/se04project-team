package dotnet.sort.designsystem.tokens

/**
 * アニメーショントークン定義。
 *
 * アニメーションの時間、遅延、イージングに関する定数を提供します。
 * ソート可視化のアニメーションで使用されます。
 */
object AnimationTokens {

    // === Duration (ms) ===
    /** 超高速アニメーション - 100ms */
    const val ExtraFastDuration: Int = 100

    /** 高速アニメーション - 150ms */
    const val FastDuration: Int = 150

    /** 通常アニメーション - 300ms */
    const val NormalDuration: Int = 300

    /** 低速アニメーション - 500ms */
    const val SlowDuration: Int = 500

    /** 超低速アニメーション - 800ms */
    const val ExtraSlowDuration: Int = 800

    // === Visualization Specific ===
    /** 可視化ステップ間のデフォルト遅延 (ms) */
    const val VisualizationDelay: Long = 50L

    /** 可視化の最小遅延 (ms) */
    const val VisualizationDelayMin: Long = 10L

    /** 可視化の最大遅延 (ms) */
    const val VisualizationDelayMax: Long = 500L

    /** バー高さ変更アニメーション時間 (ms) */
    const val BarHeightAnimationDuration: Int = 200

    /** バーカラー変更アニメーション時間 (ms) */
    const val BarColorAnimationDuration: Int = 150

    /** スワップアニメーション時間 (ms) */
    const val SwapAnimationDuration: Int = 300

    // === Speed Multipliers ===
    /** 最低速度 (0.25x) */
    const val SpeedMinMultiplier: Float = 0.25f

    /** 通常速度 (1.0x) */
    const val SpeedNormalMultiplier: Float = 1.0f

    /** 最高速度 (4.0x) */
    const val SpeedMaxMultiplier: Float = 4.0f

    /** 速度ステップ (0.25ずつ) */
    const val SpeedStep: Float = 0.25f

    // === Easing Names (for documentation/reference) ===
    /** 
     * 使用するイージング関数の参照用定数。
     * 実際のイージングはCompose AnimationSpecで適用します。
     */
    object EasingNames {
        const val LINEAR = "Linear"
        const val EASE_IN = "EaseIn"
        const val EASE_OUT = "EaseOut"
        const val EASE_IN_OUT = "EaseInOut"
        const val FAST_OUT_SLOW_IN = "FastOutSlowIn"
    }
}
