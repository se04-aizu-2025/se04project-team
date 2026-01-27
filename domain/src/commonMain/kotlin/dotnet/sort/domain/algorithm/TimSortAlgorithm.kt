package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * Tim Sort アルゴリズム。
 *
 * 挿入ソートとマージソートを組み合わせたハイブリッドソートです。
 * 連続した昇順の run を活用し、実用的な入力で高速に動作します。
 * - 時間計算量: O(n log n) (best: O(n))
 * - 空間計算量: O(n)
 */
class TimSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.TIM

    override val timeComplexity: String = "O(n log n)"

    override val spaceComplexity: String = "O(n)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val n = array.size
        val minRun = 32

        var start = 0
        while (start < n) {
            val end = minOf(start + minRun - 1, n - 1)
            insertionSortRange(array, start, end)
            addSnapshot(array, listOf(start, end), "Sort run [$start,$end]")
            start += minRun
        }

        var size = minRun
        while (size < n) {
            var left = 0
            while (left < n) {
                val mid = left + size - 1
                val right = minOf(left + size * 2 - 1, n - 1)
                if (mid < right) {
                    merge(array, left, mid, right)
                }
                left += size * 2
            }
            size *= 2
        }
    }

    private fun insertionSortRange(array: MutableList<Int>, left: Int, right: Int) {
        for (i in left + 1..right) {
            var j = i
            while (j > left && compare(array[j], array[j - 1]) < 0) {
                swap(array, j, j - 1)
                addSnapshot(array, listOf(j - 1, j), "Swap ${array[j - 1]} and ${array[j]}")
                j--
            }
        }
    }

    private fun merge(array: MutableList<Int>, left: Int, mid: Int, right: Int) {
        val leftPart = array.subList(left, mid + 1).toList()
        val rightPart = array.subList(mid + 1, right + 1).toList()

        var i = 0
        var j = 0
        var k = left

        while (i < leftPart.size && j < rightPart.size) {
            if (compare(leftPart[i], rightPart[j]) <= 0) {
                array[k] = leftPart[i]
                i++
            } else {
                array[k] = rightPart[j]
                j++
            }
            k++
        }

        while (i < leftPart.size) {
            array[k] = leftPart[i]
            i++
            k++
        }

        while (j < rightPart.size) {
            array[k] = rightPart[j]
            j++
            k++
        }

        addSnapshot(array, listOf(left, right), "Merge runs [$left,$mid] and [${mid + 1},$right]")
    }
}
