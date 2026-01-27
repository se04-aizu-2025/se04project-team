package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.math.abs

/**
 * Radix Sort Algorithm (LSD).
 */
class RadixSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.RADIX
    override val timeComplexity: String = "O(nk)"
    override val spaceComplexity: String = "O(n + k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.isEmpty()) return

        val negatives = mutableListOf<Int>()
        val positives = mutableListOf<Int>()
        array.forEach { value ->
            if (value < 0) {
                negatives.add(abs(value))
            } else {
                positives.add(value)
            }
        }

        if (positives.isNotEmpty()) {
            radixSortNonNegative(positives)
        }
        if (negatives.isNotEmpty()) {
            radixSortNonNegative(negatives)
            negatives.reverse()
        }

        var index = 0
        negatives.forEach { value ->
            array[index] = -value
            swapCount++
            addSnapshot(array, listOf(index), "Place ${-value}")
            index++
        }
        positives.forEach { value ->
            array[index] = value
            swapCount++
            addSnapshot(array, listOf(index), "Place $value")
            index++
        }
    }

    private fun radixSortNonNegative(list: MutableList<Int>) {
        var exp = 1
        val max = list.maxOrNull() ?: 0
        while (max / exp > 0) {
            val output = IntArray(list.size)
            val count = IntArray(10)

            list.forEach { value ->
                val digit = (value / exp) % 10
                count[digit]++
            }

            for (i in 1 until count.size) {
                count[i] += count[i - 1]
            }

            for (i in list.lastIndex downTo 0) {
                val value = list[i]
                val digit = (value / exp) % 10
                output[count[digit] - 1] = value
                count[digit]--
            }

            for (i in list.indices) {
                list[i] = output[i]
            }

            exp *= 10
        }
    }
}
