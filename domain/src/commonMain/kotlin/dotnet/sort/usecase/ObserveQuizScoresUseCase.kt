package dotnet.sort.usecase

import dotnet.sort.model.QuizScore
import dotnet.sort.repository.QuizScoreRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * クイズ成績を購読するユースケース。
 */
@Single
class ObserveQuizScoresUseCase(
    private val quizScoreRepository: QuizScoreRepository,
) {
    operator fun invoke(limit: Int = 50): Flow<List<QuizScore>> =
        quizScoreRepository.observeRecentScores(limit)
}
