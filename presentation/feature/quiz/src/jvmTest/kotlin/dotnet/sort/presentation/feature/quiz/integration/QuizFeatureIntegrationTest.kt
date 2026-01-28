package dotnet.sort.presentation.feature.quiz.integration

import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.QuizScore
import dotnet.sort.model.ScorePeriod
import dotnet.sort.presentation.feature.quiz.QuizIntent
import dotnet.sort.presentation.feature.quiz.QuizViewModel
import dotnet.sort.repository.QuizScoreRepository
import dotnet.sort.usecase.ObserveQuizScoresByPeriodUseCase

import dotnet.sort.usecase.RecordQuizScoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Quiz機能の統合テスト。
 */
@OptIn(ExperimentalCoroutinesApi::class)
class QuizFeatureIntegrationTest {

    private lateinit var viewModel: QuizViewModel
    private lateinit var fakeRepository: FakeQuizScoreRepository

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeRepository = FakeQuizScoreRepository()
        val recordUseCase = RecordQuizScoreUseCase(fakeRepository)
        val observeByPeriodUseCase = ObserveQuizScoresByPeriodUseCase(fakeRepository)
        viewModel = QuizViewModel(recordUseCase, observeByPeriodUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Integration Test - Quiz Mode Selection`() = runTest {
        // Initial state
        assertEquals(QuizMode.SPEED_SWAP, viewModel.state.value.mode)

        // Change mode
        viewModel.send(QuizIntent.SelectMode(QuizMode.GUESS_ALGORITHM))
        advanceUntilIdle()

        assertEquals(QuizMode.GUESS_ALGORITHM, viewModel.state.value.mode)
    }

    @Test
    fun `Integration Test - Quiz Difficulty Selection`() = runTest {
        // Initial state
        assertEquals(QuizDifficulty.EASY, viewModel.state.value.difficulty)

        // Change difficulty
        viewModel.send(QuizIntent.SelectDifficulty(QuizDifficulty.HARD))
        advanceUntilIdle()

        assertEquals(QuizDifficulty.HARD, viewModel.state.value.difficulty)
    }

    @Test
    fun `Integration Test - Quiz Start and Answer Flow`() = runTest {
        // Start quiz
        viewModel.send(QuizIntent.StartQuiz)

        // Check initial running state immediately (before timer completes)
        assertTrue(viewModel.state.value.isRunning)
        assertEquals(0, viewModel.state.value.questionIndex)

        // Select an option
        viewModel.send(QuizIntent.SelectOption(0))

        assertEquals(0, viewModel.state.value.selectedIndex)

        // Submit answer
        viewModel.send(QuizIntent.SubmitAnswer)

        assertFalse(viewModel.state.value.isRunning)
        assertTrue(viewModel.state.value.isCorrect != null)
    }

    @Test
    fun `Integration Test - Score Period Filter`() = runTest {
        // Initial state
        assertEquals(ScorePeriod.ALL, viewModel.state.value.scorePeriod)

        // Change period
        viewModel.send(QuizIntent.SelectScorePeriod(ScorePeriod.WEEKLY))
        advanceUntilIdle()

        assertEquals(ScorePeriod.WEEKLY, viewModel.state.value.scorePeriod)
    }
}

/**
 * テスト用のFake QuizScoreRepository。
 */
private class FakeQuizScoreRepository : QuizScoreRepository {
    private val scores = MutableStateFlow<List<QuizScore>>(emptyList())

    override suspend fun recordScore(score: QuizScore) {
        scores.value = scores.value + score
    }

    override fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        scores.map { it.take(limit) }

    override fun observeScoresByPeriod(period: ScorePeriod, limit: Int): Flow<List<QuizScore>> =
        scores.map { it.take(limit) }
}
