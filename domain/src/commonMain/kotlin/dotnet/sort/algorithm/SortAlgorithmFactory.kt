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
            SortType.SELECTION -> SelectionSortAlgorithm()
            SortType.INSERTION -> InsertionSortAlgorithm()
            SortType.SHELL -> ShellSortAlgorithm()
            SortType.MERGE -> MergeSortAlgorithm()
            SortType.QUICK -> QuickSortAlgorithm()
            SortType.HEAP -> HeapSortAlgorithm()
        }
    }
}
