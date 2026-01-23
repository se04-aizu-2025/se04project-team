package dotnet.sort.presentation.feature.learn

import dotnet.sort.domain.model.SortType
import dotnet.sort.presentation.common.viewmodel.UiState

data class LearnState(
    val algorithms: List<SortType> = SortType.entries,
    val navigationTarget: SortType? = null
) : UiState
