package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class HeapSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = HeapSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.HEAP, createAlgorithm().type)
    }
}
