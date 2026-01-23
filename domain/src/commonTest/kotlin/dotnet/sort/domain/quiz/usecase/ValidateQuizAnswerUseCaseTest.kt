package dotnet.sort.domain.quiz.usecase

import dotnet.sort.domain.quiz.model.QuizFeedback
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidateQuizAnswerUseCaseTest {

    private val useCase = ValidateQuizAnswerUseCase()

    @Test
    fun `GIVEN exact match WHEN invoke called THEN returns Correct`() {
        val valid = 1 to 2
        val user = 1 to 2
        assertEquals(QuizFeedback.Correct(0), useCase(valid, user))
    }

    @Test
    fun `GIVEN reversed pair WHEN invoke called THEN returns Correct`() {
        // Swap order logic validation
        val valid = 1 to 2
        val user = 2 to 1
        assertEquals(QuizFeedback.Correct(0), useCase(valid, user))
    }

    @Test
    fun `GIVEN incorrect pair WHEN invoke called THEN returns Incorrect with valid answer`() {
        val valid = 1 to 2
        val user = 3 to 4
        val result = useCase(valid, user)
        
        assertTrue(result is QuizFeedback.Incorrect)
        assertEquals(valid, result.correctIndices)
    }
}
