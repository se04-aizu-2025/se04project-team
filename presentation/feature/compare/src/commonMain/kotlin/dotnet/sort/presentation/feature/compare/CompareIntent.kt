package dotnet.sort.presentation.feature.compare

import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.Intent

sealed class CompareIntent : Intent {
    data class SelectAlgorithm1(val sortType: SortType) : CompareIntent()
    data class SelectAlgorithm2(val sortType: SortType) : CompareIntent()
    data class SetArraySize(val size: Int) : CompareIntent()
    data object StartComparison : CompareIntent()
}
