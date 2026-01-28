package dotnet.sort.usecase

import dotnet.sort.model.QuizScore
import dotnet.sort.repository.QuizScoreRepository
import org.koin.core.annotation.Single

/**
 * クイズ成績を記録するユースケース。
 */
@Single
class RecordQuizScoreUseCase(
    private val quizScoreRepository: QuizScoreRepository,
) {
    suspend operator fun invoke(score: QuizScore) {
        quizScoreRepository.recordScore(score)
    }
}
