package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Counting Sort アルゴリズム。
 *
 * 取り得る値の範囲が小さい整数配列に対して高速に動作します。
 * 負数も扱えるように最小値を基準にオフセットを取り、カウント配列を作成します。
 */
class CountingSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.COUNTING

    override val timeComplexity: String = "O(n + k)"

    override val spaceComplexity: String = "O(k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val minValue = array.minOrNull() ?: return
        val maxValue = array.maxOrNull() ?: return
        val range = maxValue - minValue + 1
        if (range <= 0) return

        val counts = IntArray(range)
        for (value in array) {
            counts[value - minValue]++
        }

        addSnapshot(array, emptyList(), "Count frequencies")

        var index = 0
        for (offset in counts.indices) {
            val value = offset + minValue
            var count = counts[offset]
            while (count > 0) {
                array[index] = value
                addSnapshot(array, listOf(index), "Place $value")
                index++
                count--
            }
        }
    }
}
