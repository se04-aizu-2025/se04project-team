package dotnet.sort.designsystem.theme

import androidx.compose.ui.graphics.Color
import dotnet.sort.designsystem.tokens.ColorTokens

/**
 * ソート可視化のカラーパレット。
 */
data class SortVisualizationPalette(
    val barDefault: Color,
    val barComparing: Color,
    val barSwapping: Color,
    val barSorted: Color,
    val barPivot: Color,
    val barSelected: Color,
)

/**
 * 可視化用のプリセットパレット。
 */
object SortVisualizationPalettes {
    val Kotlin = SortVisualizationPalette(
        barDefault = ColorTokens.BarDefault,
        barComparing = ColorTokens.BarComparing,
        barSwapping = ColorTokens.BarSwapping,
        barSorted = ColorTokens.BarSorted,
        barPivot = ColorTokens.BarPivot,
        barSelected = ColorTokens.BarSelected,
    )

    val Ocean = SortVisualizationPalette(
        barDefault = ColorTokens.OceanBarDefault,
        barComparing = ColorTokens.OceanBarComparing,
        barSwapping = ColorTokens.OceanBarSwapping,
        barSorted = ColorTokens.OceanBarSorted,
        barPivot = ColorTokens.OceanBarPivot,
        barSelected = ColorTokens.OceanBarSelected,
    )

    val Forest = SortVisualizationPalette(
        barDefault = ColorTokens.ForestBarDefault,
        barComparing = ColorTokens.ForestBarComparing,
        barSwapping = ColorTokens.ForestBarSwapping,
        barSorted = ColorTokens.ForestBarSorted,
        barPivot = ColorTokens.ForestBarPivot,
        barSelected = ColorTokens.ForestBarSelected,
    )
}
