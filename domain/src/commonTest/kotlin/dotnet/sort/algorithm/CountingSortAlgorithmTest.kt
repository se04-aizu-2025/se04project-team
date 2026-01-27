package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class CountingSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = CountingSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.COUNTING, createAlgorithm().type)
    }
}
