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
