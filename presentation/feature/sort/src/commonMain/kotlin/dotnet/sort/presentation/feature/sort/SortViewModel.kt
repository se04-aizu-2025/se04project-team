package dotnet.sort.presentation.feature.sort

import dotnet.sort.algorithm.SortAlgorithmFactory
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.HistoryEventType
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import dotnet.sort.usecase.RecordHistoryEventUseCase
import kotlinx.coroutines.delay
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
    val stepDescription: String = "",
) : UiState

@Factory
class SortViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val generateArrayUseCase: GenerateArrayUseCase,
    private val recordHistoryEventUseCase: RecordHistoryEventUseCase,
) : BaseViewModel<SortState, SortIntent>(SortState()) {
    override fun send(intent: SortIntent) {
        when (intent) {
            is SortIntent.SelectAlgorithm -> {
                updateState { copy(algorithm = intent.type) }
                // Generate array if empty, then execute sort with new algorithm (no autoplay)
                if (state.value.initialNumbers.isEmpty()) generateArray()
                executeSort()
            }
            is SortIntent.SetArraySize -> {
                updateState { copy(arraySize = intent.size) }
                generateArray()
            }
            is SortIntent.GenerateArray -> {
                updateState { copy(generatorType = intent.generatorType) }
                generateArray()
            }
            SortIntent.StartSort -> startSort()
            SortIntent.ResetSort -> resetSort()
            SortIntent.ShuffleArray -> {
                generateArray()
                executeSort()
            }
            SortIntent.PauseSort -> {
                updateState { copy(isPlaying = false) }
            }
            SortIntent.ResumeSort -> {
                if (!state.value.isPlaying) {
                    updateState { copy(isPlaying = true) }
                    startAutoPlay()
                }
            }
            SortIntent.StepForward -> stepForward()
            SortIntent.StepBackward -> stepBackward()
            is SortIntent.SetSpeed -> {
                updateState { copy(playbackSpeed = intent.speedMultiplier) }
            }
            is SortIntent.SeekTo -> {
                updateVisualizerState(intent.stepIndex)
            }
        }
    }

    private fun generateArray() {
        execute {
            val currentState = state.value
            val array = generateArrayUseCase(currentState.arraySize, currentState.generatorType)
            updateState {
                copy(
                    initialNumbers = array,
                    currentNumbers = array,
                    sortResult = null,
                    currentStepIndex = 0,
                    stepDescription = "Ready to sort",
                    highlightingIndices = emptyList(),
                )
            }
        }
    }

    /**
     * ソートを実行し、結果を取得する（自動再生なし）
     */
    private fun executeSort() {
        val currentState = state.value
        if (currentState.initialNumbers.isEmpty()) generateArray()

        execute {
            updateState { copy(isLoading = true) }
            // Use initialNumbers to ensure clean sort
            val result = executeSortUseCase.execute(state.value.algorithm, state.value.initialNumbers)
            recordHistoryEventUseCase(
                algorithmType = state.value.algorithm,
                eventType = HistoryEventType.SortExecuted,
            )
            updateState {
                copy(
                    isLoading = false,
                    sortResult = result,
                    currentStepIndex = 0,
                )
            }
            updateVisualizerState(0)
        }
    }

    /**
     * ソートを開始し、自動再生を開始する
     */
    private fun startSort() {
        executeSort()
        execute {
            // Wait for executeSort to complete, then start playback
            // Small delay to ensure state is updated
            delay(50)
            updateState { copy(isPlaying = true) }
            startAutoPlay()
        }
    }

    private fun startAutoPlay() {
        execute {
            while (state.value.isPlaying) {
                // Determine base delay. 500ms base.
                val baseDelay = 500L
                val delayTime = (baseDelay / state.value.playbackSpeed).toLong()

                delay(delayTime)

                // Check if still playing after delay (could be paused during delay)
                if (!state.value.isPlaying) break

                val currentState = state.value
                val result = currentState.sortResult

                if (result != null && currentState.currentStepIndex < result.steps.size - 1) {
                    stepForward()
                } else {
                    // Finished or no result
                    updateState { copy(isPlaying = false) }
                    break
                }
            }
        }
    }

    private fun resetSort() {
        updateState {
            copy(
                currentNumbers = initialNumbers,
                currentStepIndex = 0,
                isPlaying = false,
                highlightingIndices = emptyList(),
                stepDescription = "Reset",
            )
        }
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
            updateState {
                copy(
                    currentNumbers = snapshot.arrayState,
                    highlightingIndices = snapshot.highlightingIndices,
                    stepDescription = snapshot.description,
                    currentStepIndex = index,
                )
            }
        }
    }
}
