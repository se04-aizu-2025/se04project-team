package dotnet.sort.presentation.feature.sort

import dotnet.sort.model.SortType
import dotnet.sort.generator.ArrayGeneratorType

/**
 * ソート画面のIntent定義。
 *
 * MVIパターンにおけるユーザーアクションを表す密封クラス。
 */
sealed class SortIntent {

    /**
     * アルゴリズムを選択する。
     * @param type 選択するソートアルゴリズムの種類
     */
    data class SelectAlgorithm(val type: SortType) : SortIntent()

    /**
     * 配列サイズを設定する。
     * @param size 配列のサイズ
     */
    data class SetArraySize(val size: Int) : SortIntent()

    /**
     * 配列を生成する。
     * @param generatorType 配列生成タイプ
     */
    data class GenerateArray(val generatorType: ArrayGeneratorType) : SortIntent()

    /**
     * ソートを開始する。
     */
    data object StartSort : SortIntent()

    /**
     * ソートを一時停止する。
     */
    data object PauseSort : SortIntent()

    /**
     * ソートを再開する。
     */
    data object ResumeSort : SortIntent()

    /**
     * ソートをリセットする。
     */
    data object ResetSort : SortIntent()

    /**
     * 1ステップ進める。
     */
    data object StepForward : SortIntent()

    /**
     * 1ステップ戻る。
     */
    data object StepBackward : SortIntent()

    /**
     * 再生速度を設定する。
     * @param speedMultiplier 速度倍率（0.25x ~ 4.0x）
     */
    data class SetSpeed(val speedMultiplier: Float) : SortIntent()
}
