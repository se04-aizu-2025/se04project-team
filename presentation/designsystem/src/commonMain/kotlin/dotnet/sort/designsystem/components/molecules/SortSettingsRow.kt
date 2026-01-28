package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * Switch付きの設定行 (Molecule)。
 *
 * タイトル・説明・トグルスイッチのレイアウトを提供します。
 *
 * @param title 設定項目のタイトル
 * @param description 設定項目の説明文
 * @param checked スイッチの状態
 * @param onCheckedChange スイッチ変更時のコールバック
 * @param enabled 有効/無効状態
 * @param modifier Modifier
 */
@Composable
fun SortSettingsRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colorScheme = SortTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SpacingTokens.S),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            SortText(
                text = title,
                style = SortTheme.typography.titleMedium,
                color = colorScheme.onSurface
            )
            SortText(
                text = description,
                style = SortTheme.typography.bodySmall,
                color = colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@Preview
@Composable
private fun SortSettingsRowPreview() {
    SortTheme {
        SortSettingsRow(
            title = "Dark Mode",
            description = "Enable dark theme",
            checked = true,
            onCheckedChange = {}
        )
    }
}
