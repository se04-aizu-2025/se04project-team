package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.math.max

/**
 * Comb Sort Algorithm.
 */
class CombSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.COMB
    override val timeComplexity: String = "O(n^2)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return
        var gap = array.size
        val shrink = 1.3
        var swapped = true

        while (gap > 1 || swapped) {
            gap = max(1, (gap / shrink).toInt())
            swapped = false
            for (i in 0 until array.size - gap) {
                if (compare(array[i], array[i + gap]) > 0) {
                    swap(array, i, i + gap)
                    addSnapshot(array, listOf(i, i + gap), "Comb swap")
                    swapped = true
                }
            }
        }
    }
}
