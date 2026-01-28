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
import dotnet.sort.designsystem.tokens.AnimationTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.sort_label_speed
import dotnet.sort.designsystem.generated.resources.sort_speed_1x
import dotnet.sort.designsystem.generated.resources.sort_speed_2x
import dotnet.sort.designsystem.generated.resources.sort_speed_4x
import dotnet.sort.designsystem.generated.resources.sort_label_size
import org.jetbrains.compose.resources.stringResource

@Composable
fun SortSettingsPanel(
    arraySize: Int,
    onArraySizeChange: (Int) -> Unit,
    playbackSpeed: Float,
    onSpeedChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
    ) {
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
                text = stringResource(Res.string.sort_speed_1x),
                onClick = { onSpeedChange(1f) },
                style = SortButtonStyle.Text,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
            SortButton(
                text = stringResource(Res.string.sort_speed_2x),
                onClick = { onSpeedChange(2f) },
                style = SortButtonStyle.Text,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )
            SortButton(
                text = stringResource(Res.string.sort_speed_4x),
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
            enabled = enabled
        )
    }
}
