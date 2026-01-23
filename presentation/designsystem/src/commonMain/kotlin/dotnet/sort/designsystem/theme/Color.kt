package dotnet.sort.designsystem.theme

import androidx.compose.ui.graphics.Color
import dotnet.sort.designsystem.tokens.ColorTokens

/**
 * カラースキーム。
 *
 * Light/Darkテーマ用のカラーセットを定義します。
 */
data class SortColorScheme(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val onSurfaceVariant: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    // Visualization Colors
    val barDefault: Color,
    val barComparing: Color,
    val barSwapping: Color,
    val barSorted: Color,
    val barPivot: Color,
    val barSelected: Color,
    // Code View Colors
    val codeContainer: Color,
    val onCodeContainer: Color,
)

/**
 * ライトテーマ用カラースキーム。
 */
val LightColorScheme = SortColorScheme(
    primary = ColorTokens.Primary,
    primaryVariant = ColorTokens.PrimaryVariant,
    secondary = ColorTokens.Secondary,
    secondaryVariant = ColorTokens.SecondaryVariant,
    background = ColorTokens.Background,
    surface = ColorTokens.Surface,
    surfaceVariant = ColorTokens.SurfaceVariant,
    error = ColorTokens.Error,
    onPrimary = ColorTokens.OnPrimary,
    onSecondary = ColorTokens.OnSecondary,
    onBackground = ColorTokens.OnBackground,
    onSurface = ColorTokens.OnSurface,
    onError = ColorTokens.OnError,
    onSurfaceVariant = ColorTokens.OnSurfaceVariant,
    primaryContainer = ColorTokens.PrimaryContainer,
    onPrimaryContainer = ColorTokens.OnPrimaryContainer,
    barDefault = ColorTokens.BarDefault,
    barComparing = ColorTokens.BarComparing,
    barSwapping = ColorTokens.BarSwapping,
    barSorted = ColorTokens.BarSorted,
    barPivot = ColorTokens.BarPivot,
    barSelected = ColorTokens.BarSelected,
    codeContainer = ColorTokens.CodeContainer,
    onCodeContainer = ColorTokens.OnCodeContainer,
)

/**
 * ダークテーマ用カラースキーム。
 */
val DarkColorScheme = SortColorScheme(
    primary = ColorTokens.PrimaryDark,
    primaryVariant = ColorTokens.PrimaryVariantDark,
    secondary = ColorTokens.SecondaryDark,
    secondaryVariant = ColorTokens.SecondaryDark,
    background = ColorTokens.BackgroundDark,
    surface = ColorTokens.SurfaceDark,
    surfaceVariant = ColorTokens.SurfaceVariantDark,
    error = ColorTokens.Error,
    onPrimary = ColorTokens.OnPrimaryDark,
    onSecondary = ColorTokens.OnSecondaryDark,
    onBackground = ColorTokens.OnBackgroundDark,
    onSurface = ColorTokens.OnSurfaceDark,
    onError = ColorTokens.OnError,
    onSurfaceVariant = ColorTokens.OnSurfaceVariantDark,
    primaryContainer = ColorTokens.PrimaryContainerDark,
    onPrimaryContainer = ColorTokens.OnPrimaryContainerDark,
    barDefault = ColorTokens.BarDefault,
    barComparing = ColorTokens.BarComparing,
    barSwapping = ColorTokens.BarSwapping,
    barSorted = ColorTokens.BarSorted,
    barPivot = ColorTokens.BarPivot,
    barSelected = ColorTokens.BarSelected,
    codeContainer = ColorTokens.CodeContainerDark,
    onCodeContainer = ColorTokens.OnCodeContainerDark,
)
