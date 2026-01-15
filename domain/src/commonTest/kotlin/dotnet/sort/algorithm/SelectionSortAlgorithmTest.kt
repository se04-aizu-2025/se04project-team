package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectionSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = SelectionSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.SELECTION, createAlgorithm().type)
    }
}
