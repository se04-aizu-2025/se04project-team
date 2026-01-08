package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * マージソートの実装クラス。
 *
 * 分割統治法を用いて配列をソートします。
 * 要素を交換（スワップ）するのではなく、値を上書きすることで整列を行うため、
 * swapCount は 0 になることが一般的ですが、可視化のために上書き操作をスナップショットとして記録します。
 *
 * - 時間計算量: O(n log n)
 * - 空間計算量: O(n) （再帰スタック + 一時配列）
 */
class MergeSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.MERGE
    override val timeComplexity = "O(n log n)"
    // 再帰呼び出しとマージ用の一時配列が必要
    override val spaceComplexity = "O(n)"

    override fun doSort(array: MutableList<Int>) {
        if (array.isNotEmpty()) {
            sort(array, 0, array.size - 1)
        }
    }

    private fun sort(array: MutableList<Int>, left: Int, right: Int) {
        if (left < right) {
            val middle = (left + right) / 2

            // Snapshot: Divide
            addSnapshot(array, listOf(middle), "Divining: left=$left, middle=$middle, right=$right")

            sort(array, left, middle)
            sort(array, middle + 1, right)

            merge(array, left, middle, right)
        }
    }

    private fun merge(array: MutableList<Int>, left: Int, middle: Int, right: Int) {
        val n1 = middle - left + 1
        val n2 = right - middle

        // 一時配列の作成
        val leftArray = IntArray(n1)
        val rightArray = IntArray(n2)

        for (i in 0 until n1) {
            leftArray[i] = array[left + i]
        }
        for (j in 0 until n2) {
            rightArray[j] = array[middle + 1 + j]
        }

        var i = 0
        var j = 0
        var k = left

        // Merge process
        while (i < n1 && j < n2) {
            // Snapshot: Comparing
            addSnapshot(array, listOf(left + i, middle + 1 + j), "Comparing merge elements")

            // compare(a, b) counts comparisons
            if (compare(leftArray[i], rightArray[j]) <= 0) {
                array[k] = leftArray[i]
                i++
            } else {
                array[k] = rightArray[j]
                j++
            }
            // Snapshot: Merged value written
            addSnapshot(array, listOf(k), "Merged value ${array[k]} at index $k")
            k++
        }

        // Copy remaining elements of leftArray
        while (i < n1) {
            array[k] = leftArray[i]
            addSnapshot(array, listOf(k), "Copying remaining left value ${array[k]}")
            i++
            k++
        }

        // Copy remaining elements of rightArray
        while (j < n2) {
            array[k] = rightArray[j]
            addSnapshot(array, listOf(k), "Copying remaining right value ${array[k]}")
            j++
            k++
        }
    }
}
