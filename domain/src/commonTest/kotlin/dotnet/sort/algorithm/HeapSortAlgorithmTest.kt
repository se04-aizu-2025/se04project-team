package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HeapSortAlgorithmTest {

    @Test
    fun testHeapSort() {
        val algorithm = HeapSortAlgorithm()
        assertEquals(SortType.HEAP, algorithm.type)

        val input = listOf(12, 11, 13, 5, 6, 7)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(5, 6, 7, 11, 12, 13), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        assertTrue(result.steps.isNotEmpty())
    }

    @Test
    fun testHeapSortAlreadySorted() {
        val algorithm = HeapSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Heap Sort builds Max Heap first. For sorted array [1,2,3,4,5], n=5.
        // i=1 (node 2). Left=3 (4), Right=4 (5). Largest=Right=4. Swap(1,4)->[1,5,3,4,2].
        // So yes, it swaps even if sorted to build max heap.
        assertTrue(result.complexityMetrics.swapCount > 0)
    }

    @Test
    fun testHeapSortReverseSorted() {
        val algorithm = HeapSortAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
        assertTrue(result.complexityMetrics.swapCount > 0)
    }
}
