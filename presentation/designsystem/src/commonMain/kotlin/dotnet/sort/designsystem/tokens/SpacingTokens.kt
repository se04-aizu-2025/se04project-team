package dotnet.sort.designsystem.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * スペーシングトークン定義。
 *
 * 一貫したスペーシングスケールを提供します。
 * 4px ベースのスケーリングシステムを採用しています。
 */
object SpacingTokens {
    // === Base Spacing Scale ===
    /** 0dp - なし */
    val None: Dp = 0.dp

    /** 2dp - 極小 (XXS) */
    val XXS: Dp = 2.dp

    /** 4dp - 小 (XS) */
    val XS: Dp = 4.dp

    /** 8dp - 小中 (S) */
    val S: Dp = 8.dp

    /** 12dp - 中小 (SM) */
    val SM: Dp = 12.dp

    /** 16dp - 中 (M) */
    val M: Dp = 16.dp

    /** 20dp - 中大 (ML) */
    val ML: Dp = 20.dp

    /** 24dp - 大 (L) */
    val L: Dp = 24.dp

    /** 32dp - 極大 (XL) */
    val XL: Dp = 32.dp

    /** 48dp - 超大 (XXL) */
    val XXL: Dp = 48.dp

    /** 64dp - 最大 (XXXL) */
    val XXXL: Dp = 64.dp

    // === Component Specific ===

    /** バー間のスペース */
    val BarGap: Dp = 1.dp

    /** パネルのパディング */
    val PanelPadding: Dp = 16.dp

    /** カードのパディング */
    val CardPadding: Dp = 12.dp

    /** ボタンの水平パディング */
    val ButtonPaddingHorizontal: Dp = 16.dp

    /** ボタンの垂直パディング */
    val ButtonPaddingVertical: Dp = 8.dp

    /** アイコンとテキストの間隔 */
    val IconTextGap: Dp = 8.dp

    /** セクション間のスペース */
    val SectionGap: Dp = 24.dp

    /** フローティングトップバーの余白 */
    val FloatingTopBarInset: Dp = 80.dp

    /** フローティングボトムバーの余白 */
    val FloatingBottomBarInset: Dp = 96.dp
}
