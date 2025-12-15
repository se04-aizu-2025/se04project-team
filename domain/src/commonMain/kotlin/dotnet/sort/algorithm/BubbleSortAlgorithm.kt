package dotnet.sort.algorithm

import dotnet.sort.model.ComplexityMetrics
import dotnet.sort.model.SortAlgorithm
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortSnapshot
import kotlin.time.measureTime

/**
 * バブルソート（単方向バブルソート）の実装クラス。
 *
 * 隣り合う要素の比較と交換を繰り返し、最大（または最小）の要素を数列の端に移動させていく単純なソートアルゴリズムです。
 * - 時間計算量: O(n^2)
 * - 空間計算量: O(1)
 */
class BubbleSortAlgorithm : SortAlgorithm {

    override fun sort(input: List<Int>): SortResult {
        val array = input.toMutableList()
        val steps = mutableListOf<SortSnapshot>()
        var comparisonCount = 0L
        var swapCount = 0L

        // Initial State
        steps.add(SortSnapshot(array.toList(), emptyList(), "Start"))

        val duration = measureTime {
            val n = array.size
            for (i in 0 until n - 1) {
                for (j in 0 until n - i - 1) {
                    comparisonCount++
                    
                    // Snapshot for comparison (Optional: can be verbose for large N)
                    steps.add(
                        SortSnapshot(
                            array.toList(),
                            listOf(j, j + 1),
                            "Comparing indices $j and ${j + 1}"
                        )
                    )

                    if (array[j] > array[j + 1]) {
                        val temp = array[j]
                        array[j] = array[j + 1]
                        array[j + 1] = temp
                        swapCount++

                        steps.add(
                            SortSnapshot(
                                array.toList(),
                                listOf(j, j + 1),
                                "Swap ${array[j + 1]} and ${array[j]}"
                            )
                        )
                    }
                }
            }
        }

        // Final State
        steps.add(SortSnapshot(array.toList(), emptyList(), "Sorted"))

        val metrics = ComplexityMetrics(
            comparisonCount = comparisonCount,
            swapCount = swapCount,
            executionTimeNs = duration.inWholeNanoseconds,
            timeComplexity = "O(n^2)",
            spaceComplexity = "O(1)"
        )

        return SortResult(array, steps, metrics)
    }
}