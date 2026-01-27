package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.math.max

/**
 * Bitonic Sort Algorithm.
 */
class BitonicSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.BITONIC
    override val timeComplexity: String = "O(n log^2 n)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val originalSize = array.size
        val paddedSize = nextPowerOfTwo(originalSize)
        if (paddedSize > originalSize) {
            val sentinel = (array.maxOrNull() ?: 0) + 1
            repeat(paddedSize - originalSize) {
                array.add(sentinel)
            }
        }

        bitonicSort(array, 0, array.size, true)

        if (array.size > originalSize) {
            while (array.size > originalSize) {
                array.removeAt(array.lastIndex)
            }
        }
    }

    private fun bitonicSort(array: MutableList<Int>, low: Int, count: Int, ascending: Boolean) {
        if (count > 1) {
            val k = count / 2
            bitonicSort(array, low, k, true)
            bitonicSort(array, low + k, k, false)
            bitonicMerge(array, low, count, ascending)
        }
    }

    private fun bitonicMerge(array: MutableList<Int>, low: Int, count: Int, ascending: Boolean) {
        if (count > 1) {
            val k = count / 2
            for (i in low until low + k) {
                if (shouldSwap(array[i], array[i + k], ascending)) {
                    swap(array, i, i + k)
                    addSnapshot(array, listOf(i, i + k), "Bitonic swap")
                }
            }
            bitonicMerge(array, low, k, ascending)
            bitonicMerge(array, low + k, k, ascending)
        }
    }

    private fun shouldSwap(a: Int, b: Int, ascending: Boolean): Boolean {
        return if (ascending) compare(a, b) > 0 else compare(a, b) < 0
    }

    private fun nextPowerOfTwo(n: Int): Int {
        var power = 1
        while (power < n) {
            power = power shl 1
        }
        return power
    }
}
