package dotnet.sort.designsystem.components.atoms

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のボタンスタイル。
 */
enum class SortButtonStyle {
    /** プライマリボタン（塗りつぶし） */
    Primary,
    /** アウトラインボタン */
    Outlined,
    /** テキストボタン */
    Text,
}

/**
 * ソートアプリ用のボタンコンポーネント (Atom)。
 *
 * @param text ボタンのテキスト
 * @param onClick クリック時のコールバック
 * @param modifier Modifier
 * @param style ボタンのスタイル [SortButtonStyle]
 * @param enabled 有効/無効状態
 * @param icon オプションのアイコン
 */
@Composable
fun SortButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: SortButtonStyle = SortButtonStyle.Primary,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    val colorScheme = SortTheme.colorScheme

    val content: @Composable () -> Unit = {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(SpacingTokens.S))
        }
        Text(text = text)
    }

    when (style) {
        SortButtonStyle.Primary -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary,
                ),
                contentPadding = PaddingValues(
                    horizontal = SpacingTokens.ButtonPaddingHorizontal,
                    vertical = SpacingTokens.ButtonPaddingVertical,
                ),
                content = { content() }
            )
        }
        SortButtonStyle.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorScheme.primary,
                ),
                contentPadding = PaddingValues(
                    horizontal = SpacingTokens.ButtonPaddingHorizontal,
                    vertical = SpacingTokens.ButtonPaddingVertical,
                ),
                content = { content() }
            )
        }
        SortButtonStyle.Text -> {
            TextButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorScheme.primary,
                ),
                content = { content() }
            )
        }
    }
}

@Preview
@Composable
private fun SortButtonPreview() {
    SortTheme {
        SortButton(
            text = "Primary Button",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun SortOutlinedButtonPreview() {
    SortTheme {
        SortButton(
            text = "Outlined Button",
            onClick = {},
            style = SortButtonStyle.Outlined
        )
    }
}
