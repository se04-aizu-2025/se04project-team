package dotnet.sort.designsystem.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のスライダーコンポーネント (Atom)。
 *
 * ラベル付きでカスタマイズ可能なスライダーを提供します。
 *
 * @param value 現在の値
 * @param onValueChange 値変更時のコールバック
 * @param modifier Modifier
 * @param valueRange 値の範囲
 * @param steps ステップ数（0の場合は連続）
 * @param label オプションのラベル
 * @param valueLabel 現在の値を表示するラベル（nullの場合は非表示）
 * @param enabled 有効/無効状態
 */
@Composable
fun SortSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    label: String? = null,
    valueLabel: String? = null,
    enabled: Boolean = true,
) {
    val colorScheme = SortTheme.colorScheme

    Column(modifier = modifier) {
        if (label != null || valueLabel != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (label != null) {
                    Text(
                        text = label,
                        style = SortTheme.typography.labelMedium,
                        color = colorScheme.onSurface,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (valueLabel != null) {
                    Text(
                        text = valueLabel,
                        style = SortTheme.typography.bodyMedium,
                        color = colorScheme.primary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpacingTokens.XS))
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = colorScheme.primary,
                activeTrackColor = colorScheme.primary,
                inactiveTrackColor = colorScheme.surfaceVariant,
            ),
        )
    }
}

@Preview
@Composable
private fun SortSliderPreview() {
    SortTheme {
        SortSlider(
            value = 0.5f,
            onValueChange = {},
            label = "Speed",
            valueLabel = "1.0x"
        )
    }
}
