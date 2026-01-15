package dotnet.sort.algorithm

import dotnet.sort.model.SortType
import org.koin.core.annotation.Single

/**
 * バブルソート（単方向バブルソート）の実装クラス。
 *
 * 隣り合う要素の比較と交換を繰り返し、最大（または最小）の要素を数列の端に移動させていく単純なソートアルゴリズムです。
 * - 時間計算量: O(n^2)
 * - 空間計算量: O(1)
 */

@Single
class BubbleSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.BUBBLE
    override val timeComplexity = "O(n²)"
    override val spaceComplexity = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        val n = array.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                // Snapshot for comparison
                addSnapshot(
                    array,
                    listOf(j, j + 1),
                    "Comparing indices $j and ${j + 1}"
                )

                if (compare(array[j], array[j + 1]) > 0) {
                    swap(array, j, j + 1)

                    addSnapshot(
                        array,
                        listOf(j, j + 1),
                        "Swap ${array[j + 1]} and ${array[j]}"
                    )
                }
            }
        }
    }
}