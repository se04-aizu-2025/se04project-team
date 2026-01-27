package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Odd-Even Sort アルゴリズム。
 *
 * 奇数インデックスと偶数インデックスを交互に比較するバブルソート系の手法です。
 * - 時間計算量: O(n²)
 * - 空間計算量: O(1)
 */
class OddEvenSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.ODD_EVEN

    override val timeComplexity: String = "O(n²)"

    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        var sorted = false
        while (!sorted) {
            sorted = true

            for (i in 1 until array.size - 1 step 2) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Swap ${array[i]} and ${array[i + 1]} (odd phase)")
                    sorted = false
                }
            }

            for (i in 0 until array.size - 1 step 2) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Swap ${array[i]} and ${array[i + 1]} (even phase)")
                    sorted = false
                }
            }
        }
    }
}
