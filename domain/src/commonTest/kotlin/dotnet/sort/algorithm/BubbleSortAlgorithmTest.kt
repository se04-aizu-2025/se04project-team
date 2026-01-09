package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BubbleSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BubbleSortAlgorithm()
    
    @Test
    fun testType() {
        assertEquals(SortType.BUBBLE, createAlgorithm().type)
    }
}
