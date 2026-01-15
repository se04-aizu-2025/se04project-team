package dotnet.sort.designsystem.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用のテキストコンポーネント (Atom)。
 *
 * テーマに基づいたデフォルトスタイルを提供します。
 *
 * @param text 表示するテキスト
 * @param style テキストスタイル
 * @param color テキスト色
 * @param fontWeight フォントウェイト
 * @param textAlign テキスト配置
 * @param maxLines 最大行数
 * @param overflow オーバーフロー時の処理
 * @param modifier Modifier
 */
@Composable
fun SortText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = SortTheme.typography.bodyMedium,
    color: Color = SortTheme.colorScheme.onSurface,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

/**
 * 見出し用テキスト。
 */
@Composable
fun SortHeadline(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = SortTheme.colorScheme.onSurface
) {
    SortText(
        text = text,
        style = SortTheme.typography.headlineMedium,
        color = color,
        modifier = modifier
    )
}

/**
 * タイトル用テキスト。
 */
@Composable
fun SortTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = SortTheme.colorScheme.onSurface
) {
    SortText(
        text = text,
        style = SortTheme.typography.titleMedium,
        color = color,
        modifier = modifier
    )
}

/**
 * ラベル用テキスト。
 */
@Composable
fun SortLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = SortTheme.colorScheme.onSurface
) {
    SortText(
        text = text,
        style = SortTheme.typography.labelMedium,
        color = color,
        modifier = modifier
    )
}
