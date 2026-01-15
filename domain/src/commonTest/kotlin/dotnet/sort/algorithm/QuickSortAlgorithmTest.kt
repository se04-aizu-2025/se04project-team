package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class QuickSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = QuickSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.QUICK, createAlgorithm().type)
    }
}
