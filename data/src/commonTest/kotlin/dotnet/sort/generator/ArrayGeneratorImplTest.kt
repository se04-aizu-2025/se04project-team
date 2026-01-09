package dotnet.sort.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ArrayGeneratorImplTest {

    private val generator = ArrayGeneratorImpl()

    // --- RANDOM ---

    @Test
    fun `generate RANDOM creates array of specified size`() {
        val result = generator.generate(10, ArrayGeneratorType.RANDOM)
        assertEquals(10, result.size)
    }

    @Test
    fun `generate RANDOM creates values within specified range`() {
        val range = 1..50
        val result = generator.generate(100, ArrayGeneratorType.RANDOM, range)
        assertTrue(result.all { it in range })
    }

    @Test
    fun `generate RANDOM returns empty list for size 0`() {
        val result = generator.generate(0, ArrayGeneratorType.RANDOM)
        assertTrue(result.isEmpty())
    }

    // --- ASCENDING ---

    @Test
    fun `generate ASCENDING creates sorted array`() {
        val result = generator.generate(10, ArrayGeneratorType.ASCENDING)
        assertEquals(result.sorted(), result)
    }

    @Test
    fun `generate ASCENDING creates array of specified size`() {
        val result = generator.generate(15, ArrayGeneratorType.ASCENDING)
        assertEquals(15, result.size)
    }

    // --- DESCENDING ---

    @Test
    fun `generate DESCENDING creates reverse sorted array`() {
        val result = generator.generate(10, ArrayGeneratorType.DESCENDING)
        assertEquals(result.sortedDescending(), result)
    }

    @Test
    fun `generate DESCENDING creates array of specified size`() {
        val result = generator.generate(15, ArrayGeneratorType.DESCENDING)
        assertEquals(15, result.size)
    }

    // --- PARTIALLY_SORTED ---

    @Test
    fun `generate PARTIALLY_SORTED creates array of specified size`() {
        val result = generator.generate(20, ArrayGeneratorType.PARTIALLY_SORTED)
        assertEquals(20, result.size)
    }

    @Test
    fun `generate PARTIALLY_SORTED creates values within range`() {
        val range = 10..90
        val result = generator.generate(50, ArrayGeneratorType.PARTIALLY_SORTED, range)
        assertTrue(result.all { it in range })
    }

    // --- DUPLICATES ---

    @Test
    fun `generate DUPLICATES creates array of specified size`() {
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES)
        assertEquals(30, result.size)
    }

    @Test
    fun `generate DUPLICATES contains duplicate values`() {
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES)
        val uniqueCount = result.toSet().size
        // 30 elements with ~10 unique values should have duplicates
        assertTrue(uniqueCount < result.size)
    }

    @Test
    fun `generate DUPLICATES creates values within range`() {
        val range = 1..50
        val result = generator.generate(30, ArrayGeneratorType.DUPLICATES, range)
        assertTrue(result.all { it in range })
    }

    // --- Default Range ---

    @Test
    fun `generate uses default range 1 to 100`() {
        val result = generator.generate(100, ArrayGeneratorType.RANDOM)
        assertTrue(result.all { it in 1..100 })
    }
}
