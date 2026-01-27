package dotnet.sort.database

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dotnet.sort.data.DnsortDatabase
import dotnet.sort.model.AlgorithmHistoryEntry
import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.SortType
import dotnet.sort.repository.historyEventTypeFromDb
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
}
