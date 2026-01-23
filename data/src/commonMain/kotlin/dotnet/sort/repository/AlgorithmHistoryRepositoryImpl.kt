package dotnet.sort.repository

import dotnet.sort.database.DnsortDatabaseProvider
import dotnet.sort.domain.model.AlgorithmHistoryEntry
import dotnet.sort.domain.model.HistoryEventType
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.repository.AlgorithmHistoryRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Single
class AlgorithmHistoryRepositoryImpl(
    private val databaseProvider: DnsortDatabaseProvider,
) : AlgorithmHistoryRepository {
    override suspend fun recordEvent(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String?,
    ) {
        databaseProvider.insertHistory(
            algorithmType = algorithmType,
            eventType = eventType,
            metadata = metadata,
            createdAtMillis = Clock.System.now().toEpochMilliseconds(),
        )
    }

    override fun observeRecentEvents(limit: Int): Flow<List<AlgorithmHistoryEntry>> = databaseProvider.observeRecent(limit)
}
