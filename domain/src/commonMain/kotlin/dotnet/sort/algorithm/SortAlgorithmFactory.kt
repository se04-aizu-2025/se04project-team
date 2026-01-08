package dotnet.sort.algorithm

import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortType

/**
 * ソートアルゴリズムのインスタンスを生成するファクトリ。
 */
object SortAlgorithmFactory {

    /**
     *指定された種類のソートアルゴリズムの新しいインスタンスを生成します。
     *
     * @param type ソートアルゴリズムの種類
     * @return [SortAlgorithm] の新しいインスタンス
     * @throws NotImplementedError 指定されたアルゴリズムが未実装の場合
     */
    fun create(type: SortType): SortAlgorithm {
        return when (type) {
            SortType.BUBBLE -> BubbleSortAlgorithm()
            SortType.SELECTION -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
            SortType.INSERTION -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
            SortType.SHELL -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
            SortType.MERGE -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
            SortType.QUICK -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
            SortType.HEAP -> throw NotImplementedError("Algorithm ${type.displayName} is not implemented yet")
        }
    }
}
