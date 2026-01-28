package dotnet.sort.repository

import dotnet.sort.model.QuizScore
import dotnet.sort.model.ScorePeriod
import kotlinx.coroutines.flow.Flow

interface QuizScoreRepository {
    suspend fun recordScore(score: QuizScore)

    fun observeRecentScores(limit: Int): Flow<List<QuizScore>>

    /**
     * 指定した期間内のスコアを監視する。
     * @param period 期間 (DAILY, WEEKLY, ALL)
     * @param limit 取得件数上限
     */
    fun observeScoresByPeriod(
        period: ScorePeriod,
        limit: Int = 100,
    ): Flow<List<QuizScore>>
}
