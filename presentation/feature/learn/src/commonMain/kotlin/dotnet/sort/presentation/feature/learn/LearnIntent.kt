package dotnet.sort.presentation.feature.learn

import dotnet.sort.domain.model.SortType
import dotnet.sort.presentation.common.viewmodel.Intent

sealed class LearnIntent : Intent {
    data class SelectAlgorithm(val sortType: SortType) : LearnIntent()
    data object ConsumeNavigation : LearnIntent()
}
