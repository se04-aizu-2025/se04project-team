package dotnet.sort.designsystem.components.organisms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
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
    var topBarVisible by remember { mutableStateOf(true) }
    var bottomBarVisible by remember { mutableStateOf(true) }

    val scrollConnection =
        remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource,
                ): Offset {
                    if (available.y < 0f) {
                        topBarVisible = false
                        bottomBarVisible = false
                    } else if (available.y > 0f) {
                        topBarVisible = true
                        bottomBarVisible = true
                    }
                    return Offset.Zero
                }
            }
        }

    Scaffold(
        modifier = modifier.nestedScroll(scrollConnection),
        containerColor = SortTheme.colorScheme.background,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            content(PaddingValues(SpacingTokens.None))

            AnimatedVisibility(
                visible = topBarVisible,
                enter =
                    slideInVertically(
                        animationSpec = tween(durationMillis = 200),
                        initialOffsetY = { -it },
                    ) + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit =
                    slideOutVertically(
                        animationSpec = tween(durationMillis = 200),
                        targetOffsetY = { -it },
                    ) + fadeOut(animationSpec = tween(durationMillis = 200)),
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = SpacingTokens.S),
            ) {
                topBar()
            }

            AnimatedVisibility(
                visible = bottomBarVisible,
                enter =
                    slideInVertically(
                        animationSpec = tween(durationMillis = 200),
                        initialOffsetY = { it },
                    ) + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit =
                    slideOutVertically(
                        animationSpec = tween(durationMillis = 200),
                        targetOffsetY = { it },
                    ) + fadeOut(animationSpec = tween(durationMillis = 200)),
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
                Text("Top Bar")
            },
            bottomBar = {
                Text("Bottom Bar")
            },
        ) {
            Text("Content")
        }
    }
}
