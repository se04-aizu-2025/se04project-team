package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class BaseSortAlgorithmTest {

    protected abstract fun createAlgorithm(): SortAlgorithm

    @Test
    fun testStandardSort() {
        val algorithm = createAlgorithm()
        val input = listOf(5, 3, 8, 1, 2, 9, 4, 7, 6)
        val result = algorithm.sort(input)

        val expected = input.sorted()
        assertEquals(expected, result.finalArray)
        
        // Metrics check
        assertTrue(result.complexityMetrics.comparisonCount >= 0)
        assertTrue(result.complexityMetrics.swapCount >= 0)
        assertTrue(result.steps.isNotEmpty())
        assertEquals("Start", result.steps.first().description)
        assertEquals("Finished", result.steps.last().description)
    }

    @Test
    fun testAlreadySorted() {
        val algorithm = createAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
    }

    @Test
    fun testReverseSorted() {
        val algorithm = createAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
    }

    @Test
    fun testEmptyArray() {
        val algorithm = createAlgorithm()
        val input = emptyList<Int>()
        val result = algorithm.sort(input)

        assertTrue(result.finalArray.isEmpty())
    }

    @Test
    fun testSingleElement() {
        val algorithm = createAlgorithm()
        val input = listOf(42)
        val result = algorithm.sort(input)

        assertEquals(listOf(42), result.finalArray)
    }

    @Test
    fun testDuplicates() {
        val algorithm = createAlgorithm()
        val input = listOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5)
        val result = algorithm.sort(input)

        val expected = input.sorted()
        assertEquals(expected, result.finalArray)
    }
}
