package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class BogoSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BogoSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.BOGO, createAlgorithm().type)
    }
}
