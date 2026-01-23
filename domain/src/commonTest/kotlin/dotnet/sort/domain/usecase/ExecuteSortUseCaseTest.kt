package dotnet.sort.domain.usecase

import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExecuteSortUseCaseTest {

    private val useCase = ExecuteSortUseCase()

    @Test
    fun `GIVEN Bubble Sort type WHEN execute is called THEN returns sorted result using Bubble Sort`() {
        // Given
        val input = listOf(5, 3, 8, 1, 2)
        val type = SortType.BUBBLE

        // When
        val result = useCase.execute(type, input)

        // Then
        assertEquals(listOf(1, 2, 3, 5, 8), result.finalArray, "Array should be sorted")
        
        // バブルソートは必ず比較を行うため、メトリクスも確認
        assertTrue(result.complexityMetrics.comparisonCount > 0, "Comparison count should be greater than 0")
        assertEquals("O(n²)", result.complexityMetrics.timeComplexity)
    }

    @Test
    fun `GIVEN empty list WHEN execute is called THEN returns empty result`() {
        // Given
        val input = emptyList<Int>()
        val type = SortType.BUBBLE

        // When
        val result = useCase.execute(type, input)

        // Then
        assertTrue(result.finalArray.isEmpty())
        assertEquals(0, result.complexityMetrics.comparisonCount)
    }

    @Test
    fun `GIVEN already sorted list WHEN execute is called THEN returns sorted list`() {
        // Given
        val input = listOf(1, 2, 3, 4, 5)
        val type = SortType.BUBBLE

        // When
        val result = useCase.execute(type, input)

        // Then
        assertEquals(input, result.finalArray)
    }

    @Test
    fun `GIVEN reverse sorted list WHEN execute is called THEN returns sorted list`() {
        // Given
        val input = listOf(5, 4, 3, 2, 1)
        val type = SortType.BUBBLE

        // When
        val result = useCase.execute(type, input)

        // Then
        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
    }
}
