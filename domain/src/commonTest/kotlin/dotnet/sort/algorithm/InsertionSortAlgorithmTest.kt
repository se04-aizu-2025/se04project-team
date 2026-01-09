package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class InsertionSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = InsertionSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.INSERTION, createAlgorithm().type)
    }
}
