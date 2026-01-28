package dotnet.sort.presentation.feature.quiz

import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.QuizScoreEntry
import dotnet.sort.model.ScorePeriod
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.presentation.feature.quiz.model.QuizUiQuestion

data class QuizState(
    val difficulty: QuizDifficulty = QuizDifficulty.EASY,
    val mode: QuizMode = QuizMode.SPEED_SWAP,
    val questionIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = 2, // defaultQuestions.size is usually 2
    val longestStreak: Int = 0,
    val isRunning: Boolean = false,
    val timeLimitMs: Long = 10_000,
    val timeRemainingMs: Long = 10_000,
    val questionSet: List<QuizUiQuestion> = emptyList(), // Avoid dependency on ViewModel private val
    val question: QuizUiQuestion? = null, // Avoid dependency on ViewModel private val. Nullable to handle initial
    val selectedIndex: Int? = null,
    val isCorrect: Boolean? = null,
    val showHint: Boolean = false,
    val showSummary: Boolean = false,
    val quizStartMillis: Long? = null,
    val leaderboard: List<QuizScoreEntry> = emptyList(),
    val scoreHistory: List<QuizScoreEntry> = emptyList(),
    val scorePeriod: ScorePeriod = ScorePeriod.ALL,
) : UiState

