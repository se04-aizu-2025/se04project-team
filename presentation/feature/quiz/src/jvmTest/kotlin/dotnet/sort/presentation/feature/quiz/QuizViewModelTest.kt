package dotnet.sort.presentation.feature.quiz

import app.cash.turbine.test
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

/**
 * QuizViewModelのテスト。
 */
@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: QuizViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = QuizViewModel()
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
}
