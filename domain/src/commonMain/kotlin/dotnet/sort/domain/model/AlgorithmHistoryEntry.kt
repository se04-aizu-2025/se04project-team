package dotnet.sort.domain.model

/**
 * アルゴリズムの利用履歴。
 *
 * @param id 履歴ID
 * @param algorithmType アルゴリズム種別
 * @param eventType イベント種別
 * @param createdAtMillis 作成時刻（ミリ秒）
 * @param metadata 拡張用メタデータ
 */
data class AlgorithmHistoryEntry(
    val id: Long,
    val algorithmType: SortType?,
    val eventType: HistoryEventType,
    val createdAtMillis: Long,
    val metadata: String? = null,
)
