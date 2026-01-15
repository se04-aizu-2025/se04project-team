package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.molecules.ArrayBar
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

@Composable
fun SortVisualizer(
    array: List<Int>,
    highlightIndices: List<Int>,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visualizer Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Fixed height for visualization
                .padding(vertical = SpacingTokens.M),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (array.isNotEmpty()) {
                ArrayBar(
                    array = array,
                    highlightIndices = highlightIndices,
                    modifier = Modifier.fillMaxWidth().height(280.dp),
                    // TODO: Pass other indices (sorted, pivot, etc.) when available in State/Snapshot
                    // Currently we only have highlightingIndices from SortSnapshot
                )
            } else {
                Text(
                    text = "Press 'Sort' to start",
                    style = SortTheme.typography.bodyLarge,
                    color = SortTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        // Description Text
        Text(
            text = description.ifEmpty { " " }, // Reserve space
            style = SortTheme.typography.bodyMedium,
            color = SortTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacingTokens.M)
        )
    }
}
