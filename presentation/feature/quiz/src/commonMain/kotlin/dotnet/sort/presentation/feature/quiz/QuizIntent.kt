package dotnet.sort.presentation.feature.quiz

import dotnet.sort.presentation.common.viewmodel.Intent

/**
 * Quiz画面のユーザーアクション。
 */
sealed interface QuizIntent : Intent {
    /**
     * ゲームを開始する
     */
    data object StartGame : QuizIntent
    
    /**
     * 回答を送信する
     * @property selectedIndices ユーザーが選択した2つのインデックス
     */
    data class SubmitAnswer(val selectedIndices: Pair<Int, Int>) : QuizIntent
    
    /**
     * 次の問題へ進む
     */
    data object NextQuestion : QuizIntent
    
    /**
     * タイマーを1秒進める（内部用）
     */
    data object Tick : QuizIntent
    
    /**
     * ゲームを終了する
     */
    data object EndGame : QuizIntent

    /**
     * 難易度を選択する
     */
    data class SelectDifficulty(val difficulty: QuizDifficulty) : QuizIntent

    /**
     * ランキングをリセットする
     */
    data object ResetRanking : QuizIntent
}
