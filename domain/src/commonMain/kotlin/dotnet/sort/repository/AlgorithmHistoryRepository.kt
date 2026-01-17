package dotnet.sort.repository

import dotnet.sort.model.AlgorithmHistoryEntry
import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.SortType
import kotlinx.coroutines.flow.Flow

interface AlgorithmHistoryRepository {
    suspend fun recordEvent(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    )

    fun observeRecentEvents(limit: Int): Flow<List<AlgorithmHistoryEntry>>
}
