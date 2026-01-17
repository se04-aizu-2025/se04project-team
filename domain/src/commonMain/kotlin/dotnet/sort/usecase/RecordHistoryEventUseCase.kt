package dotnet.sort.usecase

import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.SortType
import dotnet.sort.repository.AlgorithmHistoryRepository
import org.koin.core.annotation.Single

/**
 * 履歴イベントを記録するユースケース。
 */
@Single
class RecordHistoryEventUseCase(
    private val historyRepository: AlgorithmHistoryRepository,
) {
    suspend operator fun invoke(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    ) {
        historyRepository.recordEvent(algorithmType, eventType, metadata)
    }
}
