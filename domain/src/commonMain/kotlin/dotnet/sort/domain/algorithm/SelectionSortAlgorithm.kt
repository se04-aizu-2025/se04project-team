package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * 選択ソートの実装クラス。
 *
 * 未ソート部分から最小値（または最大値）を見つけ出し、未ソート部分の先頭と交換する操作を繰り返します。
 * - 時間計算量: O(n^2)
 * - 空間計算量: O(1)
 */
class SelectionSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.SELECTION
    override val timeComplexity = "O(n²)"
    override val spaceComplexity = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        val n = array.size
        for (i in 0 until n - 1) {
            var minIdx = i
            
            // Snapshot: Min search start
            addSnapshot(array, listOf(i), "Searching min starting from index $i")

            for (j in i + 1 until n) {
                // Compare current min with next element
                // Note: We don't snapshot every comparison to avoid clutter, 
                // but we could if we wanted to match BubbleSort verbosity.
                // Plan says: "2. Current min candidate (highlight)". 
                // Let's rely on highlighting minIdx when it changes.
                
                if (compare(array[j], array[minIdx]) < 0) {
                    minIdx = j
                    // Snapshot: New min candidate found
                    addSnapshot(array, listOf(minIdx), "New minimum found at index $minIdx")
                }
            }

            // Swap if needed
            if (minIdx != i) {
                swap(array, i, minIdx)
                // Snapshot: Min determined/swap
                addSnapshot(array, listOf(i, minIdx), "Swapped min value ${array[i]} to position $i")
            }
        }
    }
}
