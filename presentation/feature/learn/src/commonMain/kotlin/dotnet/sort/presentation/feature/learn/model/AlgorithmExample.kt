package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.domain.model.SortType

/**
 * アルゴリズムの具体的動作例を保持するデータクラス
 *
 * @param sortType 対象のソートアルゴリズム
 * @param initialArray 初期配列
 * @param steps 各ステップの状態リスト
 */
data class AlgorithmExample(
    val sortType: SortType,
    val initialArray: List<Int>,
    val steps: List<AlgorithmExampleStep>
)

/**
 * 動作例の1ステップ
 *
 * @param stepIndex ステップ番号 (1-based)
 * @param arrayState その時点の配列の状態
 * @param description そのステップで行われた操作の説明
 * @param highlightIndices 注目しているインデックス（比較・交換対象）
 * @param modificationType 操作の種類（比較、交換など）
 */
data class AlgorithmExampleStep(
    val stepIndex: Int,
    val arrayState: List<Int>,
    val description: String,
    val highlightIndices: List<Int> = emptyList(),
    val modificationType: StepModificationType = StepModificationType.NONE
)

enum class StepModificationType {
    NONE,
    COMPARE, // 比較のみ
    SWAP,    // 交換
    SET      // 値の確定 (ソート済み部分への移動など)
}
