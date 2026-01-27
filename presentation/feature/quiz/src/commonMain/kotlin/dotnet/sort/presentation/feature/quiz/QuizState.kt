package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.domain.quiz.model.QuizQuestion
import dotnet.sort.domain.quiz.model.QuizFeedback
import dotnet.sort.domain.model.SortType

/**
 * Quiz画面のUI状態。
 *
 * @property isLoading ローディング状態
 * @property currentQuestion 現在出題中の問題
 * @property score 現在のスコア
 * @property timeLeftSeconds 残り時間（秒）
 * @property isGameActive ゲーム進行中かどうか
 * @property feedback 正誤判定のフィードバック
 * @property consecutiveCorrectCount 連続正解数
 * @property longestCorrectStreak 最長連続正解数
 * @property totalAnsweredQuestions 回答済み問題数
 * @property correctAnswers 正解数
 * @property incorrectCounts アルゴリズム別不正解回数
 * @property showSummary サマリー表示中かどうか
 * @property difficulty クイズの難易度
 * @property rankings 難易度別ランキング
 */
data class QuizState(
    val isLoading: Boolean = false,
    val currentQuestion: QuizQuestion? = null,
    val score: Int = 0,
    val timeLeftSeconds: Int = 10,
    val isGameActive: Boolean = false,
    val feedback: QuizFeedback? = null,
    val consecutiveCorrectCount: Int = 0,
    val longestCorrectStreak: Int = 0,
    val totalAnsweredQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectCounts: Map<SortType, Int> = emptyMap(),
    val showSummary: Boolean = false,
    val difficulty: QuizDifficulty = QuizDifficulty.MEDIUM,
    val rankings: Map<QuizDifficulty, List<QuizRankingEntry>> = emptyMap()
) : UiState

/**
 * クイズの難易度。
 *
 * @property displayName 表示名
 * @property arraySize 配列サイズ
 * @property timeLimitSeconds 制限時間（秒）
 */
enum class QuizDifficulty(
    val displayName: String,
    val arraySize: Int,
    val timeLimitSeconds: Int
) {
    EASY("Easy", arraySize = 8, timeLimitSeconds = 15),
    MEDIUM("Medium", arraySize = 10, timeLimitSeconds = 10),
    HARD("Hard", arraySize = 14, timeLimitSeconds = 7)
}

/**
 * ランキングエントリ。
 *
 * @property score 総合スコア
 * @property accuracy 正解率（%）
 * @property longestStreak 最長連続正解
 */
data class QuizRankingEntry(
    val score: Int,
    val accuracy: Int,
    val longestStreak: Int
)

/**
 * クイズの正誤フィードバック
 */

