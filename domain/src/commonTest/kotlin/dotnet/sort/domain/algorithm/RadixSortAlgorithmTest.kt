package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class RadixSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = RadixSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.RADIX, createAlgorithm().type)
    }

    @Test
    fun testHandlesNegativeValues() {
        val algorithm = createAlgorithm()
        val input = listOf(-10, 5, 0, -3, 8, -1)
        val result = algorithm.sort(input)

        assertEquals(input.sorted(), result.finalArray)
    }
}
