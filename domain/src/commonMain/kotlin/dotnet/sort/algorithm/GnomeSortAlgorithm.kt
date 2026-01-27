package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * Gnome Sort Algorithm.
 */
class GnomeSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.GNOME
    override val timeComplexity: String = "O(n^2)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return
        var index = 1
        while (index < array.size) {
            if (index == 0 || compare(array[index - 1], array[index]) <= 0) {
                index++
            } else {
                swap(array, index - 1, index)
                addSnapshot(array, listOf(index - 1, index), "Gnome swap")
                index--
            }
        }
    }
}
