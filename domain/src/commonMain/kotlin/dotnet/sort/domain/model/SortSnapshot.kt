package dotnet.sort.domain.model

/**
 * ソート過程の「ある瞬間」の状態を表すスナップショット。
 *
 * GUIでのステップ実行やアニメーションなどの可視化に使用されます。
 *
 * @property arrayState その時点での配列全体の並び順
 * @property highlightingIndices その時点で注目している（比較・交換中の）要素のインデックスリスト。GUIで色を変えて表示するために使用します。
 * @property description そのステップで行われた操作の説明（例: "インデックス 3 と 4 を比較"）。ユーザーへの解説用。
 */
data class SortSnapshot(
    val arrayState: List<Int>,
    val highlightingIndices: List<Int>,
    val description: String
)