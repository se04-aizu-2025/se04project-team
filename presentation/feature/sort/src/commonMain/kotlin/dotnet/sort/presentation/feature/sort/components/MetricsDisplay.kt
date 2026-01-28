package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortDivider
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortInfoRow
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.ComplexityMetrics
import org.jetbrains.compose.resources.stringResource

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
        title = stringResource(Res.string.sort_metrics_title),
        modifier = modifier
    ) {
        if (metrics != null) {
            SortInfoRow(label = stringResource(Res.string.sort_metrics_comparisons), value = "${metrics.comparisonCount}")
            SortInfoRow(label = stringResource(Res.string.sort_metrics_swaps), value = "${metrics.swapCount}")
            SortInfoRow(label = stringResource(Res.string.sort_metrics_time), value = "${metrics.executionTimeNs / 1000} µs")

            Spacer(modifier = Modifier.height(SpacingTokens.S))
            SortDivider()
            Spacer(modifier = Modifier.height(SpacingTokens.S))

            SortInfoRow(label = stringResource(Res.string.sort_metrics_time_complexity), value = metrics.timeComplexity)
            SortInfoRow(label = stringResource(Res.string.sort_metrics_space_complexity), value = metrics.spaceComplexity)
        } else {
            SortText(
                text = stringResource(Res.string.sort_metrics_no_data),
                style = SortTheme.typography.bodyMedium,
                color = SortTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
