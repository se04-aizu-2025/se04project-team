package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InsertionSortAlgorithmTest {

    @Test
    fun testInsertionSort() {
        val algorithm = InsertionSortAlgorithm()
        assertEquals(SortType.INSERTION, algorithm.type)

        val input = listOf(5, 2, 4, 6, 1, 3)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(1, 2, 3, 4, 5, 6), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        assertTrue(result.steps.isNotEmpty())
    }

    @Test
    fun testInsertionSortAlreadySorted() {
        val algorithm = InsertionSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Insertion sort checks array[j] < array[j-1]. If sorted, it breaks immediately.
        // It compares but never swaps.
        assertEquals(0L, result.complexityMetrics.swapCount)
    }

    @Test
    fun testInsertionSortReverseSorted() {
        val algorithm = InsertionSortAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
        // Max swaps
        assertTrue(result.complexityMetrics.swapCount > 0)
    }
}
