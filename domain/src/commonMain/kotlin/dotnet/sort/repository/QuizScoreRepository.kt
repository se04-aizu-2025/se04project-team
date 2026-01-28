package dotnet.sort.repository

import dotnet.sort.model.QuizScore
import dotnet.sort.model.ScorePeriod
import kotlinx.coroutines.flow.Flow

interface QuizScoreRepository {
    suspend fun recordScore(score: QuizScore)

    fun observeRecentScores(limit: Int): Flow<List<QuizScore>>

    fun observeScoresByPeriod(period: ScorePeriod, limit: Int): Flow<List<QuizScore>>
}
