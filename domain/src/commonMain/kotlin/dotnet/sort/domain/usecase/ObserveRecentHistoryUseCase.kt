package dotnet.sort.domain.usecase

import dotnet.sort.domain.model.AlgorithmHistoryEntry
import dotnet.sort.domain.repository.AlgorithmHistoryRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

/**
 * 最新の履歴を監視するユースケース。
 */
@Single
class ObserveRecentHistoryUseCase(
    private val historyRepository: AlgorithmHistoryRepository,
) {
    operator fun invoke(limit: Int): Flow<List<AlgorithmHistoryEntry>> = historyRepository.observeRecentEvents(limit)
}
