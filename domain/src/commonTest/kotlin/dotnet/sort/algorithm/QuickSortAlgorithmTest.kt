package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuickSortAlgorithmTest {

    @Test
    fun testQuickSort() {
        val algorithm = QuickSortAlgorithm()
        assertEquals(SortType.QUICK, algorithm.type)

        val input = listOf(10, 7, 8, 9, 1, 5)
        val result = algorithm.sort(input)

        // Check sorted array
        assertEquals(listOf(1, 5, 7, 8, 9, 10), result.finalArray)

        // Check metrics
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        assertTrue(result.complexityMetrics.swapCount > 0)

        // Check steps
        // Should contain pivot selection and swap snapshots
        assertTrue(result.steps.any { it.description.startsWith("Pivot selected") })
        // Could be empty if sorted already but for this input it won't be
        assertTrue(result.steps.isNotEmpty())
    }

    @Test
    fun testQuickSortAlreadySorted() {
        val algorithm = QuickSortAlgorithm()
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)

        assertEquals(input, result.finalArray)
        assertTrue(result.complexityMetrics.comparisonCount > 0)
        // Lomuto partition involves swaps even if sorted (swapping pivot to itself or near it?)
        // Actually, Lomuto performs worst on sorted array O(n^2) and might do swaps if implemented naively.
        // My impl:
        // Piv = 5. j=0..3. 1<5 ok i=0 (swap 0,0 - optimizing to avoid self swap in code: `if (i != j)`)
        // 2<5 ok i=1.
        // ...
        // Finally swap(i+1, high) -> swap(4, 4). Optimized `if (i+1 != high)`.
        // So expected swapCount should be 0 if fully optimized?
        // Let's check impl: `if (i != j)` check is present. `if (i+1 != high)` check is present.
        // So for fully sorted array, swapCount should be 0.
        
        // Wait, standard Lomuto does many comparisons.
        
        // Let's verify via test run. If it fails I'll adjust expectation or code logic.
    }

    @Test
    fun testQuickSortReverseSorted() {
        val algorithm = QuickSortAlgorithm()
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)

        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
        assertTrue(result.complexityMetrics.swapCount > 0)
    }
}
