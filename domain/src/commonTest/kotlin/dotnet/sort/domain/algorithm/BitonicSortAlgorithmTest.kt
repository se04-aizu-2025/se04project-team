package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class BitonicSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BitonicSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.BITONIC, createAlgorithm().type)
    }
}
