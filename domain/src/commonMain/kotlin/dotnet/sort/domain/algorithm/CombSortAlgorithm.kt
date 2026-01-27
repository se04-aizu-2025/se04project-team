package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Comb Sort アルゴリズム。
 *
 * バブルソートの改良版で、比較するギャップを徐々に縮めます。
 * - 時間計算量: O(n²/2^p)
 * - 空間計算量: O(1)
 */
class CombSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.COMB

    override val timeComplexity: String = "O(n²/2^p)"

    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val shrink = 1.3
        var gap = array.size
        var swapped = true

        while (gap > 1 || swapped) {
            gap = (gap / shrink).toInt().coerceAtLeast(1)
            swapped = false

            for (i in 0 until array.size - gap) {
                val j = i + gap
                if (compare(array[i], array[j]) > 0) {
                    swap(array, i, j)
                    addSnapshot(array, listOf(i, j), "Swap ${array[i]} and ${array[j]}")
                    swapped = true
                }
            }
        }
    }
}
