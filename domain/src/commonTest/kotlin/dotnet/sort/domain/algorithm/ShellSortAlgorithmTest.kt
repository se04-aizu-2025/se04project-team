package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class ShellSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = ShellSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.SHELL, createAlgorithm().type)
    }
}
