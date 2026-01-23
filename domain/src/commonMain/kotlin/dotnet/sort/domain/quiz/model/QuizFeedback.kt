package dotnet.sort.domain.quiz.model

/**
 * クイズの解答に対するフィードバック。
 */
sealed interface QuizFeedback {
    /**
     * 正解。
     */
    data object Correct : QuizFeedback

    /**
     * 不正解。
     *
     * @property correctIndices 正解のインデックスペア
     */
    data class Incorrect(val correctIndices: Pair<Int, Int>) : QuizFeedback
}
