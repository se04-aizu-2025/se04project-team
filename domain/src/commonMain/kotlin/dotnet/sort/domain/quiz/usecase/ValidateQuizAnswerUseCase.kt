package dotnet.sort.domain.quiz.usecase

import dotnet.sort.domain.quiz.model.QuizFeedback
import org.koin.core.annotation.Factory

/**
 * クイズの解答を検証するユースケース。
 */
@Factory
class ValidateQuizAnswerUseCase {
    /**
     * ユーザーの解答を検証します。
     *
     * @param validAnswer 正解のインデックスペア
     * @param userAnswer ユーザーが選択したインデックスペア
     * @return 検証結果（正解または不正解）
     */
    operator fun invoke(validAnswer: Pair<Int, Int>, userAnswer: Pair<Int, Int>): QuizFeedback {
        // 順序不同で比較
        val isValid = (validAnswer.first == userAnswer.first && validAnswer.second == userAnswer.second) ||
                (validAnswer.first == userAnswer.second && validAnswer.second == userAnswer.first)

        return if (isValid) {
            QuizFeedback.Correct
        } else {
            QuizFeedback.Incorrect(validAnswer)
        }
    }
}
