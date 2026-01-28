package dotnet.sort.database

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dotnet.sort.data.DnsortDatabase
import dotnet.sort.model.AlgorithmHistoryEntry
import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.QuizScore
import dotnet.sort.model.SortType
import dotnet.sort.repository.historyEventTypeFromDb
import dotnet.sort.repository.quizDifficultyFromDb
import dotnet.sort.repository.quizModeFromDb
import dotnet.sort.repository.sortTypeFromDb
import dotnet.sort.repository.toDbValue
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class DnsortDatabaseProvider(
    driverFactory: DatabaseDriverFactory,
) {
    private val driver = driverFactory.createDriver()
    private val database = DnsortDatabase(driver)
    private val queries = database.algorithm_historyQueries
    private val quizQueries = database.quiz_scoreQueries
    private val databaseReady = CompletableDeferred<Unit>()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            DnsortDatabase.Schema.awaitCreate(driver)
            databaseReady.complete(Unit)
        }
    }

    private suspend fun ensureDatabaseReady() {
        databaseReady.await()
    }

    suspend fun insertHistory(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String?,
        createdAtMillis: Long,
    ) {
        ensureDatabaseReady()
        queries.insertEvent(
            algorithm_type = algorithmType?.toDbValue(),
            event_type = eventType.toDbValue(),
            metadata = metadata,
            created_at_millis = createdAtMillis,
        )
    }

    suspend fun insertQuizScore(score: QuizScore) {
        ensureDatabaseReady()
        quizQueries.insertQuizScore(
            correct_count = score.correctCount.toLong(),
            incorrect_count = score.incorrectCount.toLong(),
            longest_streak = score.longestStreak.toLong(),
            score = score.score.toLong(),
            duration_millis = score.durationMillis,
            difficulty = score.difficulty.toDbValue(),
            mode = score.mode.toDbValue(),
            algorithm_type = score.algorithmType?.toDbValue(),
            quiz_version = score.quizVersion,
            created_at_millis = score.createdAtMillis,
        )
    }

    fun observeRecent(limit: Int): Flow<List<AlgorithmHistoryEntry>> =
        flow {
            ensureDatabaseReady()
            emitAll(
                queries
                    .selectRecent(limit.toLong())
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { rows ->
                        rows.map { row ->
                            AlgorithmHistoryEntry(
                                id = row.id,
                                algorithmType = row.algorithm_type?.let { sortTypeFromDb(it) },
                                eventType = historyEventTypeFromDb(row.event_type),
                                createdAtMillis = row.created_at_millis,
                                metadata = row.metadata,
                            )
                        }
                    },
            )
        }

    fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        flow {
            ensureDatabaseReady()
            emitAll(
                quizQueries
                    .selectQuizScores(limit.toLong())
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { rows ->
                        rows.map { row ->
                            QuizScore(
                                id = row.id,
                                correctCount = row.correct_count.toInt(),
                                incorrectCount = row.incorrect_count.toInt(),
                                longestStreak = row.longest_streak.toInt(),
                                score = row.score.toInt(),
                                durationMillis = row.duration_millis,
                                difficulty = quizDifficultyFromDb(row.difficulty),
                                mode = quizModeFromDb(row.mode),
                                algorithmType = row.algorithm_type?.let { sortTypeFromDb(it) },
                                quizVersion = row.quiz_version,
                                createdAtMillis = row.created_at_millis,
                            )
                        }
                    },
            )
        }
}
