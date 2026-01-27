package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class OddEvenSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = OddEvenSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.ODD_EVEN, createAlgorithm().type)
    }
}
