package dotnet.sort.designsystem.components.atoms

import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のプログレスインジケーター (Atom)。
 *
 * 進捗状況を表示するための水平プログレスバーを提供します。
 *
 * @param progress 進捗値（0.0f ~ 1.0f）
 * @param modifier Modifier
 * @param color プログレスバーの色（デフォルトはテーマの primary）
 * @param trackColor トラックの色（デフォルトはテーマの surfaceVariant）
 * @param height プログレスバーの高さ
 */
@Composable
fun SortProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = SortTheme.colorScheme.primary,
    trackColor: Color = SortTheme.colorScheme.surfaceVariant,
    height: Dp = SpacingTokens.ProgressBarHeight,
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier.height(height),
        color = color,
        trackColor = trackColor,
    )
}

@Preview
@Composable
private fun SortProgressIndicatorPreview() {
    SortTheme {
        SortProgressIndicator(
            progress = 0.7f,
        )
    }
}
