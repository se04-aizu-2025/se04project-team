package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.domain.quiz.model.QuizQuestion
import dotnet.sort.domain.quiz.model.QuizFeedback

/**
 * Quiz画面のUI状態。
 *
 * @property isLoading ローディング状態
 * @property currentQuestion 現在出題中の問題
 * @property score 現在のスコア
 * @property timeLeftSeconds 残り時間（秒）
 * @property isGameActive ゲーム進行中かどうか
 * @property feedback 正誤判定のフィードバック
 */
data class QuizState(
    val isLoading: Boolean = false,
    val currentQuestion: QuizQuestion? = null,
    val score: Int = 0,
    val timeLeftSeconds: Int = 10,
    val isGameActive: Boolean = false,
    val feedback: QuizFeedback? = null,
    val consecutiveCorrectCount: Int = 0
) : UiState

/**
 * クイズの正誤フィードバック
 */

