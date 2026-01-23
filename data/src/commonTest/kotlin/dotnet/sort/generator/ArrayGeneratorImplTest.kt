package dotnet.sort.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import dotnet.sort.domain.generator.ArrayGeneratorType

class ArrayGeneratorImplTest {

    private val generator = ArrayGeneratorImpl()

    // --- RANDOM ---

    @Test
    fun `GIVEN RANDOM type WHEN generate is called THEN creates array of specified size`() {
        val result = generator.generate(10, ArrayGeneratorType.RANDOM)
        assertEquals(10, result.size)
    }

    @Test
    fun `GIVEN RANDOM type and range WHEN generate is called THEN creates values within specified range`() {
        val range = 1..50
        val result = generator.generate(100, ArrayGeneratorType.RANDOM, range)
        assertTrue(result.all { it in range })
    }

    @Test
    fun `GIVEN RANDOM type and size 0 WHEN generate is called THEN returns empty list`() {
        val result = generator.generate(0, ArrayGeneratorType.RANDOM)
        assertTrue(result.isEmpty())
    }

    // --- ASCENDING ---

    @Test
    fun `GIVEN ASCENDING type WHEN generate is called THEN creates sorted array`() {
        val result = generator.generate(10, ArrayGeneratorType.ASCENDING)
        assertEquals(result.sorted(), result)
    }

    @Test
    fun `GIVEN ASCENDING type WHEN generate is called THEN creates array of specified size`() {
        val result = generator.generate(15, ArrayGeneratorType.ASCENDING)
        assertEquals(15, result.size)
    }

    // --- DESCENDING ---

    @Test
    fun `GIVEN DESCENDING type WHEN generate is called THEN creates reverse sorted array`() {
        val result = generator.generate(10, ArrayGeneratorType.DESCENDING)
        assertEquals(result.sortedDescending(), result)
    }

    @Test
    fun `GIVEN DESCENDING type WHEN generate is called THEN creates array of specified size`() {
        val result = generator.generate(15, ArrayGeneratorType.DESCENDING)
        assertEquals(15, result.size)
    }

    // --- PARTIALLY_SORTED ---

    @Test
    fun `GIVEN PARTIALLY_SORTED type WHEN generate is called THEN creates array of specified size`() {
        val result = generator.generate(20, ArrayGeneratorType.PARTIALLY_SORTED)
        assertEquals(20, result.size)
    }

    @Test
    fun `GIVEN PARTIALLY_SORTED type and range WHEN generate is called THEN creates values within range`() {
        val range = 10..90
        val result = generator.generate(50, ArrayGeneratorType.PARTIALLY_SORTED, range)
        assertTrue(result.all { it in range })
    }

    // --- DUPLICATES ---

    @Test
    fun `GIVEN DUPLICATES type WHEN generate is called THEN creates array of specified size`() {
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES)
        assertEquals(30, result.size)
    }

    @Test
    fun `GIVEN DUPLICATES type WHEN generate is called THEN contains duplicate values`() {
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES)
        val uniqueCount = result.toSet().size
        // 30 elements with ~10 unique values should have duplicates
        assertTrue(uniqueCount < result.size)
    }

    @Test
    fun `GIVEN DUPLICATES type and range WHEN generate is called THEN creates values within range`() {
        val range = 1..50
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES, range)
        assertTrue(result.all { it in range })
    }

    // --- Default Range ---

    @Test
    fun `GIVEN no range WHEN generate is called THEN uses default range 1 to 100`() {
        val result = generator.generate(100, ArrayGeneratorType.RANDOM)
        assertTrue(result.all { it in 1..100 })
    }
}
