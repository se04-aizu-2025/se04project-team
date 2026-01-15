package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * アイコン・タイトル・説明付きのクリッカブルカード (Molecule)。
 *
 * ナビゲーション用のカードUIを提供します。
 *
 * @param title カードタイトル
 * @param description カードの説明文
 * @param icon アイコン（絵文字）
 * @param onClick クリック時のコールバック
 * @param enabled 有効/無効状態
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortCard(
    title: String,
    description: String,
    icon: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colorScheme = SortTheme.colorScheme

    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
            disabledContainerColor = colorScheme.surfaceVariant.copy(alpha = 0.5f),
            disabledContentColor = colorScheme.onSurface.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpacingTokens.M)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = icon,
                    style = SortTheme.typography.displayMedium,
                    color = if (enabled) colorScheme.primary else colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(SpacingTokens.S))
                Text(
                    text = title,
                    style = SortTheme.typography.titleMedium,
                    color = colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(SpacingTokens.XS))
                Text(
                    text = description,
                    style = SortTheme.typography.bodySmall,
                    color = colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
