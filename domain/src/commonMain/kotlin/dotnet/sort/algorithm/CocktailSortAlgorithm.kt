package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * Cocktail Sort Algorithm.
 */
class CocktailSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.COCKTAIL
    override val timeComplexity: String = "O(n^2)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        var start = 0
        var end = array.lastIndex
        var swapped = true
        while (swapped) {
            swapped = false
            for (i in start until end) {
                if (compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1)
                    addSnapshot(array, listOf(i, i + 1), "Swap forward")
                    swapped = true
                }
            }
            if (!swapped) break
            swapped = false
            end--
            for (i in end downTo start + 1) {
                if (compare(array[i - 1], array[i]) > 0) {
                    swap(array, i - 1, i)
                    addSnapshot(array, listOf(i - 1, i), "Swap backward")
                    swapped = true
                }
            }
            start++
        }
    }
}
