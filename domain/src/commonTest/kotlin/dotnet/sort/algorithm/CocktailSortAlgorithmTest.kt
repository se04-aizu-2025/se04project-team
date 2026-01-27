package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class CocktailSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = CocktailSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.COCKTAIL, createAlgorithm().type)
    }
}
