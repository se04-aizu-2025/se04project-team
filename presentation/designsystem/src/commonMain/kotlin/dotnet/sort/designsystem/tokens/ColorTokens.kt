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

    val Background = Color(0xFFFDF7FF) // Tinted with Purple (High lightness)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF5F5F5)

    val Error = Color(0xFFB00020)
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9800)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF1C1B1F)
    val OnSurface = Color(0xFF1C1B1F)
    val OnSurfaceVariant = Color(0xFF49454F) // Default M3 onSurfaceVariant
    val OnError = Color(0xFFFFFFFF)
    
    val PrimaryContainer = Color(0xFFEADDFF) // Lighter Purple
    val OnPrimaryContainer = Color(0xFF21005D) // Dark Purple

    // === Code Container Colors ===
    /** コードコンテナの背景色 */
    val CodeContainer = Color(0xFFF8F8F8)
    /** コードコンテナ上のテキスト色 */
    val OnCodeContainer = Color(0xFF1C1B1F)

    // === Semantic Colors (Dark Theme) ===
    val PrimaryDark = Color(0xFFB794F6)
    val PrimaryVariantDark = Color(0xFF9B7FE6)
    val SecondaryDark = Color(0xFFFFB347)

    val BackgroundDark = Color(0xFF141218) // Slightly tinted dark
    val SurfaceDark = Color(0xFF2D2D30)
    val SurfaceVariantDark = Color(0xFF3C3C3F)

    val OnPrimaryDark = Color(0xFF1C1B1F)
    val OnSecondaryDark = Color(0xFF1C1B1F)
    val OnBackgroundDark = Color(0xFFE6E1E5)
    val OnSurfaceDark = Color(0xFFE6E1E5)
    val OnSurfaceVariantDark = Color(0xFFCAC4D0)
    
    val PrimaryContainerDark = Color(0xFF4F378B)
    val OnPrimaryContainerDark = Color(0xFFEADDFF)

    // === Code Container Colors (Dark Theme) ===
    /** ダークテーマでのコードコンテナの背景色 */
    val CodeContainerDark = Color(0xFF1E1E1E)
    /** ダークテーマでのコードコンテナ上のテキスト色 */
    val OnCodeContainerDark = Color(0xFFE6E1E5)

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

    // === Visualization Presets (Ocean) ===
    val OceanDefault = Color(0xFF1976D2)
    val OceanComparing = Color(0xFF26C6DA)
    val OceanSwapping = Color(0xFFFF7043)
    val OceanSorted = Color(0xFF66BB6A)
    val OceanPivot = Color(0xFF3949AB)
    val OceanSelected = Color(0xFFFFF176)
    
    // Ocean Theme - Full Color Scheme
    val OceanPrimary = Color(0xFF0288D1)
    val OceanPrimaryVariant = Color(0xFF0277BD)
    val OceanSecondary = Color(0xFF00BCD4)
    val OceanSecondaryVariant = Color(0xFF0097A7)
    val OceanBackground = Color(0xFFF5FAFF)
    val OceanSurface = Color(0xFFFFFFFF)
    val OceanSurfaceVariant = Color(0xFFE3F2FD)
    val OceanPrimaryContainer = Color(0xFFB3E5FC)
    val OceanOnPrimaryContainer = Color(0xFF01579B)
    // Ocean Dark Theme
    val OceanPrimaryDark = Color(0xFF4FC3F7)
    val OceanPrimaryVariantDark = Color(0xFF29B6F6)
    val OceanSecondaryDark = Color(0xFF4DD0E1)
    val OceanBackgroundDark = Color(0xFF0D1B2A)
    val OceanSurfaceDark = Color(0xFF1B3A4B)
    val OceanSurfaceVariantDark = Color(0xFF274156)
    val OceanPrimaryContainerDark = Color(0xFF004D73)
    val OceanOnPrimaryContainerDark = Color(0xFFB3E5FC)

    // === Visualization Presets (Forest) ===
    val ForestDefault = Color(0xFF2E7D32)
    val ForestComparing = Color(0xFFFFA000)
    val ForestSwapping = Color(0xFFD32F2F)
    val ForestSorted = Color(0xFF66BB6A)
    val ForestPivot = Color(0xFF6D4C41)
    val ForestSelected = Color(0xFFFFF176)
    
    // Forest Theme - Full Color Scheme
    val ForestPrimary = Color(0xFF388E3C)
    val ForestPrimaryVariant = Color(0xFF2E7D32)
    val ForestSecondary = Color(0xFF8BC34A)
    val ForestSecondaryVariant = Color(0xFF689F38)
    val ForestBackground = Color(0xFFF5FFF5)
    val ForestSurface = Color(0xFFFFFFFF)
    val ForestSurfaceVariant = Color(0xFFE8F5E9)
    val ForestPrimaryContainer = Color(0xFFC8E6C9)
    val ForestOnPrimaryContainer = Color(0xFF1B5E20)
    // Forest Dark Theme
    val ForestPrimaryDark = Color(0xFF81C784)
    val ForestPrimaryVariantDark = Color(0xFF66BB6A)
    val ForestSecondaryDark = Color(0xFFAED581)
    val ForestBackgroundDark = Color(0xFF0D1A0D)
    val ForestSurfaceDark = Color(0xFF1B3A1B)
    val ForestSurfaceVariantDark = Color(0xFF274127)
    val ForestPrimaryContainerDark = Color(0xFF1B5E20)
    val ForestOnPrimaryContainerDark = Color(0xFFC8E6C9)

    // === Gradient Support ===
    /** Kotlinロゴのグラデーション開始色 */
    val GradientStart = KotlinBlue

    /** Kotlinロゴのグラデーション中間色 */
    val GradientMiddle = KotlinPurple

    /** Kotlinロゴのグラデーション終了色 */
    val GradientEnd = KotlinOrange

    // === Proficiency Level Colors ===
    /** 初心者レベル */
    val ProficiencyBeginner = KotlinOrange

    /** 中級者レベル */
    val ProficiencyIntermediate = Color(0xFFFFD23F)

    /** エキスパートレベル */
    val ProficiencyExpert = BarSorted

    // === Syntax Highlighting Colors ===
    /** キーワード色 */
    val SyntaxKeyword = Color(0xFFCC7832)

    /** 型名色 */
    val SyntaxType = Color(0xFFDA70D6)

    /** コメント色 */
    val SyntaxComment = Color(0xFF808080)

    /** 数値色 */
    val SyntaxNumber = Color(0xFF6897BB)

    /** 文字列色 */
    val SyntaxString = Color(0xFF6A8759)
}
