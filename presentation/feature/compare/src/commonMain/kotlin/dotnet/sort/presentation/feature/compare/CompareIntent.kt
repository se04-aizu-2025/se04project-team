package dotnet.sort.presentation.feature.compare

import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.Intent

sealed class CompareIntent : Intent {
    data class SelectAlgorithm1(val sortType: SortType) : CompareIntent()
    data class SelectAlgorithm2(val sortType: SortType) : CompareIntent()
    data class SetArraySize(val size: Int) : CompareIntent()
    data class SetGeneratorType(val type: ArrayGeneratorType) : CompareIntent()
    data object StartComparison : CompareIntent()
    data object TogglePlay : CompareIntent()
    data class SetSpeed(val speed: Float) : CompareIntent()
    data object Reset : CompareIntent()
}
