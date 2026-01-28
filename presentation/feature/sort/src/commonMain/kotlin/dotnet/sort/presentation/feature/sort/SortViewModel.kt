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
import dotnet.sort.presentation.common.sound.SoundEffect
import dotnet.sort.presentation.common.sound.SoundEffectPlayer
import dotnet.sort.repository.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withTimeout
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
    val sortedIndices: Set<Int> = emptySet(),
    val isCompleted: Boolean = false,
    val stepDescription: String = "",
) : UiState

@Factory
class SortViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val generateArrayUseCase: GenerateArrayUseCase,
    private val recordHistoryEventUseCase: RecordHistoryEventUseCase,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<SortState, SortIntent>(SortState()) {
    private val soundPlayer = SoundEffectPlayer()
    private var soundEnabled: Boolean = true
    private var soundVolume: Float = 0.6f

    init {
        execute {
            settingsRepository.isSoundEnabled.collect { enabled ->
                soundEnabled = enabled
            }
        }
        execute {
            settingsRepository.soundVolume.collect { volume ->
                soundVolume = volume
            }
        }
    }
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
                    sortedIndices = emptySet(),
                    isCompleted = false,
                    stepDescription = "Ready to sort",
                    highlightingIndices = emptyList(),
                )
            }
        }
    }

    private suspend fun prepareArrayIfNeeded(): List<Int> {
        val currentState = state.value
        if (currentState.initialNumbers.isNotEmpty()) {
            return currentState.initialNumbers
        }

        val array = generateArrayUseCase(currentState.arraySize, currentState.generatorType)
        updateState {
            copy(
                initialNumbers = array,
                currentNumbers = array,
                sortResult = null,
                currentStepIndex = 0,
                sortedIndices = emptySet(),
                isCompleted = false,
                stepDescription = "Ready to sort",
                highlightingIndices = emptyList(),
            )
        }
        return array
    }

    /**
     * ソートを実行し、結果を取得する（自動再生なし）
     */
    private fun executeSort() {
        execute {
            updateState { copy(isLoading = true) }
            try {
                val targetArray = prepareArrayIfNeeded()
                val result = executeSortUseCase.execute(state.value.algorithm, targetArray)
                updateState {
                    copy(
                        isLoading = false,
                        sortResult = result,
                        currentStepIndex = 0,
                        sortedIndices = emptySet(),
                        isCompleted = false,
                    )
                }
                updateVisualizerState(0)
                execute {
                    runCatching {
                        withTimeout(1_000) {
                            recordHistoryEventUseCase(
                                algorithmType = state.value.algorithm,
                                eventType = HistoryEventType.SortExecuted,
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                updateState {
                    copy(
                        isLoading = false,
                        isPlaying = false,
                        stepDescription = "Sort failed: ${throwable.message ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    /**
     * ソートを開始し、自動再生を開始する
     */
    private fun startSort() {
        execute {
            updateState { copy(isLoading = true) }
            try {
                val targetArray = prepareArrayIfNeeded()
                val result = executeSortUseCase.execute(state.value.algorithm, targetArray)
                updateState {
                    copy(
                        isLoading = false,
                        sortResult = result,
                        currentStepIndex = 0,
                        isPlaying = true,
                        sortedIndices = emptySet(),
                        isCompleted = false,
                    )
                }
                updateVisualizerState(0)
                execute {
                    runCatching {
                        withTimeout(1_000) {
                            recordHistoryEventUseCase(
                                algorithmType = state.value.algorithm,
                                eventType = HistoryEventType.SortExecuted,
                            )
                        }
                    }
                }
                startAutoPlay()
            } catch (throwable: Throwable) {
                updateState {
                    copy(
                        isLoading = false,
                        isPlaying = false,
                        stepDescription = "Sort failed: ${throwable.message ?: "Unknown error"}",
                    )
                }
            }
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
                    startCompletionWave()
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
                sortedIndices = emptySet(),
                isCompleted = false,
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
                    sortedIndices = if (isCompleted) sortedIndices else emptySet(),
                )
            }
            playStepSound(snapshot.description)
            if (index == steps.lastIndex) {
                startCompletionWave()
            }
        }
    }

    private fun startCompletionWave() {
        val currentState = state.value
        val size = currentState.currentNumbers.size
        if (size == 0 || currentState.isCompleted) return
        updateState { copy(isCompleted = true) }
        execute {
            for (i in 0 until size) {
                updateState {
                    copy(
                        sortedIndices = (0..i).toSet(),
                        stepDescription = if (i == size - 1) "Completed" else stepDescription,
                    )
                }
                delay(20)
            }
            playSound(SoundEffect.COMPLETE)
        }
    }

    private fun playStepSound(description: String) {
        if (!soundEnabled) return
        val lower = description.lowercase()
        when {
            "swap" in lower -> playSound(SoundEffect.SWAP)
            "compare" in lower -> playSound(SoundEffect.COMPARE)
        }
    }

    private fun playSound(effect: SoundEffect) {
        if (!soundEnabled) return
        soundPlayer.play(effect, soundVolume)
    }
}
