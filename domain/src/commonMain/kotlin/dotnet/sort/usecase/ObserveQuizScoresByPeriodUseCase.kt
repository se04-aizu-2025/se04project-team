package dotnet.sort.usecase

import dotnet.sort.model.QuizScore
import dotnet.sort.model.ScorePeriod
import dotnet.sort.repository.QuizScoreRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * 期間別のクイズスコアを監視するユースケース。
 */
@Single
class ObserveQuizScoresByPeriodUseCase(
    private val quizScoreRepository: QuizScoreRepository,
) {
    /**
     * 指定した期間のスコアを監視する。
     * @param period 期間 (DAILY, WEEKLY, ALL)
     * @param limit 取得件数上限
     */
    operator fun invoke(
        period: ScorePeriod,
        limit: Int = 100,
    ): Flow<List<QuizScore>> = quizScoreRepository.observeScoresByPeriod(period, limit)
}
