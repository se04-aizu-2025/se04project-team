package dotnet.sort.presentation.feature.sort

import dotnet.sort.algorithm.SortAlgorithmFactory
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.presentation.common.viewmodel.UnidirectionalViewModel
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Factory
// import org.koin.core.annotation.KoinViewModel

data class SortState(
    val isLoading: Boolean = false,
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 50,
    val generatorType: ArrayGeneratorType = ArrayGeneratorType.RANDOM,
    val initialNumbers: List<Int> = emptyList(),
    val currentNumbers: List<Int> = emptyList(),
    val sortResult: SortResult? = null,
    val currentStepIndex: Int = 0,
    val isPlaying: Boolean = false,
    val playbackSpeed: Float = 1.0f,
    val highlightingIndices: List<Int> = emptyList(),
    val stepDescription: String = ""
) : UiState

@Factory
class SortViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val generateArrayUseCase: GenerateArrayUseCase,
) : BaseViewModel(), UnidirectionalViewModel<SortState, SortIntent> {

    private val _state = MutableStateFlow(SortState())
    override val state: StateFlow<SortState> = _state.asStateFlow()

    override fun send(intent: SortIntent) {
        when (intent) {
            is SortIntent.SelectAlgorithm -> {
                _state.value = _state.value.copy(algorithm = intent.type)
                resetSort()
            }
            is SortIntent.SetArraySize -> {
                _state.value = _state.value.copy(arraySize = intent.size)
                generateArray()
            }
            is SortIntent.GenerateArray -> {
                _state.value = _state.value.copy(generatorType = intent.generatorType)
                generateArray()
            }
            SortIntent.StartSort -> startSort()
            SortIntent.ResetSort -> resetSort()
            SortIntent.PauseSort -> {
                _state.value = _state.value.copy(isPlaying = false)
            }
            SortIntent.ResumeSort -> {
                _state.value = _state.value.copy(isPlaying = true)
                // TODO: Auto-play logic will be implemented in PR-37
            }
            SortIntent.StepForward -> stepForward()
            SortIntent.StepBackward -> stepBackward()
            is SortIntent.SetSpeed -> {
                _state.value = _state.value.copy(playbackSpeed = intent.speedMultiplier)
            }
        }
    }

    private fun generateArray() {
        execute {
            val currentState = state.value
            val array = generateArrayUseCase(currentState.arraySize, currentState.generatorType)
            _state.value = currentState.copy(
                initialNumbers = array,
                currentNumbers = array,
                sortResult = null,
                currentStepIndex = 0,
                stepDescription = "Ready to sort",
                highlightingIndices = emptyList()
            )
        }
    }

    private fun startSort() {
        val currentState = state.value
        if (currentState.initialNumbers.isEmpty()) generateArray()
        
        execute {
            _state.value = _state.value.copy(isLoading = true)
            // Use initialNumbers to ensure clean sort
            val result = executeSortUseCase.execute(currentState.algorithm, _state.value.initialNumbers)
            _state.value = _state.value.copy(
                isLoading = false,
                sortResult = result,
                currentStepIndex = 0, // Start from beginning of visualization
                // Maybe keep initial state or show first step?
                // Usually step 0 is the initial state or close to it.
            )
            updateVisualizerState(0)
        }
    }

    private fun resetSort() {
        val currentState = state.value
        _state.value = currentState.copy(
            currentNumbers = currentState.initialNumbers,
            currentStepIndex = 0,
            isPlaying = false,
            highlightingIndices = emptyList(),
            stepDescription = "Reset"
        )
    }

    private fun stepForward() {
        val currentState = state.value
        val result = currentState.sortResult ?: return
        if (currentState.currentStepIndex < result.steps.size - 1) {
            updateVisualizerState(currentState.currentStepIndex + 1)
        }
    }

    private fun stepBackward() {
        val currentState = state.value
        if (currentState.currentStepIndex > 0) {
            updateVisualizerState(currentState.currentStepIndex - 1)
        }
    }

    private fun updateVisualizerState(index: Int) {
        val currentState = state.value
        val steps = currentState.sortResult?.steps ?: return
        if (index in steps.indices) {
            val snapshot = steps[index]
            _state.value = currentState.copy(
                currentNumbers = snapshot.arrayState,
                highlightingIndices = snapshot.highlightingIndices,
                stepDescription = snapshot.description,
                currentStepIndex = index
            )
        }
    }
}
