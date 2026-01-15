package dotnet.sort.algorithm

import dotnet.sort.model.SortType

/**
 * ヒープソートの実装クラス。
 *
 * 配列を最大ヒープ（Max Heap）として構築し、
 * ルート要素（最大値）を配列の末尾と交換してヒープサイズを減らしていくことでソートを行います。
 *
 * - 時間計算量: O(n log n)
 * - 空間計算量: O(1)
 */
class HeapSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.HEAP
    override val timeComplexity = "O(n log n)"
    override val spaceComplexity = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        val n = array.size

        // Build heap (rearrange array)
        for (i in n / 2 - 1 downTo 0) {
            heapify(array, n, i)
        }

        // One by one extract an element from heap
        for (i in n - 1 downTo 0) {
            // Move current root to end
            swap(array, 0, i)
            // Snapshot: Extract Max
            addSnapshot(array, listOf(i), "Moved max value ${array[i]} to end (index $i)")

            // Call max heapify on the reduced heap
            heapify(array, i, 0)
        }
    }

    // To heapify a subtree rooted with node i which is an index in array[]. n is size of heap
    private fun heapify(array: MutableList<Int>, n: Int, i: Int) {
        var largest = i // Initialize largest as root
        val l = 2 * i + 1 // left = 2*i + 1
        val r = 2 * i + 2 // right = 2*i + 2

        // If left child is larger than root
        if (l < n && compare(array[l], array[largest]) > 0) {
            largest = l
        }

        // If right child is larger than largest so far
        if (r < n && compare(array[r], array[largest]) > 0) {
            largest = r
        }

        // If largest is not root
        if (largest != i) {
            swap(array, i, largest)
            // Snapshot: Heapify Swap
            addSnapshot(array, listOf(i, largest), "Heapify: Swapped parent $i with child $largest")

            // Recursively heapify the affected sub-tree
            heapify(array, n, largest)
        }
    }
}
