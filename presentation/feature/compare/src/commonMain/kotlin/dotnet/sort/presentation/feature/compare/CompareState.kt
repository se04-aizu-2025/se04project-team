package dotnet.sort.presentation.feature.compare

import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.UiState

data class CompareState(
    val selectedAlgorithm1: SortType = SortType.BUBBLE,
    val selectedAlgorithm2: SortType = SortType.SELECTION,
    val arraySize: Int = 30, // Default size for comparison
    val isRunning: Boolean = false,
    val algorithm1Result: SortResult? = null,
    val algorithm2Result: SortResult? = null
) : UiState
