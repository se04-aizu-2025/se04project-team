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
    val showSummary: Boolean = false
) : UiState

/**
 * クイズの正誤フィードバック
 */

