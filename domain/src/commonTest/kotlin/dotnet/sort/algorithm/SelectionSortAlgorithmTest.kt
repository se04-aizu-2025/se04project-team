package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SelectionSortAlgorithmTest {

    @Test
    fun testSelectionSort() {
        val algorithm = SelectionSortAlgorithm()
        assertEquals(SortType.SELECTION, algorithm.type)

        val input = listOf(64, 25, 12, 22, 11)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(11, 12, 22, 25, 64), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Selection sort usually does swaps (max n-1), but definitely > 0 for this input
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        assertTrue(result.steps.isNotEmpty())
        assertEquals("Start", result.steps.first().description)
        assertEquals("Finished", result.steps.last().description)
    }

    @Test
    fun testSelectionSortAlreadySorted() {
        val algorithm = SelectionSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Selection sort (standard) does not swap if minIdx == i.
        // My implementation checks `if (minIdx != i)`.
        // So for sorted array, swapCount should be 0.
        assertEquals(0L, result.complexityMetrics.swapCount)
    }
}
