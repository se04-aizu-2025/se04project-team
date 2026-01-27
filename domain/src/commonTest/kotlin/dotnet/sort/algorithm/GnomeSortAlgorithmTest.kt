package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class GnomeSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = GnomeSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.GNOME, createAlgorithm().type)
    }
}
