package dotnet.sort.usecase

import dotnet.sort.model.AlgorithmHistoryEntry
import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.SortType
import dotnet.sort.repository.AlgorithmHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

/**
 * 学習統計を取得するユースケース。
 */
@Single
class GetLearningStatisticsUseCase(
    private val historyRepository: AlgorithmHistoryRepository,
) {
    operator fun invoke(): Flow<LearningStatistics> =
        historyRepository.observeRecentEvents(1000).map { entries ->
            val totalLearningTimeMs = calculateTotalLearningTime(entries)
            val algorithmProficiency = calculateAlgorithmProficiency(entries)
            val recentActivity = entries.take(10)
            val totalSessions = entries.count { it.eventType == HistoryEventType.SortExecuted }

            LearningStatistics(
                totalLearningTimeMs = totalLearningTimeMs,
                algorithmProficiency = algorithmProficiency,
                recentActivity = recentActivity,
                totalSessions = totalSessions,
            )
        }

    private fun calculateTotalLearningTime(entries: List<AlgorithmHistoryEntry>): Long {
        // 簡易的な実装：各SortExecutedイベントに5秒を想定
        return entries.count { it.eventType == HistoryEventType.SortExecuted } * 5000L
    }

    private fun calculateAlgorithmProficiency(entries: List<AlgorithmHistoryEntry>): Map<SortType, ProficiencyLevel> {
        val algorithmUsage = mutableMapOf<SortType, Int>()

        entries.filter { it.eventType == HistoryEventType.SortExecuted && it.algorithmType != null }
            .forEach { entry ->
                entry.algorithmType?.let { type ->
                    algorithmUsage[type] = (algorithmUsage[type] ?: 0) + 1
                }
            }

        return algorithmUsage.mapValues { (_, count) ->
            when {
                count >= 10 -> ProficiencyLevel.EXPERT
                count >= 5 -> ProficiencyLevel.INTERMEDIATE
                count >= 1 -> ProficiencyLevel.BEGINNER
                else -> ProficiencyLevel.NONE
            }
        }
    }
}

/**
 * 学習統計データ。
 */
data class LearningStatistics(
    val totalLearningTimeMs: Long,
    val algorithmProficiency: Map<SortType, ProficiencyLevel>,
    val recentActivity: List<AlgorithmHistoryEntry>,
    val totalSessions: Int,
)

/**
 * 習熟度レベル。
 */
enum class ProficiencyLevel {
    NONE,
    BEGINNER,
    INTERMEDIATE,
    EXPERT,
}