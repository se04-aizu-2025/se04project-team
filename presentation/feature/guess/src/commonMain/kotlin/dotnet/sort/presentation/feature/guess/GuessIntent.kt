package dotnet.sort.presentation.feature.guess

import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.domain.model.SortType

/**
 * Guessゲーム画面のユーザーアクション。
 */
sealed interface GuessIntent : Intent {
    /**
     * ゲームを開始する
     */
    data object StartGame : GuessIntent

    /**
     * アニメーションを開始する
     */
    data object StartAnimation : GuessIntent

    /**
     * アルゴリズムを選択する
     */
    data class SelectAlgorithm(val algorithm: SortType) : GuessIntent

    /**
     * 回答を確定する
     */
    data object ConfirmAnswer : GuessIntent

    /**
     * ヒントを表示する
     */
    data object ShowHint : GuessIntent

    /**
     * 次の問題へ進む
     */
    data object NextQuestion : GuessIntent

    /**
     * ゲームを終了する
     */
    data object EndGame : GuessIntent
}