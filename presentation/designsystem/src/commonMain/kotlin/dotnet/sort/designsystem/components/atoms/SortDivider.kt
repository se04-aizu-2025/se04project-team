package dotnet.sort.designsystem.components.atoms

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用の区切り線 (Atom)。
 *
 * テーマに基づいた色を使用します。
 *
 * @param thickness 線の太さ
 * @param modifier Modifier
 */
@Composable
fun SortDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = SortTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
    )
}

@Preview
@Composable
private fun SortDividerPreview() {
    SortTheme {
        SortDivider()
    }
}
