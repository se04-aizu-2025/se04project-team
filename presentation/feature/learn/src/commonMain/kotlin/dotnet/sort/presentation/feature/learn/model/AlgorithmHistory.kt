package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.domain.model.SortType

data class AlgorithmHistory(
    val sortType: SortType,
    val originYear: String,
    val inventor: String,
    val description: String
)
