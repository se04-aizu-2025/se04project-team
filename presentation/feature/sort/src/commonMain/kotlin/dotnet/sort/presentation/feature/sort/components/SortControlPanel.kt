package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortSlider
import dotnet.sort.designsystem.tokens.SpacingTokens

@Composable
fun SortControlPanel(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onResetClick: () -> Unit,
    onStepForwardClick: () -> Unit,
    onStepBackwardClick: () -> Unit,
    arraySize: Int,
    onArraySizeChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
    ) {
        // Size Control
        SortSlider(
            label = "Array Size",
            valueLabel = arraySize.toString(),
            value = arraySize.toFloat(),
            onValueChange = { onArraySizeChange(it.toInt()) },
            valueRange = 10f..100f,
            steps = 90, // roughly integer steps
            enabled = enabled && !isPlaying
        )

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
            SortButton(
                text = if (isPlaying) "Pause" else "Sort / Resume",
                onClick = onPlayPauseClick,
                style = SortButtonStyle.Primary,
                enabled = enabled, // Always enabled if panel is enabled, or depends on internal logic
                modifier = Modifier.weight(1f)
            )

            SortButton(
                text = "Reset",
                onClick = onResetClick,
                style = SortButtonStyle.Outlined,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Step Buttons (Optional, for manual control)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
             SortButton(
                text = "< Step",
                onClick = onStepBackwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying,
                modifier = Modifier.weight(1f)
            )
            SortButton(
                text = "Step >",
                onClick = onStepForwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
