package dotnet.sort.usecase

import dotnet.sort.model.SortType
import org.koin.core.annotation.Single

/**
 * 推奨学習内容を取得するユースケース。
 */
@Single
class GetRecommendedLearningUseCase {
    operator fun invoke(proficiencyMap: Map<SortType, ProficiencyLevel>): List<RecommendedLearning> {
        val recommendations = mutableListOf<RecommendedLearning>()

        // 学習していないアルゴリズムを推奨
        SortType.entries.forEach { algorithm ->
            val proficiency = proficiencyMap[algorithm] ?: ProficiencyLevel.NONE
            if (proficiency == ProficiencyLevel.NONE) {
                recommendations.add(
                    RecommendedLearning(
                        algorithm = algorithm,
                        reason = "未学習",
                        priority = RecommendationPriority.HIGH,
                    ),
                )
            }
        }

        // 基礎アルゴリズムの習熟を推奨
        val basicAlgorithms = listOf(SortType.BUBBLE, SortType.SELECTION, SortType.INSERTION)
        basicAlgorithms.forEach { algorithm ->
            val proficiency = proficiencyMap[algorithm] ?: ProficiencyLevel.NONE
            if (proficiency < ProficiencyLevel.INTERMEDIATE) {
                recommendations.add(
                    RecommendedLearning(
                        algorithm = algorithm,
                        reason = "基礎アルゴリズムの習熟が必要",
                        priority = RecommendationPriority.MEDIUM,
                    ),
                )
            }
        }

        return recommendations
            .distinctBy { it.algorithm }
            .sortedBy { it.priority }
            .take(3)
    }
}

/**
 * 推奨学習内容。
 */
data class RecommendedLearning(
    val algorithm: SortType,
    val reason: String,
    val priority: RecommendationPriority,
)

/**
 * 推奨優先度。
 */
enum class RecommendationPriority {
    HIGH,
    MEDIUM,
    LOW,
}