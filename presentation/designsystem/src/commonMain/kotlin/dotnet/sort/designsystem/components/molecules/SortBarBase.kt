package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * SortTopBar/SortBottomBarの共通ベースコンポーネント (internal)。
 *
 * 共通のスタイリング（背景色、形状、パディング）を提供します。
 *
 * @param modifier Modifier
 * @param content 内部コンテンツ
 */
@Composable
internal fun SortBarBase(
    modifier: Modifier = Modifier,
    containerColor: Color = SortTheme.colorScheme.primaryContainer,
    contentColor: Color = SortTheme.colorScheme.onPrimaryContainer,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SpacingTokens.L)),
        color = containerColor,
        shadowElevation = SpacingTokens.S,
        contentColor = contentColor,
    ) {
        Box(
            modifier = Modifier.padding(SpacingTokens.S),
            content = content,
        )
    }
}
