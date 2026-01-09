package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class ShellSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = ShellSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.SHELL, createAlgorithm().type)
    }
}
