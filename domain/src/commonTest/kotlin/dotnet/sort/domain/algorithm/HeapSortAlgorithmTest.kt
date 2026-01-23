package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class HeapSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = HeapSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.HEAP, createAlgorithm().type)
    }
}
