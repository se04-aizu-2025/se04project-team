package dotnet.sort.designsystem.components.atoms

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のトップアプリバー (Atom)。
 *
 * 戻るボタン付きの中央揃えトップバーを提供します。
 *
 * @param title タイトルテキスト
 * @param onBackClick 戻るボタン押下時のコールバック（nullの場合は戻るボタン非表示）
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = SortTheme.typography.titleLarge,
                color = SortTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Text(
                        text = "<",
                        style = SortTheme.typography.titleMedium,
                        color = SortTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = SortTheme.colorScheme.background
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun SortTopBarPreview() {
    SortTheme {
        SortTopBar(
            title = "Top Bar",
            onBackClick = {}
        )
    }
}
