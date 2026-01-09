package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    playbackSpeed: Float,
    onSpeedChange: (Float) -> Unit,
    currentStep: Int,
    totalSteps: Int,
    onSeek: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
    ) {
        // Progress Control (Seek Bar)
        val progressEnabled = enabled && totalSteps > 0
        SortSlider(
            label = "Progress",
            valueLabel = if (progressEnabled) "$currentStep / ${totalSteps - 1}" else "-",
            value = currentStep.toFloat(),
            onValueChange = { onSeek(it.toInt()) },
            valueRange = 0f..(if (totalSteps > 1) (totalSteps - 1).toFloat() else 1f),
            steps = 0,
            enabled = progressEnabled && !isPlaying
        )

        // Speed Control
        SortSlider(
            label = "Speed",
            valueLabel = "${(playbackSpeed * 10).toInt() / 10f}x",
            value = playbackSpeed,
            onValueChange = onSpeedChange,
            valueRange = 0.25f..4.0f,
            steps = 0,
            enabled = enabled
        )

        // Array Size Control
        SortSlider(
            label = "Array Size",
            valueLabel = arraySize.toString(),
            value = arraySize.toFloat(),
            onValueChange = { onArraySizeChange(it.toInt()) },
            valueRange = 10f..100f,
            steps = 90,
            enabled = enabled && !isPlaying
        )

        Spacer(modifier = Modifier.height(SpacingTokens.S))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
            SortButton(
                text = if (isPlaying) "Pause" else "Sort / Resume",
                onClick = onPlayPauseClick,
                style = SortButtonStyle.Primary,
                enabled = enabled,
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
        
        // Manual Step Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
             SortButton(
                text = "< Step",
                onClick = onStepBackwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying && currentStep > 0,
                modifier = Modifier.weight(1f)
            )
            SortButton(
                text = "Step >",
                onClick = onStepForwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying && currentStep < totalSteps - 1,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
