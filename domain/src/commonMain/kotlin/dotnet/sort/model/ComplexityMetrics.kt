package dotnet.sort.model

/**
 * ソートアルゴリズムの計算量・性能指標を保持する値オブジェクト。
 *
 * @property comparisonCount 要素同士の比較が行われた回数。
 * @property swapCount 要素の交換(Swap)や書き込みが行われた回数。
 * @property executionTimeNs 実際の実行時間（ナノ秒）。
 * @property timeComplexity 時間計算量の理論値（例: "O(n^2)"）。
 * @property spaceComplexity 空間計算量の理論値（例: "O(1)"）。
 */
data class ComplexityMetrics(
    // --- 実測値 (Measured Values) ---
    val comparisonCount: Long,
    val swapCount: Long,
    val executionTimeNs: Long,

    // --- 理論値 (Theoretical Values) ---
    val timeComplexity: String,

    val spaceComplexity: String
)