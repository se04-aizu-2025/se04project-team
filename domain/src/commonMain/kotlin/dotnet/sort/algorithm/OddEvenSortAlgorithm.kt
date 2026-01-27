package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * Odd-Even Sort Algorithm.
 */
class OddEvenSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.ODD_EVEN
    override val timeComplexity: String = "O(n^2)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return
        var sorted = false
        while (!sorted) {
            sorted = true
            var i = 1
            while (i < array.size - 1) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Odd-even swap")
                    sorted = false
                }
                i += 2
            }
            i = 0
            while (i < array.size - 1) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Odd-even swap")
                    sorted = false
                }
                i += 2
            }
        }
    }
}
