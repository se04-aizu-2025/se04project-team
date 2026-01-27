package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class TimSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = TimSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.TIM, createAlgorithm().type)
    }
}
