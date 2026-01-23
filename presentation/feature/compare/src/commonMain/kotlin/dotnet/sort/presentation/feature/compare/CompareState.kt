package dotnet.sort.presentation.feature.compare

import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.UiState

data class CompareState(
    val selectedAlgorithm1: SortType = SortType.BUBBLE,
    val selectedAlgorithm2: SortType = SortType.SELECTION,
    val arraySize: Int = 30, // Default size for comparison
    val generatorType: ArrayGeneratorType = ArrayGeneratorType.RANDOM,
    val isRunning: Boolean = false,
    val isPlaying: Boolean = false,
    val playbackSpeed: Float = 1.0f,
    val currentStepIndex1: Int = 0,
    val currentStepIndex2: Int = 0,
    val initialArray: List<Int> = emptyList(),
    val algorithm1Result: SortResult? = null,
    val algorithm2Result: SortResult? = null
) : UiState
