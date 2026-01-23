package dotnet.sort.domain.usecase

import dotnet.sort.domain.model.HistoryEventType
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.repository.AlgorithmHistoryRepository
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
