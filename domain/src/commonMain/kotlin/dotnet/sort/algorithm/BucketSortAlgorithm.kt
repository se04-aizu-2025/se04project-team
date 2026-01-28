package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Bucket Sort Algorithm.
 */
class BucketSortAlgorithm : BaseSortAlgorithm() {
    override val type: SortType = SortType.BUCKET
    override val timeComplexity: String = "O(n + k)"
    override val spaceComplexity: String = "O(n + k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.isEmpty()) return

        val min = array.minOrNull() ?: return
        val maxValue = array.maxOrNull() ?: return
        val range = max(1, maxValue - min + 1)
        val bucketCount = max(1, sqrt(array.size.toDouble()).toInt())
        val buckets = List(bucketCount) { mutableListOf<Int>() }

        array.forEach { value ->
            val normalized = value - min
            val bucketIndex = ((normalized.toDouble() / range) * (bucketCount - 1)).toInt()
            buckets[bucketIndex].add(value)
        }

        var index = 0
        buckets.forEach { bucket ->
            insertionSort(bucket)
            bucket.forEach { value ->
                array[index] = value
                swapCount++
                addSnapshot(array, listOf(index), "Place $value")
                index++
            }
        }
    }

    private fun insertionSort(bucket: MutableList<Int>) {
        for (i in 1 until bucket.size) {
            val key = bucket[i]
            var j = i - 1
            while (j >= 0 && compare(bucket[j], key) > 0) {
                bucket[j + 1] = bucket[j]
                swapCount++
                j--
            }
            bucket[j + 1] = key
        }
    }
}
