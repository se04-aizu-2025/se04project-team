package dotnet.sort.presentation.feature.quiz

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.algo_bubble
import dotnet.sort.designsystem.generated.resources.algo_heap
import dotnet.sort.designsystem.generated.resources.algo_insertion
import dotnet.sort.designsystem.generated.resources.algo_merge
import dotnet.sort.designsystem.generated.resources.algo_quick
import dotnet.sort.designsystem.generated.resources.algo_selection
import dotnet.sort.designsystem.generated.resources.algo_shell
import dotnet.sort.designsystem.generated.resources.quiz_hint_heap
import dotnet.sort.designsystem.generated.resources.quiz_hint_quick
import dotnet.sort.designsystem.generated.resources.quiz_hint_shell
import dotnet.sort.designsystem.generated.resources.quiz_opt_max
import dotnet.sort.designsystem.generated.resources.quiz_opt_middle
import dotnet.sort.designsystem.generated.resources.quiz_opt_min
import dotnet.sort.designsystem.generated.resources.quiz_opt_pair_0_1
import dotnet.sort.designsystem.generated.resources.quiz_opt_pair_1_2
import dotnet.sort.designsystem.generated.resources.quiz_opt_pair_2_3
import dotnet.sort.designsystem.generated.resources.quiz_q_bubble_first_compare
import dotnet.sort.designsystem.generated.resources.quiz_q_heap_extract
import dotnet.sort.designsystem.generated.resources.quiz_q_merge_divide
import dotnet.sort.designsystem.generated.resources.quiz_q_quick_pivot
import dotnet.sort.designsystem.generated.resources.quiz_q_selection_first_place
import dotnet.sort.designsystem.generated.resources.quiz_q_shell_gap
import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.QuizScore
import dotnet.sort.model.QuizScoreEntry
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.feature.quiz.model.QuizUiQuestion
import dotnet.sort.usecase.ObserveQuizScoresByPeriodUseCase
import dotnet.sort.usecase.RecordQuizScoreUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Factory
class QuizViewModel(
    private val recordQuizScoreUseCase: RecordQuizScoreUseCase,
    private val observeQuizScoresByPeriodUseCase: ObserveQuizScoresByPeriodUseCase,
) : BaseViewModel<QuizState, QuizIntent>(createInitialState()) {

    private var scoreObservationJob: Job? = null

    init {
        observeScores()
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
                        question = questions.firstOrNull(),
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
                        question = questions.firstOrNull(),
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
            QuizIntent.SubmitAnswer -> submitAnswer()
            QuizIntent.NextQuestion -> moveToNextQuestion()
            QuizIntent.ToggleHint -> updateState { copy(showHint = !showHint) }
            is QuizIntent.SelectScorePeriod -> {
                updateState { copy(scorePeriod = intent.period) }
                observeScores()
            }
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
                question = questions.firstOrNull(),
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
        val currentQuestion = state.value.question ?: return
        val isCorrect = selected == currentQuestion.correctIndex
        val newStreak = if (isCorrect) state.value.streak + 1 else 0
        val newScore = if (isCorrect) {
            val timeBonus = (state.value.timeRemainingMs / SCORE_TIME_BONUS_DIVISOR).toInt()
            state.value.score + SCORE_BASE_POINTS + newStreak + timeBonus
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
        if (questions.isEmpty()) return
        
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
        // Set isRunning=true first, then clear isCorrect in same update
        // This ensures smooth transition from RESULT to PLAYING
        updateState {
            copy(
                isRunning = true,
                isCorrect = null,
                questionIndex = nextIndex,
                question = question,
                selectedIndex = null,
                timeRemainingMs = timeLimitForDifficulty(difficulty),
                timeLimitMs = timeLimitForDifficulty(difficulty),
                showHint = false,
            )
        }
        startTimer()
    }

    private fun observeScores() {
        if (scoreObservationJob?.isActive == true) {
            scoreObservationJob?.cancel()
        }
        scoreObservationJob = execute {
            observeQuizScoresByPeriodUseCase(state.value.scorePeriod)
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

    companion object {
        private const val QUIZ_VERSION = "v2"
        private const val SCORE_BASE_POINTS = 10
        private const val SCORE_TIME_BONUS_DIVISOR = 200

        private const val TIME_LIMIT_EASY = 12_000L
        private const val TIME_LIMIT_MEDIUM = 9_000L
        private const val TIME_LIMIT_HARD = 6_000L

        private fun createInitialState(): QuizState {
            val defaultQuestions = questionsFor(QuizMode.SPEED_SWAP, QuizDifficulty.EASY)
            return QuizState(
                questionSet = defaultQuestions,
                question = defaultQuestions.firstOrNull(),
                totalQuestions = defaultQuestions.size
            )
        }

        private fun questionsFor(mode: QuizMode, difficulty: QuizDifficulty): List<QuizUiQuestion> {
            val base = when (mode) {
                QuizMode.SPEED_SWAP -> listOf(
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_bubble_first_compare,
                        options = listOf(
                            Res.string.quiz_opt_pair_0_1,
                            Res.string.quiz_opt_pair_1_2,
                            Res.string.quiz_opt_pair_2_3
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.BUBBLE,
                    ),
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_selection_first_place,
                        options = listOf(
                            Res.string.quiz_opt_min,
                            Res.string.quiz_opt_max,
                            Res.string.quiz_opt_middle
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.SELECTION,
                    ),
                )
                QuizMode.GUESS_ALGORITHM -> listOf(
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_quick_pivot,
                        options = listOf(
                            Res.string.algo_quick,
                            Res.string.algo_merge,
                            Res.string.algo_heap
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.QUICK,
                        hint = Res.string.quiz_hint_quick,
                    ),
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_heap_extract,
                        options = listOf(
                            Res.string.algo_heap,
                            Res.string.algo_shell,
                            Res.string.algo_insertion
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.HEAP,
                        hint = Res.string.quiz_hint_heap,
                    ),
                )
            }

            return when (difficulty) {
                QuizDifficulty.EASY -> base
                QuizDifficulty.MEDIUM -> base + QuizUiQuestion(
                    prompt = Res.string.quiz_q_merge_divide,
                    options = listOf(
                        Res.string.algo_merge,
                        Res.string.algo_bubble,
                        Res.string.algo_selection
                    ),
                    correctIndex = 0,
                    algorithmType = SortType.MERGE,
                )
                QuizDifficulty.HARD -> base + listOf(
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_merge_divide,
                        options = listOf(
                            Res.string.algo_merge,
                            Res.string.algo_bubble,
                            Res.string.algo_selection
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.MERGE,
                    ),
                    QuizUiQuestion(
                        prompt = Res.string.quiz_q_shell_gap,
                        options = listOf(
                            Res.string.algo_shell,
                            Res.string.algo_quick,
                            Res.string.algo_heap
                        ),
                        correctIndex = 0,
                        algorithmType = SortType.SHELL,
                        hint = Res.string.quiz_hint_shell
                    ),
                )
            }
        }

        private fun timeLimitForDifficulty(difficulty: QuizDifficulty): Long {
            return when (difficulty) {
                QuizDifficulty.EASY -> TIME_LIMIT_EASY
                QuizDifficulty.MEDIUM -> TIME_LIMIT_MEDIUM
                QuizDifficulty.HARD -> TIME_LIMIT_HARD
            }
        }
    }
}
