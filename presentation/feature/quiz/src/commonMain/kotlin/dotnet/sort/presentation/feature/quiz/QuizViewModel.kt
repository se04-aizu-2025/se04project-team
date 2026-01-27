package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class QuizDifficulty {
    EASY,
    MEDIUM,
    HARD,
}

enum class QuizMode {
    SPEED_SWAP,
    GUESS_ALGORITHM,
}

data class QuizQuestion(
    val prompt: StringResource,
    val options: List<StringResource>,
    val correctIndex: Int,
    val hint: StringResource? = null,
)

data class QuizScoreEntry(
    val difficulty: QuizDifficulty,
    val mode: QuizMode,
    val score: Int,
    val correctCount: Int,
    val totalQuestions: Int,
)

data class QuizState(
    val difficulty: QuizDifficulty = QuizDifficulty.EASY,
    val mode: QuizMode = QuizMode.SPEED_SWAP,
    val questionIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = 5,
    val longestStreak: Int = 0,
    val isRunning: Boolean = false,
    val timeLimitMs: Long = 10_000,
    val timeRemainingMs: Long = 10_000,
    val question: QuizQuestion = questionsFor(QuizMode.SPEED_SWAP, QuizDifficulty.EASY).first(),
    val selectedIndex: Int? = null,
    val isCorrect: Boolean? = null,
    val showHint: Boolean = false,
    val showSummary: Boolean = false,
    val leaderboard: List<QuizScoreEntry> = emptyList(),
) : UiState

sealed class QuizIntent : Intent {
    data class SelectMode(val mode: QuizMode) : QuizIntent()
    data class SelectDifficulty(val difficulty: QuizDifficulty) : QuizIntent()
    data class SelectOption(val index: Int) : QuizIntent()
    data object StartQuiz : QuizIntent()
    data object SubmitAnswer : QuizIntent()
    data object NextQuestion : QuizIntent()
    data object ToggleHint : QuizIntent()
}

@Factory
class QuizViewModel : BaseViewModel<QuizState, QuizIntent>(QuizState()) {
    override fun send(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.SelectMode -> updateState {
                copy(mode = intent.mode, showSummary = false, showHint = false)
            }
            is QuizIntent.SelectDifficulty -> updateState {
                copy(
                    difficulty = intent.difficulty,
                    questionIndex = 0,
                    score = 0,
                    streak = 0,
                    correctCount = 0,
                    longestStreak = 0,
                    question = questionsFor(mode = state.value.mode, difficulty = intent.difficulty).first(),
                    selectedIndex = null,
                    isCorrect = null,
                    showHint = false,
                    showSummary = false,
                )
            }
            is QuizIntent.SelectOption -> updateState { copy(selectedIndex = intent.index) }
            QuizIntent.StartQuiz -> startQuiz()
            QuizIntent.SubmitAnswer -> {
                submitAnswer()
            }
            QuizIntent.NextQuestion -> {
                moveToNextQuestion()
            }
            QuizIntent.ToggleHint -> updateState { copy(showHint = !showHint) }
        }
    }

    private fun startQuiz() {
        val questions = questionsFor(state.value.mode, state.value.difficulty)
        updateState {
            copy(
                isRunning = true,
                showSummary = false,
                questionIndex = 0,
                question = questions.first(),
                timeRemainingMs = timeLimitForDifficulty(state.value.difficulty),
                timeLimitMs = timeLimitForDifficulty(state.value.difficulty),
                selectedIndex = null,
                isCorrect = null,
                showHint = false,
            )
        }
        startTimer()
    }

    private fun startTimer() {
        execute {
            while (state.value.isRunning && state.value.timeRemainingMs > 0) {
                delay(100)
                updateState {
                    copy(timeRemainingMs = (timeRemainingMs - 100).coerceAtLeast(0))
                }
            }
            if (state.value.isRunning && state.value.timeRemainingMs == 0L) {
                updateState { copy(isRunning = false, isCorrect = false, streak = 0, showHint = false) }
            }
        }
    }

    private fun submitAnswer() {
        val selected = state.value.selectedIndex
        val isCorrect = selected == state.value.question.correctIndex
        val newStreak = if (isCorrect) state.value.streak + 1 else 0
        val newScore = if (isCorrect) {
            val timeBonus = (state.value.timeRemainingMs / 200).toInt()
            state.value.score + 10 + newStreak + timeBonus
        } else {
            state.value.score
        }
        val newCorrectCount = if (isCorrect) state.value.correctCount + 1 else state.value.correctCount
        val newLongestStreak = maxOf(state.value.longestStreak, newStreak)
        updateState {
            copy(
                isCorrect = isCorrect,
                streak = newStreak,
                score = newScore,
                correctCount = newCorrectCount,
                longestStreak = newLongestStreak,
                isRunning = false,
                showHint = false,
            )
        }
    }

    private fun moveToNextQuestion() {
        val questions = questionsFor(state.value.mode, state.value.difficulty)
        val nextIndex = state.value.questionIndex + 1
        if (nextIndex >= state.value.totalQuestions) {
            updateState {
                copy(
                    showSummary = true,
                    leaderboard = leaderboard + QuizScoreEntry(
                        difficulty = difficulty,
                        mode = mode,
                        score = score,
                        correctCount = correctCount,
                        totalQuestions = totalQuestions,
                    ),
                )
            }
            return
        }

        val question = questions[nextIndex % questions.size]
        updateState {
            copy(
                questionIndex = nextIndex,
                question = question,
                selectedIndex = null,
                isCorrect = null,
                timeRemainingMs = timeLimitForDifficulty(difficulty),
                timeLimitMs = timeLimitForDifficulty(difficulty),
                isRunning = true,
                showHint = false,
            )
        }
        startTimer()
    }
}

private fun questionsFor(mode: QuizMode, difficulty: QuizDifficulty): List<QuizQuestion> {
    val base = when (mode) {
        QuizMode.SPEED_SWAP -> listOf(
            QuizQuestion(
                prompt = Res.string.quiz_q_bubble_next_swap,
                options = listOf(Res.string.quiz_q_check_pair_0_1, Res.string.quiz_q_check_pair_1_2, Res.string.quiz_q_check_pair_2_3),
                correctIndex = 0,
            ),
            QuizQuestion(
                prompt = Res.string.quiz_q_selection_move_end,
                options = listOf(Res.string.quiz_opt_min, Res.string.quiz_opt_max, Res.string.quiz_opt_middle),
                correctIndex = 1,
            ),
        )
        QuizMode.GUESS_ALGORITHM -> listOf(
            QuizQuestion(
                prompt = Res.string.quiz_q_pivot_partition,
                options = listOf(Res.string.algo_quick, Res.string.algo_merge, Res.string.algo_heap),
                correctIndex = 0,
                hint = Res.string.quiz_hint_n_log_n_avg,
            ),
            QuizQuestion(
                prompt = Res.string.quiz_q_heap_extract,
                options = listOf(Res.string.algo_heap, Res.string.algo_shell, Res.string.algo_insertion),
                correctIndex = 0,
                hint = Res.string.quiz_hint_binary_heap,
            ),
        )
    }

    return when (difficulty) {
        QuizDifficulty.EASY -> base
        QuizDifficulty.MEDIUM -> base + QuizQuestion(
            prompt = Res.string.quiz_q_divide_merge,
            options = listOf(Res.string.algo_merge, Res.string.algo_bubble, Res.string.algo_selection),
            correctIndex = 0,
        )
        QuizDifficulty.HARD -> base + QuizQuestion(
            prompt = Res.string.quiz_q_gap_insertion,
            options = listOf(Res.string.algo_shell, Res.string.algo_quick, Res.string.algo_heap),
            correctIndex = 0,
        )
    }
}

private fun timeLimitForDifficulty(difficulty: QuizDifficulty): Long {
    return when (difficulty) {
        QuizDifficulty.EASY -> 12_000
        QuizDifficulty.MEDIUM -> 9_000
        QuizDifficulty.HARD -> 6_000
    }
}
