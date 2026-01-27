package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class CocktailSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = CocktailSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.COCKTAIL, createAlgorithm().type)
    }
}
