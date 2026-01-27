package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Cocktail Sort アルゴリズム。
 *
 * 双方向にバブルソートを行うことで、端の要素を同時に整列します。
 * - 時間計算量: O(n²)
 * - 空間計算量: O(1)
 */
class CocktailSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.COCKTAIL

    override val timeComplexity: String = "O(n²)"

    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        var start = 0
        var end = array.lastIndex
        var swapped = true

        while (swapped) {
            swapped = false
            for (i in start until end) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Swap ${array[i]} and ${array[i + 1]}")
                    swapped = true
                }
            }
            if (!swapped) break

            swapped = false
            end--

            for (i in end downTo start + 1) {
                if (compare(array[i - 1], array[i]) > 0) {
                    swap(array, i - 1, i)
                    addSnapshot(array, listOf(i - 1, i), "Swap ${array[i - 1]} and ${array[i]}")
                    swapped = true
                }
            }
            start++
        }
    }
}
