package dotnet.sort.designsystem.components.atoms

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のアイコンコンポーネント (Atom)。
 *
 * Material3 Icon をラップし、テーマに基づいたデフォルト色を提供します。
 *
 * @param imageVector 表示するアイコン
 * @param contentDescription アクセシビリティ用の説明（装飾的な場合は null）
 * @param modifier Modifier
 * @param tint アイコンの色（デフォルトはテーマの onSurface 色）
 */
@Composable
fun SortIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

@Preview
@Composable
private fun SortIconPreview() {
    SortTheme {
        SortIcon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play"
        )
    }
}

@Preview
@Composable
private fun SortIconSettingsPreview() {
    SortTheme {
        SortIcon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings"
        )
    }
}
