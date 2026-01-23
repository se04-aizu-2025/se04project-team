package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.domain.model.SortType

/**
 * アルゴリズムの実装コードを保持するデータクラス
 *
 * @param sortType 対象のソートアルゴリズム
 * @param code Kotlinでの実装コード
 * @param description コードの解説（オプション）
 */
data class AlgorithmImplementation(
    val sortType: SortType,
    val code: String,
    val description: String = ""
)
