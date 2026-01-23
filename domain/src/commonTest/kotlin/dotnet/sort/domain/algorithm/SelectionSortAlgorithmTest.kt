package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectionSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = SelectionSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.SELECTION, createAlgorithm().type)
    }
}
