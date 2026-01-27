package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Bitonic Sort アルゴリズム。
 *
 * Bitonic シーケンスを構築してマージする並列ソート向けの手法です。
 * - 時間計算量: O(n log² n)
 * - 空間計算量: O(n)
 */
class BitonicSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.BITONIC

    override val timeComplexity: String = "O(n log² n)"

    override val spaceComplexity: String = "O(n)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val originalSize = array.size
        val paddedSize = nextPowerOfTwo(originalSize)
        val work = array.toMutableList()

        if (paddedSize > originalSize) {
            repeat(paddedSize - originalSize) {
                work.add(Int.MAX_VALUE)
            }
        }

        bitonicSort(work, 0, work.size, true)

        array.clear()
        array.addAll(work.take(originalSize))
        addSnapshot(array, emptyList(), "Trim padding")
    }

    private fun bitonicSort(array: MutableList<Int>, low: Int, cnt: Int, ascending: Boolean) {
        if (cnt <= 1) return

        val k = cnt / 2
        bitonicSort(array, low, k, true)
        bitonicSort(array, low + k, k, false)
        bitonicMerge(array, low, cnt, ascending)
    }

    private fun bitonicMerge(array: MutableList<Int>, low: Int, cnt: Int, ascending: Boolean) {
        if (cnt <= 1) return

        val k = cnt / 2
        for (i in low until low + k) {
            val shouldSwap = if (ascending) compare(array[i], array[i + k]) > 0 else compare(array[i], array[i + k]) < 0
            if (shouldSwap) {
                swap(array, i, i + k)
                addSnapshot(array, listOf(i, i + k), "Swap ${array[i]} and ${array[i + k]}")
            }
        }
        bitonicMerge(array, low, k, ascending)
        bitonicMerge(array, low + k, k, ascending)
    }

    private fun nextPowerOfTwo(value: Int): Int {
        var n = 1
        while (n < value) n = n shl 1
        return n
    }
}
