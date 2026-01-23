package dotnet.sort.domain.algorithm

import dotnet.sort.domain.model.ComplexityMetrics
import dotnet.sort.domain.model.SortAlgorithm
import dotnet.sort.domain.model.SortResult
import dotnet.sort.domain.model.SortSnapshot
import dotnet.sort.domain.model.SortType
import kotlin.time.TimeSource

/**
 * ソートアルゴリズムの基底クラス。
 *
 * 共通の計測処理（比較回数、交換回数、実行時間）や、
 * 可視化用のスナップショット記録処理を提供します。
 * 各具体的アルゴリズムはこのクラスを継承し、[doSort] メソッドを実装してください。
 */
abstract class BaseSortAlgorithm : SortAlgorithm {

    /**
     * ソート過程のスナップショットリスト。
     */
    protected val snapshots = mutableListOf<SortSnapshot>()

    /**
     * 比較回数。
     */
    protected var comparisonCount = 0L

    /**
     * 交換回数。
     */
    protected var swapCount = 0L

    /**
     * アルゴリズムの種類。
     */
    abstract override val type: SortType

    /**
     * ソートの実装（サブクラスでオーバーライド）。
     *
     * @param array ソート対象の配列（可変リスト）
     */
    protected abstract fun doSort(array: MutableList<Int>)

    /**
     * 時間計算量の理論値（表示用）。
     */
    protected abstract val timeComplexity: String

    /**
     * 空間計算量の理論値（表示用）。
     */
    protected abstract val spaceComplexity: String

    override fun sort(input: List<Int>): SortResult {
        snapshots.clear()
        comparisonCount = 0
        swapCount = 0

        val array = input.toMutableList()

        // Initial snapshot
        addSnapshot(array, emptyList(), "Start")

        val start = TimeSource.Monotonic.markNow()
        doSort(array)
        val elapsedNs = start.elapsedNow().inWholeNanoseconds

        // Final snapshot
        addSnapshot(array, emptyList(), "Finished")

        return SortResult(
            finalArray = array,
            steps = snapshots.toList(),
            complexityMetrics = ComplexityMetrics(
                comparisonCount = comparisonCount,
                swapCount = swapCount,
                executionTimeNs = elapsedNs,
                timeComplexity = timeComplexity,
                spaceComplexity = spaceComplexity
            )
        )
    }

    /**
     * 2つの要素を比較します。
     * 比較回数をインクリメントします。
     *
     * @param a 比較する値1
     * @param b 比較する値2
     * @return [Comparable.compareTo] の結果
     */
    protected fun compare(a: Int, b: Int): Int {
        comparisonCount++
        return a.compareTo(b)
    }

    /**
     * 配列内の2つの要素を交換します。
     * 交換回数をインクリメントします。
     *
     * @param array 対象の配列
     * @param i インデックス1
     * @param j インデックス2
     */
    protected fun swap(array: MutableList<Int>, i: Int, j: Int) {
        swapCount++
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    /**
     * 現在の状態をスナップショットとして記録します。
     *
     * @param array 現在の配列の状態
     * @param highlightingIndices 注目している（色を変える）インデックスのリスト
     * @param description 操作の説明
     */
    protected fun addSnapshot(array: List<Int>, highlightingIndices: List<Int>, description: String) {
        snapshots.add(SortSnapshot(array.toList(), highlightingIndices, description))
    }
}
