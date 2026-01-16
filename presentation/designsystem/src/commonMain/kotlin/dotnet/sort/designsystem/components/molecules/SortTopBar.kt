package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のトップアプリバー (Atom)。
 *
 * タイトルテキストと、オプションで左端と右端のクリックアクションを含むトップバーを提供します。
 *
 * @param title タイトルテキスト
 * @param onStartClick 左端のクリックアクション（省略可能）
 * @param onEndClick 右端のクリックアクション（省略可能）
 * @param modifier Modifier
 */
@Composable
fun SortTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onStartClick: (@Composable () -> Unit)? = null,
    onEndClick: (@Composable () -> Unit)? = null
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(SortTheme.colorScheme.primaryContainer)
            .padding(SpacingTokens.M),
        contentAlignment = Alignment.Center,
    ){
        if (onStartClick != null) {
            Box(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                onStartClick()
            }
        }

        SortText(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = SortTheme.typography.titleLarge,
            color = SortTheme.colorScheme.onSurface
        )

        if(onEndClick != null) {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                onEndClick()
            }
        }
    }
}

@Preview
@Composable
private fun SortTopBarPreview() {
    SortTheme {
        SortTopBar(
            title = "タイトル",
            onStartClick = {
                SortIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "戻る",
                    onClick = {}
                )
            },
            onEndClick = {
                SortIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "次へ",
                    onClick = {}
                )
            }
        )
    }
}
