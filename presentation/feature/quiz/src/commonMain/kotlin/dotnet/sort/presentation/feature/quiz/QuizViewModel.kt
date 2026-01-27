package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory

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
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String? = null,
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
                prompt = "Which pair will swap next in Bubble Sort?",
                options = listOf("(0,1)", "(1,2)", "(2,3)"),
                correctIndex = 0,
            ),
            QuizQuestion(
                prompt = "Which element moves to the end in Selection Sort?",
                options = listOf("Minimum", "Maximum", "Middle"),
                correctIndex = 1,
            ),
        )
        QuizMode.GUESS_ALGORITHM -> listOf(
            QuizQuestion(
                prompt = "Uses pivot-based partitioning.",
                options = listOf("Quick Sort", "Merge Sort", "Heap Sort"),
                correctIndex = 0,
                hint = "Average complexity \$O(n \\log n)",
            ),
            QuizQuestion(
                prompt = "Repeatedly extracts max/min from a heap.",
                options = listOf("Heap Sort", "Shell Sort", "Insertion Sort"),
                correctIndex = 0,
                hint = "Uses a binary heap structure",
            ),
        )
    }

    return when (difficulty) {
        QuizDifficulty.EASY -> base
        QuizDifficulty.MEDIUM -> base + QuizQuestion(
            prompt = "Divide and merge halves.",
            options = listOf("Merge Sort", "Bubble Sort", "Selection Sort"),
            correctIndex = 0,
        )
        QuizDifficulty.HARD -> base + QuizQuestion(
            prompt = "Gapped insertion passes.",
            options = listOf("Shell Sort", "Quick Sort", "Heap Sort"),
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
