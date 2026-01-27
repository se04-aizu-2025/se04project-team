package dotnet.sort.presentation.feature.learn

import dotnet.sort.model.SortType

/**
 * Learn画面で使用するアルゴリズムの表示モデル。
 */
data class LearnAlgorithmItem(
    val type: SortType,
    val title: String,
    val description: String,
    val icon: String,
)
