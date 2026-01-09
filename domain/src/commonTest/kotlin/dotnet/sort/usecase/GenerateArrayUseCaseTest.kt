package dotnet.sort.usecase

import dotnet.sort.generator.ArrayGenerator
import dotnet.sort.generator.ArrayGeneratorType
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateArrayUseCaseTest {

    private val mockGenerator = object : ArrayGenerator {
        override fun generate(size: Int, type: ArrayGeneratorType): List<Int> {
            return List(size) { it + 1 }
        }

        override fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> {
            return List(size) { range.first + (it % (range.last - range.first + 1)) }
        }
    }

    private val useCase = GenerateArrayUseCase(mockGenerator)

    @Test
    fun `invoke with size and type delegates to ArrayGenerator`() {
        val result = useCase(5, ArrayGeneratorType.RANDOM)
        assertEquals(5, result.size)
        assertEquals(listOf(1, 2, 3, 4, 5), result)
    }

    @Test
    fun `invoke with size type and range delegates to ArrayGenerator`() {
        val result = useCase(3, ArrayGeneratorType.RANDOM, 10..20)
        assertEquals(3, result.size)
    }

    @Test
    fun `invoke returns empty list for size 0`() {
        val result = useCase(0, ArrayGeneratorType.RANDOM)
        assertEquals(emptyList(), result)
    }
}
