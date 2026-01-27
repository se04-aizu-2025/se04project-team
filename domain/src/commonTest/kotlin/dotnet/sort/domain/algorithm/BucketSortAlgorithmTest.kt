package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class BucketSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BucketSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.BUCKET, createAlgorithm().type)
    }

    @Test
    fun testHandlesNegativeValues() {
        val algorithm = createAlgorithm()
        val input = listOf(-5, -10, 0, 3, 2, -1)
        val result = algorithm.sort(input)

        assertEquals(input.sorted(), result.finalArray)
    }
}
