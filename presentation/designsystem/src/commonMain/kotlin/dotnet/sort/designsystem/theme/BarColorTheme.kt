package dotnet.sort.designsystem.theme

import androidx.compose.ui.graphics.Color
import dotnet.sort.designsystem.tokens.ColorTokens
import dotnet.sort.domain.model.BarColorTheme

/**
 * Bar visualization color scheme.
 */
data class BarColorScheme(
    val barDefault: Color,
    val barComparing: Color,
    val barSwapping: Color,
    val barSorted: Color,
    val barPivot: Color,
    val barSelected: Color,
)

/**
 * Full theme color scheme for a BarColorTheme preset.
 */
data class ThemeColorPreset(
    // Bar colors
    val barColors: BarColorScheme,
    // Light theme colors
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    // Dark theme colors
    val primaryDark: Color,
    val primaryVariantDark: Color,
    val secondaryDark: Color,
    val backgroundDark: Color,
    val surfaceDark: Color,
    val surfaceVariantDark: Color,
    val primaryContainerDark: Color,
    val onPrimaryContainerDark: Color,
)

fun barColorScheme(theme: BarColorTheme): BarColorScheme {
    return when (theme) {
        BarColorTheme.KOTLIN -> BarColorScheme(
            barDefault = ColorTokens.BarDefault,
            barComparing = ColorTokens.BarComparing,
            barSwapping = ColorTokens.BarSwapping,
            barSorted = ColorTokens.BarSorted,
            barPivot = ColorTokens.BarPivot,
            barSelected = ColorTokens.BarSelected,
        )
        BarColorTheme.OCEAN -> BarColorScheme(
            barDefault = ColorTokens.OceanDefault,
            barComparing = ColorTokens.OceanComparing,
            barSwapping = ColorTokens.OceanSwapping,
            barSorted = ColorTokens.OceanSorted,
            barPivot = ColorTokens.OceanPivot,
            barSelected = ColorTokens.OceanSelected,
        )
        BarColorTheme.FOREST -> BarColorScheme(
            barDefault = ColorTokens.ForestDefault,
            barComparing = ColorTokens.ForestComparing,
            barSwapping = ColorTokens.ForestSwapping,
            barSorted = ColorTokens.ForestSorted,
            barPivot = ColorTokens.ForestPivot,
            barSelected = ColorTokens.ForestSelected,
        )
    }
}

/**
 * Get full theme color preset for a given BarColorTheme.
 */
fun themeColorPreset(theme: BarColorTheme): ThemeColorPreset {
    return when (theme) {
        BarColorTheme.KOTLIN -> ThemeColorPreset(
            barColors = barColorScheme(theme),
            // Light
            primary = ColorTokens.Primary,
            primaryVariant = ColorTokens.PrimaryVariant,
            secondary = ColorTokens.Secondary,
            secondaryVariant = ColorTokens.SecondaryVariant,
            background = ColorTokens.Background,
            surface = ColorTokens.Surface,
            surfaceVariant = ColorTokens.SurfaceVariant,
            primaryContainer = ColorTokens.PrimaryContainer,
            onPrimaryContainer = ColorTokens.OnPrimaryContainer,
            // Dark
            primaryDark = ColorTokens.PrimaryDark,
            primaryVariantDark = ColorTokens.PrimaryVariantDark,
            secondaryDark = ColorTokens.SecondaryDark,
            backgroundDark = ColorTokens.BackgroundDark,
            surfaceDark = ColorTokens.SurfaceDark,
            surfaceVariantDark = ColorTokens.SurfaceVariantDark,
            primaryContainerDark = ColorTokens.PrimaryContainerDark,
            onPrimaryContainerDark = ColorTokens.OnPrimaryContainerDark,
        )
        BarColorTheme.OCEAN -> ThemeColorPreset(
            barColors = barColorScheme(theme),
            // Light
            primary = ColorTokens.OceanPrimary,
            primaryVariant = ColorTokens.OceanPrimaryVariant,
            secondary = ColorTokens.OceanSecondary,
            secondaryVariant = ColorTokens.OceanSecondaryVariant,
            background = ColorTokens.OceanBackground,
            surface = ColorTokens.OceanSurface,
            surfaceVariant = ColorTokens.OceanSurfaceVariant,
            primaryContainer = ColorTokens.OceanPrimaryContainer,
            onPrimaryContainer = ColorTokens.OceanOnPrimaryContainer,
            // Dark
            primaryDark = ColorTokens.OceanPrimaryDark,
            primaryVariantDark = ColorTokens.OceanPrimaryVariantDark,
            secondaryDark = ColorTokens.OceanSecondaryDark,
            backgroundDark = ColorTokens.OceanBackgroundDark,
            surfaceDark = ColorTokens.OceanSurfaceDark,
            surfaceVariantDark = ColorTokens.OceanSurfaceVariantDark,
            primaryContainerDark = ColorTokens.OceanPrimaryContainerDark,
            onPrimaryContainerDark = ColorTokens.OceanOnPrimaryContainerDark,
        )
        BarColorTheme.FOREST -> ThemeColorPreset(
            barColors = barColorScheme(theme),
            // Light
            primary = ColorTokens.ForestPrimary,
            primaryVariant = ColorTokens.ForestPrimaryVariant,
            secondary = ColorTokens.ForestSecondary,
            secondaryVariant = ColorTokens.ForestSecondaryVariant,
            background = ColorTokens.ForestBackground,
            surface = ColorTokens.ForestSurface,
            surfaceVariant = ColorTokens.ForestSurfaceVariant,
            primaryContainer = ColorTokens.ForestPrimaryContainer,
            onPrimaryContainer = ColorTokens.ForestOnPrimaryContainer,
            // Dark
            primaryDark = ColorTokens.ForestPrimaryDark,
            primaryVariantDark = ColorTokens.ForestPrimaryVariantDark,
            secondaryDark = ColorTokens.ForestSecondaryDark,
            backgroundDark = ColorTokens.ForestBackgroundDark,
            surfaceDark = ColorTokens.ForestSurfaceDark,
            surfaceVariantDark = ColorTokens.ForestSurfaceVariantDark,
            primaryContainerDark = ColorTokens.ForestPrimaryContainerDark,
            onPrimaryContainerDark = ColorTokens.ForestOnPrimaryContainerDark,
        )
    }
}

/**
 * Apply a full theme preset to SortColorScheme based on light/dark mode.
 */
fun SortColorScheme.withFullTheme(theme: BarColorTheme, isDarkTheme: Boolean): SortColorScheme {
    val preset = themeColorPreset(theme)
    return if (isDarkTheme) {
        copy(
            primary = preset.primaryDark,
            primaryVariant = preset.primaryVariantDark,
            secondary = preset.secondaryDark,
            secondaryVariant = preset.secondaryDark,
            background = preset.backgroundDark,
            surface = preset.surfaceDark,
            surfaceVariant = preset.surfaceVariantDark,
            primaryContainer = preset.primaryContainerDark,
            onPrimaryContainer = preset.onPrimaryContainerDark,
            barDefault = preset.barColors.barDefault,
            barComparing = preset.barColors.barComparing,
            barSwapping = preset.barColors.barSwapping,
            barSorted = preset.barColors.barSorted,
            barPivot = preset.barColors.barPivot,
            barSelected = preset.barColors.barSelected,
        )
    } else {
        copy(
            primary = preset.primary,
            primaryVariant = preset.primaryVariant,
            secondary = preset.secondary,
            secondaryVariant = preset.secondaryVariant,
            background = preset.background,
            surface = preset.surface,
            surfaceVariant = preset.surfaceVariant,
            primaryContainer = preset.primaryContainer,
            onPrimaryContainer = preset.onPrimaryContainer,
            barDefault = preset.barColors.barDefault,
            barComparing = preset.barColors.barComparing,
            barSwapping = preset.barColors.barSwapping,
            barSorted = preset.barColors.barSorted,
            barPivot = preset.barColors.barPivot,
            barSelected = preset.barColors.barSelected,
        )
    }
}

@Deprecated("Use withFullTheme instead for complete theme application")
fun SortColorScheme.withBarTheme(theme: BarColorTheme): SortColorScheme {
    val barColors = barColorScheme(theme)
    return copy(
        barDefault = barColors.barDefault,
        barComparing = barColors.barComparing,
        barSwapping = barColors.barSwapping,
        barSorted = barColors.barSorted,
        barPivot = barColors.barPivot,
        barSelected = barColors.barSelected,
    )
}
