package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import org.koin.core.annotation.Factory

/**
 * Quiz画面のユーザーアクション。
 */
sealed interface QuizIntent : dotnet.sort.presentation.common.viewmodel.Intent

/**
 * Quiz画面のUI状態。
 *
 * @property isLoading ローディング状態
 */
data class QuizState(
    val isLoading: Boolean = false,
) : UiState

/**
 * Quiz画面のViewModel。
 *
 * クイズ機能の状態管理とビジネスロジックを担当します。
 */
@Factory
class QuizViewModel : BaseViewModel<QuizState, QuizIntent>(QuizState()) {
    override fun send(intent: QuizIntent) {
        // TODO: PR-65以降で実装
    }
}
