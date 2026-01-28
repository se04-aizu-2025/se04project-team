package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * Counting Sort Algorithm.
 */
class CountingSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.COUNTING
    override val timeComplexity: String = "O(n + k)"
    override val spaceComplexity: String = "O(k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.isEmpty()) return

        var min = array.first()
        var max = array.first()
        for (value in array) {
            if (value < min) min = value
            if (value > max) max = value
        }

        val range = max - min + 1
        val counts = IntArray(range)
        array.forEach { value ->
            counts[value - min]++
        }

        addSnapshot(array, emptyList(), "Counted occurrences")

        var index = 0
        for (i in counts.indices) {
            val value = i + min
            repeat(counts[i]) {
                array[index] = value
                swapCount++
                addSnapshot(array, listOf(index), "Place $value")
                index++
            }
        }
    }
}
