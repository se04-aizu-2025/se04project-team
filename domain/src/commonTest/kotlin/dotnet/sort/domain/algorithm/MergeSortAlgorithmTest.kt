package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class MergeSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = MergeSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.MERGE, createAlgorithm().type)
    }
}
