package dotnet.sort.domain.quiz.model

/**
 * クイズの解答に対するフィードバック。
 */
sealed interface QuizFeedback {
    /**
     * 正解。
     */
    /**
     * 正解。
     * 
     * @property scoreDelta 獲得したスコア
     */
    data class Correct(val scoreDelta: Int) : QuizFeedback

    /**
     * 不正解。
     *
     * @property correctIndices 正解のインデックスペア
     */
    data class Incorrect(val correctIndices: Pair<Int, Int>) : QuizFeedback
}
