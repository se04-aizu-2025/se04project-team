package dotnet.sort.designsystem.tokens

import androidx.compose.ui.graphics.Color

/**
 * カラートークン定義。
 *
 * Kotlinブランドカラーをベースにしたカラーパレットを提供します。
 * - Primary: Kotlin Purple 系
 * - Secondary: Kotlin Orange/Pink グラデーション系
 * - Visualization: ソート可視化用カラー
 */
object ColorTokens {

    // === Kotlin Brand Colors ===
    /** Kotlin Purple - メインカラー */
    val KotlinPurple = Color(0xFF7F52FF)

    /** Kotlin Orange - アクセントカラー */
    val KotlinOrange = Color(0xFFF88909)

    /** Kotlin Pink - グラデーション用 */
    val KotlinPink = Color(0xFFE44857)

    /** Kotlin Blue - グラデーション用 */
    val KotlinBlue = Color(0xFF087CFA)

    // === Semantic Colors (Light Theme) ===
    val Primary = KotlinPurple
    val PrimaryVariant = Color(0xFF5C3DCF)
    val Secondary = KotlinOrange
    val SecondaryVariant = Color(0xFFE57300)

    val Background = Color(0xFFFAFAFA)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF5F5F5)

    val Error = Color(0xFFB00020)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF1C1B1F)
    val OnSurface = Color(0xFF1C1B1F)
    val OnSurfaceVariant = Color(0xFF49454F) // Default M3 onSurfaceVariant
    val OnError = Color(0xFFFFFFFF)
    
    val PrimaryContainer = Color(0xFFEADDFF) // Lighter Purple
    val OnPrimaryContainer = Color(0xFF21005D) // Dark Purple

    // === Semantic Colors (Dark Theme) ===
    val PrimaryDark = Color(0xFFB794F6)
    val PrimaryVariantDark = Color(0xFF9B7FE6)
    val SecondaryDark = Color(0xFFFFB347)

    val BackgroundDark = Color(0xFF1C1B1F)
    val SurfaceDark = Color(0xFF2D2D30)
    val SurfaceVariantDark = Color(0xFF3C3C3F)

    val OnPrimaryDark = Color(0xFF1C1B1F)
    val OnSecondaryDark = Color(0xFF1C1B1F)
    val OnBackgroundDark = Color(0xFFE6E1E5)
    val OnSurfaceDark = Color(0xFFE6E1E5)
    val OnSurfaceVariantDark = Color(0xFFCAC4D0)
    
    val PrimaryContainerDark = Color(0xFF4F378B)
    val OnPrimaryContainerDark = Color(0xFFEADDFF)

    // === Visualization Colors (ソート可視化用) ===
    /** デフォルトのバー色 - Kotlin Blue 系 */
    val BarDefault = Color(0xFF5C9CE6)

    /** 比較中の要素 - Kotlin Orange */
    val BarComparing = KotlinOrange

    /** 交換中の要素 - Kotlin Pink/Red */
    val BarSwapping = KotlinPink

    /** ソート完了した要素 - Kotlin Green/Teal */
    val BarSorted = Color(0xFF4CAF50)

    /** ピボット要素 - Kotlin Purple */
    val BarPivot = KotlinPurple

    /** 選択中の最小/最大要素 */
    val BarSelected = Color(0xFFFFEB3B)

    // === Gradient Support ===
    /** Kotlinロゴのグラデーション開始色 */
    val GradientStart = KotlinBlue

    /** Kotlinロゴのグラデーション中間色 */
    val GradientMiddle = KotlinPurple

    /** Kotlinロゴのグラデーション終了色 */
    val GradientEnd = KotlinOrange
}
