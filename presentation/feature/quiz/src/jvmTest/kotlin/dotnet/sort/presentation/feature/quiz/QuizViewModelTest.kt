package dotnet.sort.presentation.feature.quiz

import app.cash.turbine.test
import dotnet.sort.domain.quiz.usecase.GenerateQuizQuestionUseCase
import dotnet.sort.domain.quiz.model.QuizQuestion
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.quiz.generator.QuizGenerator
import dotnet.sort.domain.quiz.model.QuizFeedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * QuizViewModelのテスト。
 */
@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: QuizViewModel
    private lateinit var generateQuizQuestionUseCase: GenerateQuizQuestionUseCase
    private lateinit var fakeQuizGenerator: QuizGenerator

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock QuizGenerator
        fakeQuizGenerator = object : QuizGenerator {
            override fun generate(type: SortType, arraySize: Int): QuizQuestion {
                return QuizQuestion(
                    id = "test_id",
                    algorithmType = type,
                    currentArray = listOf(2, 1, 3),
                    correctIndices = 0 to 1,
                    timeLimitSeconds = 10
                )
            }
        }
        
        
        generateQuizQuestionUseCase = GenerateQuizQuestionUseCase(fakeQuizGenerator)
        val validateQuizAnswerUseCase = dotnet.sort.domain.quiz.usecase.ValidateQuizAnswerUseCase()
        viewModel = QuizViewModel(generateQuizQuestionUseCase, validateQuizAnswerUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `初期状態はローディングなし`() =
        runTest(testDispatcher) {
            viewModel.state.test {
                val state = awaitItem()
                assertFalse(state.isLoading, "初期状態ではローディングしていないこと")
            }
        }

    @Test
    fun `初期状態の確認`() =
        runTest(testDispatcher) {
            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(
                    expected = QuizState(isLoading = false),
                    actual = initialState,
                    message = "初期状態が正しいこと",
                )
            }
        }

    @Test
    fun `StartGame initializes game state`() =
        runTest(testDispatcher) {
             viewModel.state.test {
                awaitItem() // Initial state

                // Trigger StartGame intent
                viewModel.send(QuizIntent.StartGame)

                // 1. State update in startGame() (isGameActive=true, currentQuestion=null)
                val intermediateState = awaitItem()
                assertTrue(intermediateState.isGameActive, "Game should be active in intermediate state")

                // 2. State update in nextQuestion() (currentQuestion=...)
                val activeState = awaitItem()
                assertNotNull(activeState.currentQuestion, "Question should be generated")
                assertEquals(0, activeState.score, "Initial score should be 0")
            }
        }

    @Test
    fun `SubmitAnswer updates score and feedback`() =
        runTest(testDispatcher) {
             viewModel.state.test {
                awaitItem() // Initial
                viewModel.send(QuizIntent.StartGame)
                
                awaitItem() // Intermediate (Active, no question)
                val questionState = awaitItem() // Active, Question generated
                
                val question = questionState.currentQuestion!!
                val correctIds = question.correctIndices
                
                // Submit correct answer
                viewModel.send(QuizIntent.SubmitAnswer(correctIds))
                
                // SubmitAnswer updates state once
                val resultState = awaitItem()
                assertEquals(10, resultState.score, "Score should increment on correct answer")
                
                // Also verify feedback is Correct
                assertTrue(resultState.feedback is QuizFeedback.Correct, "Feedback should be Correct")
             }
        }
}
