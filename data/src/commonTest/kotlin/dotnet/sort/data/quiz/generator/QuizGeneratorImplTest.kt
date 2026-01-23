package dotnet.sort.data.quiz.generator

import dotnet.sort.domain.quiz.generator.QuizGenerator
import dotnet.sort.domain.generator.ArrayGenerator
import dotnet.sort.domain.generator.ArrayGeneratorType
import dotnet.sort.domain.model.SortType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuizGeneratorImplTest {

    private lateinit var quizGenerator: QuizGenerator
    private lateinit var fakeArrayGenerator: FakeArrayGenerator

    class FakeArrayGenerator : ArrayGenerator {
        override fun generate(size: Int, type: ArrayGeneratorType): List<Int> {
            // Return a fixed array that we know will require sorting
            // [2, 1, 3] -> Bubble Sort will swap 2 and 1.
            return listOf(2, 1, 3)
        }

        override fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> {
             return listOf(2, 1, 3)
        }
    }

    @BeforeTest
    fun setup() {
        fakeArrayGenerator = FakeArrayGenerator()
        quizGenerator = QuizGeneratorImpl(fakeArrayGenerator)
    }

    @Test
    fun `generate returns a valid question with correct indices`() {
        // Use SortType.BUBBLE as per enum definition
        val question = quizGenerator.generate(SortType.BUBBLE, 3)
        
        // Assert ID format
        assertTrue(question.id.startsWith("quiz_"), "ID should start with quiz_")
        
        // Assert Algorithm Type
        assertEquals(SortType.BUBBLE, question.algorithmType)
        
        // With input [2, 1, 3], BubbleSort first compares 2 and 1, then swaps them.
        // Step 0: [2, 1, 3] (Start)
        // Step 1: [2, 1, 3] (Compare 0, 1)
        // Step 2: [1, 2, 3] (Swap 0, 1) -> This is a swap step!
        
        // The generator picks a random swap step. 
        // In this simple case, there might be only one swap (2 <-> 1).
        // Or if it continues comparing (2,3), no swap. (1,3) no swap.
        
        // So the swap should be indices (0, 1).
        // Note: SortAlgorithm implementations might vary slightly, but standard bubble sort on [2,1,3] should swap first two.
        
        assertEquals(0 to 1, question.correctIndices, "Should identify the swap at indices 0 and 1")
    }
}
