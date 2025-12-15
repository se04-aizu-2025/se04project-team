package dotnet.sort.model

/**
 * ソートアルゴリズムの共通インターフェース。
 */
interface SortAlgorithm {
    /**
     * 指定された整数リストをソートし、その結果と過程（スナップショット）を返します。
     *
     * @param input ソート対象の整数リスト（非破壊的であるべきですが、実装によりコピーを作成します）
     * @return [SortResult] ソート済みの配列、手順ごとのスナップショット、および計算量メトリクスを含む結果オブジェクト
     */
    fun sort(input: List<Int>): SortResult
}