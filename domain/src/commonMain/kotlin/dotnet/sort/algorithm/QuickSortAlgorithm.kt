package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * クイックソートの実装クラス。
 *
 * Lomuto分割法（末尾ピボット）を採用しています。
 * ピボットを選択し、それより小さい要素を左に、大きい要素を右に移動させる（パーティション操作）
 * ことを再帰的に繰り返します。
 *
 * - 時間計算量: 平均 O(n log n), 最悪 O(n^2)
 * - 空間計算量:平均 O(log n)
 */
class QuickSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.QUICK
    override val timeComplexity = "O(n log n)"
    override val spaceComplexity = "O(log n)"

    override fun doSort(array: MutableList<Int>) {
        if (array.isNotEmpty()) {
            quickSort(array, 0, array.size - 1)
        }
    }

    private fun quickSort(array: MutableList<Int>, low: Int, high: Int) {
        if (low < high) {
            val pivotIndex = partition(array, low, high)
            quickSort(array, low, pivotIndex - 1)
            quickSort(array, pivotIndex + 1, high)
        }
    }

    private fun partition(array: MutableList<Int>, low: Int, high: Int): Int {
        val pivot = array[high]
        // Snapshot: Pivot Selection
        addSnapshot(array, listOf(high), "Pivot selected: $pivot at index $high")

        var i = low - 1 // 小さい要素のインデックス

        for (j in low until high) {
            // Snapshot: Compare
            // addSnapshot(array, listOf(j, high), "Comparing ${array[j]} with pivot $pivot")

            if (compare(array[j], pivot) < 0) {
                i++
                if (i != j) {
                    swap(array, i, j)
                    // Snapshot: Swap
                    addSnapshot(array, listOf(i, j), "Swapped ${array[j]} and ${array[i]} (smaller than pivot)")
                }
            }
        }

        if (i + 1 != high) {
            swap(array, i + 1, high)
            // Snapshot: Pivot placed
            addSnapshot(array, listOf(i + 1, high), "Placed pivot $pivot at correct position ${i + 1}")
        } else {
             // Even if no swap, snapshot to show pivot is settled? Maybe redundant but clear.
             addSnapshot(array, listOf(i + 1), "Pivot $pivot is at correct position ${i + 1}")
        }
        
        return i + 1
    }
}
