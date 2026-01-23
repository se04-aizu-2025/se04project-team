package dotnet.sort.domain.usecase

import dotnet.sort.domain.generator.ArrayGenerator
import dotnet.sort.domain.generator.ArrayGeneratorType
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
    fun `GIVEN size and type WHEN invoke is called THEN delegates to ArrayGenerator`() {
        val result = useCase(5, ArrayGeneratorType.RANDOM)
        assertEquals(5, result.size)
        assertEquals(listOf(1, 2, 3, 4, 5), result)
    }

    @Test
    fun `GIVEN size type and range WHEN invoke is called THEN delegates to ArrayGenerator`() {
        val result = useCase(3, ArrayGeneratorType.RANDOM, 10..20)
        assertEquals(3, result.size)
    }

    @Test
    fun `GIVEN size 0 WHEN invoke is called THEN returns empty list`() {
        val result = useCase(0, ArrayGeneratorType.RANDOM)
        assertEquals(emptyList(), result)
    }
}
