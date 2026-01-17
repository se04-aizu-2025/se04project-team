package dotnet.sort.designsystem.components.atoms

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のアイコンボタンスタイル。
 */
enum class SortIconButtonStyle {
    /** 標準のアイコンボタン（背景なし） */
    Standard,
    /** 塗りつぶしアイコンボタン */
    Filled,
    /** アウトラインアイコンボタン */
    Outlined,
    /** トーナルアイコンボタン（柔らかい背景色） */
    Tonal,
}

/**
 * ソートアプリ用のアイコンボタンコンポーネント (Atom)。
 *
 * Material3 IconButton をラップし、デザインシステムのスタイルを適用します。
 *
 * @param onClick クリック時のコールバック
 * @param icon 表示するアイコン
 * @param contentDescription アクセシビリティ用の説明
 * @param modifier Modifier
 * @param style ボタンのスタイル [SortIconButtonStyle]
 * @param enabled 有効/無効状態
 * @param tint アイコンの色（null の場合はスタイルに応じたデフォルト色）
 */
@Composable
fun SortIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    style: SortIconButtonStyle = SortIconButtonStyle.Standard,
    enabled: Boolean = true,
    tint: Color? = null,
) {
    val colorScheme = SortTheme.colorScheme

    when (style) {
        SortIconButtonStyle.Standard -> {
            IconButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = tint ?: colorScheme.onSurface,
                ),
            ) {
                SortIcon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = tint ?: colorScheme.onSurface,
                )
            }
        }
        SortIconButtonStyle.Filled -> {
            FilledIconButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = colorScheme.primary,
                    contentColor = tint ?: colorScheme.onPrimary,
                ),
            ) {
                SortIcon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = tint ?: colorScheme.onPrimary,
                )
            }
        }
        SortIconButtonStyle.Outlined -> {
            OutlinedIconButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    contentColor = tint ?: colorScheme.primary,
                ),
            ) {
                SortIcon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = tint ?: colorScheme.primary,
                )
            }
        }
        SortIconButtonStyle.Tonal -> {
            FilledTonalIconButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = tint ?: colorScheme.onPrimaryContainer,
                ),
            ) {
                SortIcon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = tint ?: colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SortIconButtonStandardPreview() {
    SortTheme {
        SortIconButton(
            onClick = {},
            icon = Icons.Default.PlayArrow,
            contentDescription = "Play",
            style = SortIconButtonStyle.Standard,
        )
    }
}

@Preview
@Composable
private fun SortIconButtonFilledPreview() {
    SortTheme {
        SortIconButton(
            onClick = {},
            icon = Icons.Default.Add,
            contentDescription = "Add",
            style = SortIconButtonStyle.Filled,
        )
    }
}

@Preview
@Composable
private fun SortIconButtonOutlinedPreview() {
    SortTheme {
        SortIconButton(
            onClick = {},
            icon = Icons.Default.Settings,
            contentDescription = "Settings",
            style = SortIconButtonStyle.Outlined,
        )
    }
}

@Preview
@Composable
private fun SortIconButtonTonalPreview() {
    SortTheme {
        SortIconButton(
            onClick = {},
            icon = Icons.Default.Close,
            contentDescription = "Close",
            style = SortIconButtonStyle.Tonal,
        )
    }
}
