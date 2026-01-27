package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Radix Sort アルゴリズム (LSD)。
 *
 * 10進数の各桁ごとに安定ソートを行う非比較ソートです。
 * 負数が含まれる場合は、負数と非負数に分離して処理します。
 */
class RadixSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.RADIX

    override val timeComplexity: String = "O(nk)"

    override val spaceComplexity: String = "O(n + k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val negatives = array.filter { it < 0 }.map { -it }.toMutableList()
        val nonNegatives = array.filter { it >= 0 }.toMutableList()

        if (negatives.isNotEmpty()) {
            radixSort(negatives)
            negatives.reverse()
        }
        if (nonNegatives.isNotEmpty()) {
            radixSort(nonNegatives)
        }

        array.clear()
        array.addAll(negatives.map { -it })
        array.addAll(nonNegatives)

        addSnapshot(array, emptyList(), "Merge negatives and non-negatives")
    }

    private fun radixSort(list: MutableList<Int>) {
        var max = list.maxOrNull() ?: return
        var exp = 1

        while (max / exp > 0) {
            countingSortByDigit(list, exp)
            exp *= 10
        }
    }

    private fun countingSortByDigit(list: MutableList<Int>, exp: Int) {
        val output = IntArray(list.size)
        val count = IntArray(10)

        for (value in list) {
            val digit = (value / exp) % 10
            count[digit]++
        }

        for (i in 1 until count.size) {
            count[i] += count[i - 1]
        }

        for (i in list.size - 1 downTo 0) {
            val value = list[i]
            val digit = (value / exp) % 10
            val position = count[digit] - 1
            output[position] = value
            count[digit]--
        }

        for (i in list.indices) {
            list[i] = output[i]
        }

        addSnapshot(list, emptyList(), "Digit pass exp=$exp")
    }
}
