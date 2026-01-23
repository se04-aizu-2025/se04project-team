package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class QuickSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = QuickSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.QUICK, createAlgorithm().type)
    }
}
