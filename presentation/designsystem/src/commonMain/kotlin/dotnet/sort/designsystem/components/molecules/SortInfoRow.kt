package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ラベルと値を表示する情報行 (Molecule)。
 *
 * 左にラベル、右に値を表示します。
 *
 * @param label ラベルテキスト
 * @param value 値テキスト
 * @param modifier Modifier
 */
@Composable
fun SortInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = SortTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SpacingTokens.XS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = SortTheme.typography.bodyMedium,
            color = colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = SortTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun SortInfoRowPreview() {
    SortTheme {
        SortInfoRow(
            label = "Time Complexity",
            value = "O(n²)"
        )
    }
}
