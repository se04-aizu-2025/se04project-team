package dotnet.sort.designsystem.components.organisms

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のスキャフォールド (Organism)。
 *
 * テーマに基づいた背景色を使用します。
 *
 * @param topBar トップバー
 * @param modifier Modifier
 * @param content コンテンツ
 */
@Composable
fun SortScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = SortTheme.colorScheme.background,
        topBar = topBar,
        content = content
    )
}

@Preview
@Composable
private fun SortScaffoldPreview() {
    SortTheme {
        SortScaffold(
            topBar = {
                Text("Top Bar")
            }
        ) {
            Text("Content")
        }
    }
}
