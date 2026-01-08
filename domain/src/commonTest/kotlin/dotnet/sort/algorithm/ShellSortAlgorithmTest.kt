package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShellSortAlgorithmTest {

    @Test
    fun testShellSort() {
        val algorithm = ShellSortAlgorithm()
        assertEquals(SortType.SHELL, algorithm.type)

        val input = listOf(64, 34, 25, 12, 22, 11, 90)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(11, 12, 22, 25, 34, 64, 90), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        // Should contain gap snapshots
        assertTrue(result.steps.any { it.description.startsWith("Gap set to") })
        // Should contain swap snapshots
        assertTrue(result.steps.any { it.description.startsWith("Swapped") })
    }

    @Test
    fun testShellSortAlreadySorted() {
        val algorithm = ShellSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Shell Sort (Insertion based) checks but doesn't swap if stored.
        assertEquals(0L, result.complexityMetrics.swapCount)
    }

    @Test
    fun testShellSortReverseSorted() {
        val algorithm = ShellSortAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
        assertTrue(result.complexityMetrics.swapCount > 0)
    }
}
