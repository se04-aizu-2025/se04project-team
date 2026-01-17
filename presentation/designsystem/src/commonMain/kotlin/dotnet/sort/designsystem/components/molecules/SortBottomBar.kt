package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のボトムアプリバー (Molecule)。
 *
 * 複数のボタンを均等に配置したボトムバーを提供します。
 *
 * @param modifier Modifier
 * @param content RowScope内で配置するコンテンツ
 */
data class SortBottomBarItem(
    val icon: ImageVector,
    val contentDescription: String,
    val selected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun SortBottomBar(
    items: List<SortBottomBarItem>,
    modifier: Modifier = Modifier,
) {
    SortBarBase(
        modifier =
            modifier
                .padding(horizontal = SpacingTokens.M),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach { item ->
                SortIconButton(
                    icon = item.icon,
                    contentDescription = item.contentDescription,
                    tint =
                        if (item.selected) {
                            SortTheme.colorScheme.primary
                        } else {
                            SortTheme.colorScheme.onSurfaceVariant
                        },
                    onClick = item.onClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SortBottomBarPreview() {
    SortTheme {
        SortBottomBar(
            items =
                listOf(
                    SortBottomBarItem(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "戻る",
                        selected = true,
                        onClick = {},
                    ),
                    SortBottomBarItem(
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "次へ",
                        selected = false,
                        onClick = {},
                    ),
                ),
        )
    }
}
