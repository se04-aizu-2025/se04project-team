package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Gnome Sort アルゴリズム。
 *
 * 隣接要素を比較し、順序が逆なら後退して修正します。
 * - 時間計算量: O(n²)
 * - 空間計算量: O(1)
 */
class GnomeSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.GNOME

    override val timeComplexity: String = "O(n²)"

    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        var index = 0
        while (index < array.size) {
            if (index == 0 || compare(array[index - 1], array[index]) <= 0) {
                index++
            } else {
                swap(array, index, index - 1)
                addSnapshot(array, listOf(index - 1, index), "Swap ${array[index - 1]} and ${array[index]}")
                index--
            }
        }
    }
}
