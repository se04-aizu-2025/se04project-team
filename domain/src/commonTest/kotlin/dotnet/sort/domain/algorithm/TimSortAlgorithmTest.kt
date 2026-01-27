package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class TimSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = TimSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.TIM, createAlgorithm().type)
    }
}
