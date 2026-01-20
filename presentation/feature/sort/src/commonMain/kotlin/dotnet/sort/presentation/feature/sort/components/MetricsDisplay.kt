package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortDivider
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortInfoRow
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.ComplexityMetrics

/**
 * パフォーマンスメトリクス表示コンポーネント。
 *
 * @param metrics 複雑性メトリクス
 * @param modifier Modifier
 */
@Composable
fun MetricsDisplay(
    metrics: ComplexityMetrics?,
    modifier: Modifier = Modifier
) {
    SortSectionCard(
        title = "Performance Metrics",
        modifier = modifier
    ) {
        if (metrics != null) {
            SortInfoRow(label = "Comparisons", value = "${metrics.comparisonCount}")
            SortInfoRow(label = "Swaps", value = "${metrics.swapCount}")
            SortInfoRow(label = "Time (Measured)", value = "${metrics.executionTimeNs / 1000} µs")

            Spacer(modifier = Modifier.height(SpacingTokens.S))
            SortDivider()
            Spacer(modifier = Modifier.height(SpacingTokens.S))

            SortInfoRow(label = "Time Complexity", value = metrics.timeComplexity)
            SortInfoRow(label = "Space Complexity", value = metrics.spaceComplexity)
        } else {
            SortText(
                text = "No data available",
                style = SortTheme.typography.bodyMedium,
                color = SortTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
