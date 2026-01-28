package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class OddEvenSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = OddEvenSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.ODD_EVEN, createAlgorithm().type)
    }
}
