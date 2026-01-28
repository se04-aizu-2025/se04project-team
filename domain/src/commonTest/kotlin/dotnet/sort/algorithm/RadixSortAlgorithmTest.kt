package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class RadixSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = RadixSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.RADIX, createAlgorithm().type)
    }
}
