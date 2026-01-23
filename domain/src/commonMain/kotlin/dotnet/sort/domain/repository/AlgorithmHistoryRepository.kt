package dotnet.sort.domain.repository

import dotnet.sort.domain.model.AlgorithmHistoryEntry
import dotnet.sort.domain.model.HistoryEventType
import dotnet.sort.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface AlgorithmHistoryRepository {
    suspend fun recordEvent(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    )

    fun observeRecentEvents(limit: Int): Flow<List<AlgorithmHistoryEntry>>
}
