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

    // === Layout Specific ===

    /** ビジュアライザーの高さ (通常) */
    val VisualizerHeight: Dp = 300.dp

    /** ビジュアライザーの高さ (コンパクト) */
    val VisualizerHeightCompact: Dp = 280.dp

    /** チャートの高さ (小) */
    val ChartHeightSmall: Dp = 140.dp

    /** ランドスケープ判定幅 */
    val LandscapeBreakpoint: Dp = 600.dp

    /** サイドパネル幅 */
    val SidePanelWidth: Dp = 320.dp

    /** グリッドの最小セル幅 */
    val GridCellMinWidth: Dp = 180.dp

    /** ビジュアライザーの最小高さ */
    val VisualizerMinHeight: Dp = 240.dp

    /** ビジュアライザーの最大高さ */
    val VisualizerMaxHeight: Dp = 440.dp

    /** 角丸 (小) */
    val CornerRadiusSmall: Dp = 8.dp

    /** 角丸 (中) */
    val CornerRadiusMedium: Dp = 16.dp

    /** コード表示のボタン高さ */
    val CodeButtonHeight: Dp = 32.dp

    /** プログレスバーの高さ */
    val ProgressBarHeight: Dp = 8.dp

    // === Icon Sizes ===
    
    /** アイコンサイズ (小) */
    val IconSizeS: Dp = 16.dp
    
    /** アイコンサイズ (中) */
    val IconSizeM: Dp = 24.dp
    
    /** アイコンサイズ (大) */
    val IconSizeL: Dp = 32.dp
    
    /** アイコンサイズ (極大) */
    val IconSizeXL: Dp = 64.dp
}
