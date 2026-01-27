package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType
import kotlin.random.Random

/**
 * Bogo Sort アルゴリズム。
 *
 * ランダムにシャッフルして整列するまで繰り返す教育目的のソートです。
 * - 時間計算量: O((n+1)!)
 * - 空間計算量: O(1)
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
        while (!isSorted(array)) {
            shuffle(array)
            attempts++
            addSnapshot(array, emptyList(), "Shuffle attempt $attempts")

            if (attempts > 5000) {
                array.sort()
                addSnapshot(array, emptyList(), "Fallback sort after many shuffles")
                return
            }
        }
    }

    private fun shuffle(array: MutableList<Int>) {
        for (i in array.lastIndex downTo 1) {
            val j = Random.nextInt(i + 1)
            swap(array, i, j)
        }
    }

    private fun isSorted(array: MutableList<Int>): Boolean {
        for (i in 0 until array.lastIndex) {
            if (compare(array[i], array[i + 1]) > 0) return false
        }
        return true
    }
}
