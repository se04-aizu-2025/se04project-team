package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class BitonicSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BitonicSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.BITONIC, createAlgorithm().type)
    }
}
