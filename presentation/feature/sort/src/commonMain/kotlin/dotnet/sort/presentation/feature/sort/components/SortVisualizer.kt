package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
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
    sortedIndices: Set<Int> = emptySet(),
    description: String,
    modifier: Modifier = Modifier,
) {
    // パフォーマンス最適化: highlightIndicesをSetに変換してO(1)ルックアップ
    val highlightSet = remember(highlightIndices) { highlightIndices.toSet() }

    // アクセシビリティ: ビジュアライザーの説明
    val accessibilityDescription = remember(array.size, sortedIndices.size) {
        "Sort visualizer with ${array.size} elements, ${sortedIndices.size} sorted"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = accessibilityDescription },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visualizer Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SpacingTokens.VisualizerHeight)
                .padding(vertical = SpacingTokens.M),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (array.isNotEmpty()) {
                ArrayBar(
                    array = array,
                    highlightIndices = highlightSet.toList(),
                    sortedIndices = sortedIndices,
                    modifier = Modifier.fillMaxWidth().height(SpacingTokens.VisualizerHeightCompact)
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
