package dotnet.sort.designsystem.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のスキャフォールド (Organism)。
 *
 * テーマに基づいた背景色を使用します。
 *
 * @param topBar トップバー
 * @param bottomBar ボトムバー
 * @param modifier Modifier
 * @param content コンテンツ
 */
@Composable
fun SortScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable BoxScope.() -> Unit = {},
    bottomBar: @Composable BoxScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = SortTheme.colorScheme.background,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            content(PaddingValues(SpacingTokens.None))

            Box(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = SpacingTokens.S),
            ) {
                topBar()
            }

            Box(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = SpacingTokens.S),
            ) {
                bottomBar()
            }
        }
    }
}

@Preview
@Composable
private fun SortScaffoldPreview() {
    SortTheme {
        SortScaffold(
            topBar = {
                SortText(text = "Top Bar")
            },
            bottomBar = {
                SortText(text = "Bottom Bar")
            },
        ) {
            SortText(text = "Content")
        }
    }
}
