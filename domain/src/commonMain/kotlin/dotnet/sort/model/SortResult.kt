package dotnet.sort.model

/**
 * ソートアルゴリズムの実行結果を保持するデータクラス。
 *
 * 最終的なソート結果だけでなく、可視化のための過程データや性能指標も含みます。
 *
 *
 * @property finalArray ソート完了後のリスト
 * @property steps 可視化用に記録された、ソート過程の全スナップショット([SortSnapshot])のリスト
 * @property complexityMetrics 実行時の比較回数や時間計算量などの性能指標([ComplexityMetrics])
 */
data class SortResult(
    val finalArray: List<Int>,
    val steps: List<SortSnapshot>,
    val complexityMetrics: ComplexityMetrics
)