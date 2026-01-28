package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.nav_back
import org.jetbrains.compose.resources.stringResource
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のトップアプリバー (Molecule)。
 *
 * タイトルテキストと、オプションで左端と右端のクリックアクションを含むトップバーを提供します。
 *
 * @param title タイトルテキスト
 * @param modifier Modifier
 * @param onBackClick 戻るボタン押下時のコールバック（省略可能）
 * @param startAction 左端のアクション（省略可能）
 * @param endAction 右端のアクション（省略可能）
 */
@Composable
fun SortTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    startAction: (@Composable () -> Unit)? = null,
    endAction: (@Composable () -> Unit)? = null,
) {
    val resolvedStartAction =
        startAction ?: onBackClick?.let {
            {
                SortIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.nav_back),
                    onClick = it,
                )
            }
        }

    SortBarBase(
        modifier =
            modifier
                .padding(horizontal = SpacingTokens.M),
        containerColor = SortTheme.colorScheme.primary,
        contentColor = SortTheme.colorScheme.onPrimary,
    ) {
        resolvedStartAction?.let {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                it()
            }
        }

        SortText(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = SortTheme.typography.titleLarge,
            color = SortTheme.colorScheme.onPrimary,
        )

        endAction?.let {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                it()
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
            startAction = {
                SortIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "戻る",
                    onClick = {},
                )
            },
            endAction = {
                SortIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "次へ",
                    onClick = {},
                )
            },
        )
    }
}
