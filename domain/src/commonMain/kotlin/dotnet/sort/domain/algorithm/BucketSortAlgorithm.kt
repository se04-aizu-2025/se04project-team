package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType
import kotlin.math.sqrt

/**
 * Bucket Sort アルゴリズム。
 *
 * 値の分布がある程度均一な場合に効果的な分布ソートです。
 * 範囲に応じてバケットに分配し、各バケットを個別に整列して結合します。
 */
class BucketSortAlgorithm : BaseSortAlgorithm() {

    override val type: SortType = SortType.BUCKET

    override val timeComplexity: String = "O(n + k)"

    override val spaceComplexity: String = "O(n + k)"

    override fun doSort(array: MutableList<Int>) {
        if (array.size < 2) return

        val minValue = array.minOrNull() ?: return
        val maxValue = array.maxOrNull() ?: return
        if (minValue == maxValue) return

        val range = maxValue - minValue + 1
        if (range <= 0) return

        val bucketCount = maxOf(1, sqrt(array.size.toDouble()).toInt())
        val buckets = List(bucketCount) { mutableListOf<Int>() }

        for (value in array) {
            val index = ((value - minValue) * bucketCount) / range
            buckets[index].add(value)
        }

        addSnapshot(array, emptyList(), "Distribute into buckets")

        for ((bucketIndex, bucket) in buckets.withIndex()) {
            if (bucket.size > 1) {
                bucket.sort()
            }
            addSnapshot(array, emptyList(), "Sort bucket ${bucketIndex + 1}")
        }

        var outputIndex = 0
        for ((bucketIndex, bucket) in buckets.withIndex()) {
            for (value in bucket) {
                array[outputIndex] = value
                addSnapshot(array, listOf(outputIndex), "Place $value from bucket ${bucketIndex + 1}")
                outputIndex++
            }
        }
    }
}
