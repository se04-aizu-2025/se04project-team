package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のボトムアプリバー (Molecule)。
 *
 * 複数のボタンを均等に配置したボトムバーを提供します。
 *
 * @param modifier Modifier
 * @param content RowScope内で配置するコンテンツ
 */
@Composable
fun SortBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    SortBarBase(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview
@Composable
private fun SortBottomBarPreview() {
    SortTheme {
        SortBottomBar {
            SortIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "戻る",
                onClick = {}
            )
            SortIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "次へ",
                onClick = {}
            )
        }
    }
}
