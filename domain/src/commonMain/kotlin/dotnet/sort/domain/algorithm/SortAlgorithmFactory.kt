package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortType

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
            SortType.COUNTING -> CountingSortAlgorithm()
        }
    }
}
