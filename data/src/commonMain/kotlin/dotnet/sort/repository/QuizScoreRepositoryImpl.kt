package dotnet.sort.repository

import dotnet.sort.database.DnsortDatabaseProvider
import dotnet.sort.model.QuizScore
import dotnet.sort.model.ScorePeriod
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L
private const val MILLIS_PER_WEEK = 7 * MILLIS_PER_DAY

@OptIn(ExperimentalTime::class)
@Single
class QuizScoreRepositoryImpl(
    private val databaseProvider: DnsortDatabaseProvider,
) : QuizScoreRepository {
    override suspend fun recordScore(score: QuizScore) {
        databaseProvider.insertQuizScore(score)
    }

    override fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        databaseProvider.observeRecentScores(limit)

    override fun observeScoresByPeriod(
        period: ScorePeriod,
        limit: Int,
    ): Flow<List<QuizScore>> {
        val nowMillis = Clock.System.now().toEpochMilliseconds()
        val sinceMillis = when (period) {
            ScorePeriod.DAILY -> nowMillis - MILLIS_PER_DAY
            ScorePeriod.WEEKLY -> nowMillis - MILLIS_PER_WEEK
            ScorePeriod.ALL -> 0L
        }
        return databaseProvider.observeScoresSince(sinceMillis, limit)
    }
}
