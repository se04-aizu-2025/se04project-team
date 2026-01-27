package dotnet.sort.domain.model

import dotnet.sort.model.SortType

/**
 * アルゴリズムの歴史情報
 */
data class AlgorithmHistory(
    val sortType: SortType,
    val originYear: String,
    val inventor: String,
    val description: String
)

/**
 * アルゴリズムの実行例
 */
data class AlgorithmExample(
    val sortType: SortType,
    val input: List<Int>,
    val steps: List<AlgorithmExampleStep>
)

/**
 * アルゴリズム実行例の各ステップ
 */
data class AlgorithmExampleStep(
    val stepNumber: Int,
    val description: String,
    val arrayState: List<Int>,
    val modifications: List<StepModification>
)

/**
 * ステップ内の変更内容
 */
data class StepModification(
    val type: StepModificationType,
    val indices: List<Int>,
    val description: String
)

/**
 * ステップ変更の種類
 */
enum class StepModificationType {
    COMPARE,
    SWAP,
    SELECT,
    INSERT,
    PARTITION,
    MERGE
}