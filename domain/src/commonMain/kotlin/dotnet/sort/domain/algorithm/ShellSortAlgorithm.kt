package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * シェルソートの実装クラス。
 *
 * 挿入ソートの改良版で、一定の間隔（ギャップ）を開けた要素同士で比較・交換を行うことで、
 * 要素を効率的に移動させます。
 * 本実装では標準的なシーケンス（n/2, n/4, ...）を使用します。
 *
 * - 時間計算量: O(n log n) ~ O(n^2)（ギャップシーケンスに依存）
 * - 空間計算量: O(1)
 */
class ShellSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.SHELL
    override val timeComplexity = "O(n log n) ~ O(n²)"
    override val spaceComplexity = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        val n = array.size
        var gap = n / 2

        while (gap > 0) {
            // Snapshot: Gap Update
            addSnapshot(array, emptyList(), "Gap set to $gap")

            // Gapped Insertion Sort
            for (i in gap until n) {
                // i番目の要素をgap間隔のソート済み部分列に挿入する
                // Insertion Sort同様、スワップで表現する

                for (j in i downTo gap step gap) {
                    // Compare array[j] and array[j - gap]
                    // If array[j] < array[j - gap], swap
                    if (compare(array[j], array[j - gap]) < 0) {
                        swap(array, j, j - gap)
                        // Snapshot: Swap
                        addSnapshot(
                            array,
                            listOf(j, j - gap),
                            "Swapped ${array[j - gap]} and ${array[j]} (gap=$gap)"
                        )
                    } else {
                        // ソート済みなのでbreak
                        break
                    }
                }
            }
            gap /= 2
        }
    }
}
