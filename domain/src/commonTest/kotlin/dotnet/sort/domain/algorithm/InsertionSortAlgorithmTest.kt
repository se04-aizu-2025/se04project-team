package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class InsertionSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = InsertionSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.INSERTION, createAlgorithm().type)
    }
}
