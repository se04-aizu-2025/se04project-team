package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class GnomeSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = GnomeSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.GNOME, createAlgorithm().type)
    }
}
