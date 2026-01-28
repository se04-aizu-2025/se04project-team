package dotnet.sort.usecase

import dotnet.sort.model.AlgorithmHistoryEntry
import dotnet.sort.repository.AlgorithmHistoryRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * 最新の履歴を監視するユースケース。
 */
@Single
class ObserveRecentHistoryUseCase(
    private val historyRepository: AlgorithmHistoryRepository,
) {
    /**
     * 最新の履歴イベントを購読する。
     *
     * @param limit 取得する最大件数
     * @return 履歴イベントのFlow
     */
    operator fun invoke(limit: Int): Flow<List<AlgorithmHistoryEntry>> = historyRepository.observeRecentEvents(limit)
}
