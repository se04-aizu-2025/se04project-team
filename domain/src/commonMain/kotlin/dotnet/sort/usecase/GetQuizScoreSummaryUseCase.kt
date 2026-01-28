package dotnet.sort.usecase

import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.repository.QuizScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

/**
 * クイズ成績のサマリーを取得するユースケース。
 */
@Single
class GetQuizScoreSummaryUseCase(
    private val quizScoreRepository: QuizScoreRepository,
) {
    /**
     * クイズ成績のサマリーを購読する。
     *
     * @param limit 集計対象の最大件数
     * @return サマリーのFlow（成績がない場合はnull）
     */
    operator fun invoke(limit: Int = 50): Flow<QuizScoreSummary?> =
        quizScoreRepository.observeRecentScores(limit).map { scores ->
            if (scores.isEmpty()) {
                return@map null
            }
            QuizScoreSummary(
                totalAttempts = scores.size,
                bestScore = scores.maxOf { it.score },
                averageScore = scores.map { it.score }.average().toInt(),
                latestScore = scores.first().score,
                latestDifficulty = scores.first().difficulty,
                latestMode = scores.first().mode,
                lastPlayedAtMillis = scores.first().createdAtMillis,
                longestStreak = scores.maxOf { it.longestStreak },
            )
        }
}

/**
 * クイズ成績サマリー。
 */
data class QuizScoreSummary(
    val totalAttempts: Int,
    val bestScore: Int,
    val averageScore: Int,
    val latestScore: Int,
    val latestDifficulty: QuizDifficulty,
    val latestMode: QuizMode,
    val lastPlayedAtMillis: Long,
    val longestStreak: Int,
)
