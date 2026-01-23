package dotnet.sort.domain.quiz.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateQuizScoreUseCaseTest {

    private val useCase = CalculateQuizScoreUseCase()

    @Test
    fun `GIVEN incorrect answer WHEN invoke called THEN returns 0`() {
        val score = useCase(isCorrect = false, timeLeftSeconds = 10, currentCombo = 0)
        assertEquals(0, score)
    }

    @Test
    fun `GIVEN correct answer with no combo and max time WHEN invoke called THEN returns max base score`() {
        // Base: 10
        // Speed: 10 * 2 = 20 (Max)
        // Combo: (0 + 1) * 5 = 5
        // Total: 35
        val score = useCase(isCorrect = true, timeLeftSeconds = 10, currentCombo = 0)
        assertEquals(35, score)
    }

    @Test
    fun `GIVEN correct answer with max combo and max time WHEN invoke called THEN returns max possible score`() {
        // Base: 10
        // Speed: 10 * 2 = 20
        // Combo: (4 + 1) * 5 = 25 (Max)
        // Total: 55
        val score = useCase(isCorrect = true, timeLeftSeconds = 10, currentCombo = 4)
        assertEquals(55, score)
    }

    @Test
    fun `GIVEN correct answer with 0 time and 0 combo WHEN invoke called THEN returns base score plus combo`() {
        // Base: 10
        // Speed: 0
        // Combo: 5
        // Total: 15
        val score = useCase(isCorrect = true, timeLeftSeconds = 0, currentCombo = 0)
        assertEquals(15, score)
    }
    
    @Test
    fun `GIVEN correct answer with combo exceeding max WHEN invoke called THEN returns capped combo bonus`() {
        // Base: 10
        // Speed: 0
        // Combo: (10 + 1) -> capped at 5 -> 25
        // Total: 35
        val score = useCase(isCorrect = true, timeLeftSeconds = 0, currentCombo = 10)
        assertEquals(35, score)
    }
}
