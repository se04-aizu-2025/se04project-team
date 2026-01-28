package dotnet.sort.presentation.feature.learn

import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.UiState

data class LearnState(
    val algorithms: List<LearnAlgorithmItem> = emptyList(),
    val navigationTarget: SortType? = null,
) : UiState
