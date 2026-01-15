package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.ComplexityMetrics

@Composable
fun MetricsDisplay(
    metrics: ComplexityMetrics?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SortTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(
            modifier = Modifier.padding(SpacingTokens.M)
        ) {
            Text(
                text = "Performance Metrics",
                style = SortTheme.typography.titleMedium,
                color = SortTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(SpacingTokens.S))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(SpacingTokens.S))

            if (metrics != null) {
                MetricRow("Comparisons", "${metrics.comparisonCount}")
                MetricRow("Swaps", "${metrics.swapCount}")
                MetricRow("Time (Measured)", "${metrics.executionTimeNs / 1000} µs")
                
                Spacer(modifier = Modifier.height(SpacingTokens.S))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(SpacingTokens.S))
                
                MetricRow("Time Complexity", metrics.timeComplexity)
                MetricRow("Space Complexity", metrics.spaceComplexity)
            } else {
                 Text(
                    text = "No data available",
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = SpacingTokens.XS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = SortTheme.typography.bodyMedium,
            color = SortTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = SortTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = SortTheme.colorScheme.onSurface
        )
    }
}
