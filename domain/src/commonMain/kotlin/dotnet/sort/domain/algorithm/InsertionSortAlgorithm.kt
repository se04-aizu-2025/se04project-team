package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.SortType

/**
 * 挿入ソートの実装クラス。
 *
 * 未ソート部分の先頭の要素を、ソート済み部分の適切な位置に挿入していく操作を繰り返します。
 * 可視化のため、隣接交換（スワップ）を繰り返す実装を採用しています。
 * - 時間計算量: O(n^2)
 * - 空間計算量: O(1)
 */
class InsertionSortAlgorithm : BaseSortAlgorithm() {

    override val type = SortType.INSERTION
    override val timeComplexity = "O(n²)"
    override val spaceComplexity = "O(1)"

    override fun doSort(array: MutableList<Int>) {
        val n = array.size
        // 1からn-1までループ
        for (i in 1 until n) {
            // Processing index i
            // ソート済み部分は 0..i-1
            // array[i] を適切な位置に挿入する
            
            // Snapshot: Processing new element
            addSnapshot(array, listOf(i), "Processing value ${array[i]} at index $i")

            // j を i から 1 まで減らしながら、array[j] < array[j-1] なら交換
            for (j in i downTo 1) {
                // 比較
                if (compare(array[j], array[j - 1]) < 0) {
                    swap(array, j, j - 1)
                    // Snapshot: Swapped
                    addSnapshot(array, listOf(j, j - 1), "Swapped ${array[j - 1]} and ${array[j]}")
                } else {
                    // 左側はソート済みなので、これ以上小さくなければ終了
                    break
                }
            }
        }
    }
}
