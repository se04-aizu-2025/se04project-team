package dotnet.sort.designsystem.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.ArrayBar
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートのビジュアライザーコンポーネント。
 *
 * @param array 表示する配列
 * @param highlightIndices ハイライトするインデックス
 * @param description 説明テキスト
 * @param modifier Modifier
 */
@Composable
fun SortVisualizer(
    array: List<Int>,
    highlightIndices: List<Int>,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visualizer Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = SpacingTokens.M),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (array.isNotEmpty()) {
                ArrayBar(
                    array = array,
                    highlightIndices = highlightIndices,
                    modifier = Modifier.fillMaxWidth().height(280.dp)
                )
            } else {
                SortText(
                    text = "Press 'Sort' to start",
                    style = SortTheme.typography.bodyLarge,
                    color = SortTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        // Description Text
        SortText(
            text = description.ifEmpty { " " },
            style = SortTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacingTokens.M)
        )
    }
}
