package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class CombSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = CombSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.COMB, createAlgorithm().type)
    }
}
