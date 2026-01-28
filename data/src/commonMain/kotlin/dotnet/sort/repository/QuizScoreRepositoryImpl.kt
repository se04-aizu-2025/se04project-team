package dotnet.sort.repository

import dotnet.sort.database.DnsortDatabaseProvider
import dotnet.sort.model.QuizScore
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class QuizScoreRepositoryImpl(
    private val databaseProvider: DnsortDatabaseProvider,
) : QuizScoreRepository {
    override suspend fun recordScore(score: QuizScore) {
        databaseProvider.insertQuizScore(score)
    }

    override fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        databaseProvider.observeRecentScores(limit)
}
