package dotnet.sort.domain.quiz.usecase

import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.quiz.generator.QuizGenerator
import dotnet.sort.domain.quiz.model.QuizQuestion
import org.koin.core.annotation.Factory

/**
 * クイズ問題を生成するユースケース。
 */
@Factory
class GenerateQuizQuestionUseCase(
    private val quizGenerator: QuizGenerator
) {
    /**
     * 指定されたアルゴリズムのクイズ問題を生成します。
     *
     * @param type アルゴリズムの種類
     * @return 生成された問題
     */
    operator fun invoke(type: SortType): QuizQuestion {
        // 難易度調整などをここで行うことも可能
        return quizGenerator.generate(type)
    }
}
