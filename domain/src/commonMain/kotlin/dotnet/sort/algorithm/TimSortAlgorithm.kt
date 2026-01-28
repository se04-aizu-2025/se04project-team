package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.math.min

/**
 * Tim Sort Algorithm (simplified).
 */
class TimSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.TIM
    override val timeComplexity: String = "O(n log n)"
    override val spaceComplexity: String = "O(n)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val run = 32
        var start = 0
        while (start < array.size) {
            val end = min(start + run - 1, array.lastIndex)
            insertionSort(array, start, end)
            start += run
        }

        var size = run
        while (size < array.size) {
            var left = 0
            while (left < array.size) {
                val mid = min(left + size - 1, array.lastIndex)
                val right = min(left + 2 * size - 1, array.lastIndex)
                if (mid < right) {
                    merge(array, left, mid, right)
                }
                left += 2 * size
            }
            size *= 2
        }
    }

    private fun insertionSort(array: MutableList<Int>, left: Int, right: Int) {
        for (i in left + 1..right) {
            val key = array[i]
            var j = i - 1
            while (j >= left && compare(array[j], key) > 0) {
                array[j + 1] = array[j]
                swapCount++
                j--
            }
            array[j + 1] = key
        }
        addSnapshot(array, listOf(left, right), "Run sorted")
    }

    private fun merge(array: MutableList<Int>, left: Int, mid: Int, right: Int) {
        val leftSize = mid - left + 1
        val rightSize = right - mid
        val leftArray = IntArray(leftSize)
        val rightArray = IntArray(rightSize)
        for (i in 0 until leftSize) {
            leftArray[i] = array[left + i]
        }
        for (j in 0 until rightSize) {
            rightArray[j] = array[mid + 1 + j]
        }

        var i = 0
        var j = 0
        var k = left
        while (i < leftSize && j < rightSize) {
            if (compare(leftArray[i], rightArray[j]) <= 0) {
                array[k] = leftArray[i]
                i++
            } else {
                array[k] = rightArray[j]
                j++
            }
            swapCount++
            k++
        }

        while (i < leftSize) {
            array[k] = leftArray[i]
            i++
            k++
            swapCount++
        }

        while (j < rightSize) {
            array[k] = rightArray[j]
            j++
            k++
            swapCount++
        }
        addSnapshot(array, listOf(left, right), "Merged")
    }
}
