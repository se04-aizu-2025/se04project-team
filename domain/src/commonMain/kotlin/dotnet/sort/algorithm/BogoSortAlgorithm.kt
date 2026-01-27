package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.random.Random

/**
 * Bogo Sort Algorithm (educational, limited input size).
 */
class BogoSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.BOGO
    override val timeComplexity: String = "O((n+1)!)"
    override val spaceComplexity: String = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return
        if (array.size > 8) {
            array.sort()
            addSnapshot(array, emptyList(), "Fallback sort for large input")
            return
        }

        var attempts = 0
        while (!isSorted(array) && attempts < 10_000) {
            array.shuffle(Random.Default)
            addSnapshot(array, emptyList(), "Shuffle")
            attempts++
        }
    }

    private fun isSorted(array: List<Int>): Boolean {
        for (i in 0 until array.lastIndex) {
            if (compare(array[i], array[i + 1]) > 0) {
                return false
            }
        }
        return true
    }
}
