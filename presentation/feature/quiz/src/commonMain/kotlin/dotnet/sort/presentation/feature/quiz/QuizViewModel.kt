package dotnet.sort.presentation.feature.quiz

import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.QuizScore
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.usecase.ObserveQuizScoresUseCase
import dotnet.sort.usecase.RecordQuizScoreUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val algorithmType: SortType? = null,
    val hint: String? = null,
)

data class QuizScoreEntry(
    val difficulty: QuizDifficulty,
    val mode: QuizMode,
    val score: Int,
    val correctCount: Int,
    val totalQuestions: Int,
    val createdAtMillis: Long,
)

private val defaultQuestions = questionsFor(QuizMode.SPEED_SWAP, QuizDifficulty.EASY)

data class QuizState(
    val difficulty: QuizDifficulty = QuizDifficulty.EASY,
    val mode: QuizMode = QuizMode.SPEED_SWAP,
    val questionIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = defaultQuestions.size,
    val longestStreak: Int = 0,
    val isRunning: Boolean = false,
    val timeLimitMs: Long = 10_000,
    val timeRemainingMs: Long = 10_000,
    val questionSet: List<QuizQuestion> = defaultQuestions,
    val question: QuizQuestion = defaultQuestions.first(),
    val selectedIndex: Int? = null,
    val isCorrect: Boolean? = null,
    val showHint: Boolean = false,
    val showSummary: Boolean = false,
    val quizStartMillis: Long? = null,
    val leaderboard: List<QuizScoreEntry> = emptyList(),
    val scoreHistory: List<QuizScoreEntry> = emptyList(),
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

@OptIn(ExperimentalTime::class)
@Factory
class QuizViewModel(
    private val recordQuizScoreUseCase: RecordQuizScoreUseCase,
    private val observeQuizScoresUseCase: ObserveQuizScoresUseCase,
) : BaseViewModel<QuizState, QuizIntent>(QuizState()) {

    init {
        observeRecentScores()
    }

    override fun send(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.SelectMode -> {
                val questions = questionsFor(intent.mode, state.value.difficulty)
                updateState {
                    copy(
                        mode = intent.mode,
                        questionIndex = 0,
                        questionSet = questions,
                        question = questions.first(),
                        totalQuestions = questions.size,
                        showSummary = false,
                        showHint = false,
                    )
                }
            }
            is QuizIntent.SelectDifficulty -> {
                val questions = questionsFor(state.value.mode, intent.difficulty)
                updateState {
                    copy(
                        difficulty = intent.difficulty,
                        questionIndex = 0,
                        score = 0,
                        streak = 0,
                        correctCount = 0,
                        longestStreak = 0,
                        questionSet = questions,
                        question = questions.first(),
                        totalQuestions = questions.size,
                        selectedIndex = null,
                        isCorrect = null,
                        showHint = false,
                        showSummary = false,
                    )
                }
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
        val nowMillis = Clock.System.now().toEpochMilliseconds()
        updateState {
            copy(
                isRunning = true,
                showSummary = false,
                questionIndex = 0,
                questionSet = questions,
                question = questions.first(),
                timeRemainingMs = timeLimitForDifficulty(state.value.difficulty),
                timeLimitMs = timeLimitForDifficulty(state.value.difficulty),
                totalQuestions = questions.size,
                selectedIndex = null,
                isCorrect = null,
                showHint = false,
                quizStartMillis = nowMillis,
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
        val questions = state.value.questionSet
        val nextIndex = state.value.questionIndex + 1
        if (nextIndex >= state.value.totalQuestions) {
            recordQuizScore()
            updateState {
                copy(
                    showSummary = true,
                    isRunning = false,
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

    private fun observeRecentScores() {
        execute {
            observeQuizScoresUseCase()
                .collectLatest { scores ->
                    val history =
                        scores.map { score ->
                            QuizScoreEntry(
                                difficulty = score.difficulty,
                                mode = score.mode,
                                score = score.score,
                                correctCount = score.correctCount,
                                totalQuestions = score.correctCount + score.incorrectCount,
                                createdAtMillis = score.createdAtMillis,
                            )
                        }
                    updateState {
                        copy(
                            scoreHistory = history,
                            leaderboard = history.sortedByDescending { it.score }.take(5),
                        )
                    }
                }
        }
    }

    private fun recordQuizScore() {
        val startedAt = state.value.quizStartMillis ?: Clock.System.now().toEpochMilliseconds()
        val durationMillis = (Clock.System.now().toEpochMilliseconds() - startedAt).coerceAtLeast(0L)
        val algorithmType = state.value.questionSet.mapNotNull { it.algorithmType }.distinct().singleOrNull()
        val incorrectCount = (state.value.totalQuestions - state.value.correctCount).coerceAtLeast(0)
        val score = QuizScore(
            correctCount = state.value.correctCount,
            incorrectCount = incorrectCount,
            longestStreak = state.value.longestStreak,
            score = state.value.score,
            durationMillis = durationMillis,
            difficulty = state.value.difficulty,
            mode = state.value.mode,
            algorithmType = algorithmType,
            quizVersion = QUIZ_VERSION,
            createdAtMillis = Clock.System.now().toEpochMilliseconds(),
        )

        execute {
            recordQuizScoreUseCase(score)
        }
    }
}

private fun questionsFor(mode: QuizMode, difficulty: QuizDifficulty): List<QuizQuestion> {
    val base = when (mode) {
        QuizMode.SPEED_SWAP -> listOf(
            QuizQuestion(
                prompt = "Which pair will swap next in Bubble Sort?",
                options = listOf("(0,1)", "(1,2)", "(2,3)"),
                correctIndex = 0,
                algorithmType = SortType.BUBBLE,
            ),
            QuizQuestion(
                prompt = "Which element moves to the end in Selection Sort?",
                options = listOf("Minimum", "Maximum", "Middle"),
                correctIndex = 1,
                algorithmType = SortType.SELECTION,
            ),
        )
        QuizMode.GUESS_ALGORITHM -> listOf(
            QuizQuestion(
                prompt = "Uses pivot-based partitioning.",
                options = listOf("Quick Sort", "Merge Sort", "Heap Sort"),
                correctIndex = 0,
                algorithmType = SortType.QUICK,
                hint = "Average complexity \$O(n \\log n)",
            ),
            QuizQuestion(
                prompt = "Repeatedly extracts max/min from a heap.",
                options = listOf("Heap Sort", "Shell Sort", "Insertion Sort"),
                correctIndex = 0,
                algorithmType = SortType.HEAP,
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
            algorithmType = SortType.MERGE,
        )
        QuizDifficulty.HARD -> base + QuizQuestion(
            prompt = "Gapped insertion passes.",
            options = listOf("Shell Sort", "Quick Sort", "Heap Sort"),
            correctIndex = 0,
            algorithmType = SortType.SHELL,
        )
    }
}

private const val QUIZ_VERSION = "v2"

private fun timeLimitForDifficulty(difficulty: QuizDifficulty): Long {
    return when (difficulty) {
        QuizDifficulty.EASY -> 12_000
        QuizDifficulty.MEDIUM -> 9_000
        QuizDifficulty.HARD -> 6_000
    }
}
