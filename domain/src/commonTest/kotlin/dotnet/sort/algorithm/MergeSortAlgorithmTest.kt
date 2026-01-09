package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class MergeSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = MergeSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.MERGE, createAlgorithm().type)
    }
}
