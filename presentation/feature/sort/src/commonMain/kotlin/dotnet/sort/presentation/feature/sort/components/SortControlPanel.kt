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
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.tokens.AnimationTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import org.jetbrains.compose.resources.stringResource

@Composable
fun SortControlPanel(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onResetClick: () -> Unit,
    onShuffleClick: () -> Unit,
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
            label = stringResource(Res.string.sort_controls_progress),
            valueLabel = if (progressEnabled) "$currentStep / ${totalSteps - 1}" else "-",
            value = currentStep.toFloat(),
            onValueChange = { onSeek(it.toInt()) },
            valueRange = 0f..(if (totalSteps > 1) (totalSteps - 1).toFloat() else 1f),
            steps = 0,
            enabled = progressEnabled && !isPlaying
        )

        // Speed Control
        SortSlider(
            label = stringResource(Res.string.sort_label_speed),
            valueLabel = "${(playbackSpeed * 10).toInt() / 10f}x",
            value = playbackSpeed,
            onValueChange = onSpeedChange,
            valueRange = AnimationTokens.SpeedMinMultiplier..AnimationTokens.SpeedMaxMultiplier,
            steps = 0,
            enabled = enabled
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
        ) {
            SortButton(
                text = "1x",
                onClick = { onSpeedChange(1f) },
                style = SortButtonStyle.Text,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
            SortButton(
                text = "2x",
                onClick = { onSpeedChange(2f) },
                style = SortButtonStyle.Text,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
            SortButton(
                text = "4x",
                onClick = { onSpeedChange(4f) },
                style = SortButtonStyle.Text,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
        }

        // Array Size Control
        SortSlider(
            label = stringResource(Res.string.sort_label_size),
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
                text = if (isPlaying) stringResource(Res.string.sort_controls_pause) else stringResource(Res.string.sort_controls_sort_resume),
                onClick = onPlayPauseClick,
                style = SortButtonStyle.Primary,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )

            SortButton(
                text = stringResource(Res.string.sort_controls_reset),
                onClick = onResetClick,
                style = SortButtonStyle.Outlined,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }

        // Shuffle Button
        SortButton(
            text = stringResource(Res.string.sort_controls_shuffle),
            onClick = onShuffleClick,
            style = SortButtonStyle.Outlined,
            enabled = enabled && !isPlaying,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Manual Step Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
             SortButton(
                text = stringResource(Res.string.sort_controls_step_prev),
                onClick = onStepBackwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying && currentStep > 0,
                modifier = Modifier.weight(1f)
            )
            SortButton(
                text = stringResource(Res.string.sort_controls_step_next),
                onClick = onStepForwardClick,
                style = SortButtonStyle.Text,
                enabled = enabled && !isPlaying && currentStep < totalSteps - 1,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
