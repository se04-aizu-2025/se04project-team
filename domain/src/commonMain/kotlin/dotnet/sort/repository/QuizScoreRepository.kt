package dotnet.sort.repository

import dotnet.sort.model.QuizScore
import kotlinx.coroutines.flow.Flow

interface QuizScoreRepository {
    suspend fun recordScore(score: QuizScore)

    fun observeRecentScores(limit: Int): Flow<List<QuizScore>>
}
