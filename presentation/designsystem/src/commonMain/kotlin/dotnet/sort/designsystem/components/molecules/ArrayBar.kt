package dotnet.sort.designsystem.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.BarState
import dotnet.sort.designsystem.components.atoms.SortBar
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * 配列を棒グラフとして表示するコンポーネント (Molecule)。
 *
 * 複数の SortBar を横並びに配置し、ソートの可視化を行います。
 *
 * @param array 表示する配列
 * @param highlightIndices 比較中としてハイライトするインデックス
 * @param swappingIndices 交換中としてハイライトするインデックス
 * @param sortedIndices ソート済みのインデックス
 * @param pivotIndex ピボット要素のインデックス (QuickSort用)
 * @param selectedIndex 選択中の要素のインデックス (SelectionSort用)
 * @param modifier Modifier
 * @param barHeight バーの最大高さ
 */
@Composable
fun ArrayBar(
    array: List<Int>,
    highlightIndices: List<Int> = emptyList(),
    swappingIndices: List<Int> = emptyList(),
    sortedIndices: Set<Int> = emptySet(),
    pivotIndex: Int? = null,
    selectedIndex: Int? = null,
    modifier: Modifier = Modifier,
    barHeight: Dp = 200.dp,
) {
    if (array.isEmpty()) return

    val maxValue = array.maxOrNull() ?: 1

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(barHeight),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        array.forEachIndexed { index, value ->
            val state = determineBarState(
                index = index,
                sortedIndices = sortedIndices,
                pivotIndex = pivotIndex,
                selectedIndex = selectedIndex,
                swappingIndices = swappingIndices,
                highlightIndices = highlightIndices,
            )

            SortBar(
                value = value,
                maxValue = maxValue,
                state = state,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = SpacingTokens.BarGap),
            )
        }
    }
}

/**
 * インデックスに基づいてバーの状態を決定します。
 * 優先順位: Sorted > Pivot > Selected > Swapping > Comparing > Default
 */
private fun determineBarState(
    index: Int,
    sortedIndices: Set<Int>,
    pivotIndex: Int?,
    selectedIndex: Int?,
    swappingIndices: List<Int>,
    highlightIndices: List<Int>,
): BarState {
    return when {
        index in sortedIndices -> BarState.Sorted
        index == pivotIndex -> BarState.Pivot
        index == selectedIndex -> BarState.Selected
        index in swappingIndices -> BarState.Swapping
        index in highlightIndices -> BarState.Comparing
        else -> BarState.Default
    }
}
