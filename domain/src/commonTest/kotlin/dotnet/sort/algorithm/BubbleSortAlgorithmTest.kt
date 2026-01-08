package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BubbleSortAlgorithmTest {

    @Test
    fun testBubbleSort() {
        val algorithm = BubbleSortAlgorithm()
        assertEquals(SortType.BUBBLE, algorithm.type)

        val input = listOf(5, 3, 8, 1, 2)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(1, 2, 3, 5, 8), result.finalArray)

        // Check metrics
        // (5, 3, 8, 1, 2)
        // 5,3 -> 3,5
        // 5,8 -> no
        // 8,1 -> 1,8
        // 8,2 -> 2,8
        // ...
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        assertTrue(result.steps.isNotEmpty())
        assertEquals("Start", result.steps.first().description)
        assertEquals("Finished", result.steps.last().description)
    }

    @Test
    fun testBubbleSortAlreadySorted() {
        val algorithm = BubbleSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Bubble sort without early exit flag (standard implementation) compares but does not swap
        assertEquals(0L, result.complexityMetrics.swapCount)
    }
}
