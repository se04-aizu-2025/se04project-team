package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * タイトル付きセクションカード (Molecule)。
 *
 * セクションタイトルとコンテンツを含むカードを提供します。
 *
 * @param title セクションタイトル
 * @param content カード内のコンテンツ
 * @param modifier Modifier
 */
@Composable
fun SortSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val colorScheme = SortTheme.colorScheme

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(SpacingTokens.M)
        ) {
            Text(
                text = title,
                style = SortTheme.typography.titleMedium,
                color = colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(SpacingTokens.S))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(SpacingTokens.S))

            content()
        }
    }
}

@Preview
@Composable
private fun SortSectionCardPreview() {
    SortTheme {
        SortSectionCard(title = "Section Title") {
            Text("Content inside card")
        }
    }
}
