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
    /**
     * 履歴イベントを記録する。
     *
     * @param algorithmType アルゴリズムの種類（null可）
     * @param eventType イベントの種類
     * @param metadata 追加のメタデータ（null可）
     */
    suspend operator fun invoke(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    ) {
        historyRepository.recordEvent(algorithmType, eventType, metadata)
    }
}
