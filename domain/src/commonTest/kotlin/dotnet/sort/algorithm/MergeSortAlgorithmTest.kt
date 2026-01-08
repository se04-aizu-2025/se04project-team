package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MergeSortAlgorithmTest {

    @Test
    fun testMergeSort() {
        val algorithm = MergeSortAlgorithm()
        assertEquals(SortType.MERGE, algorithm.type)

        val input = listOf(12, 11, 13, 5, 6, 7)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(5, 6, 7, 11, 12, 13), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Merge Sort typically doesn't use "swaps" in standard implementation unless visualized as such.
        // My implementation copies values, so swapCount remains 0.
        // If I wanted to track assignments as something else, I'd need a new metric.
        // For now, swapCount == 0 is correct for this impl.
        assertEquals(0L, result.complexityMetrics.swapCount)

        // Check steps
        assertTrue(result.steps.isNotEmpty())
    }

    @Test
    fun testMergeSortAlreadySorted() {
        val algorithm = MergeSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
    }

    @Test
    fun testMergeSortReverseSorted() {
        val algorithm = MergeSortAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
    }
}
