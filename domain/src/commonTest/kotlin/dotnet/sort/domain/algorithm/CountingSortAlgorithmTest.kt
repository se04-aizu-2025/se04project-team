package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class CountingSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = CountingSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.COUNTING, createAlgorithm().type)
    }

    @Test
    fun testHandlesNegativeValues() {
        val algorithm = createAlgorithm()
        val input = listOf(-2, 3, -1, 0, 2)
        val result = algorithm.sort(input)

        assertEquals(input.sorted(), result.finalArray)
    }
}
