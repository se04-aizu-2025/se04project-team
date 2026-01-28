package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType
import kotlin.test.Test
import kotlin.test.assertEquals

class BucketSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm(): SortAlgorithm = BucketSortAlgorithm()

    @Test
    fun testType() {
        assertEquals(SortType.BUCKET, createAlgorithm().type)
    }
}
