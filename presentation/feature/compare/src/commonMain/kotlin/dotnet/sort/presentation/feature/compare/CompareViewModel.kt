package dotnet.sort.presentation.feature.compare

import androidx.lifecycle.viewModelScope
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import dotnet.sort.domain.generator.ArrayGeneratorType
import dotnet.sort.domain.usecase.ExecuteSortUseCase
import dotnet.sort.domain.usecase.GenerateArrayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory

@Factory
class CompareViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val generateArrayUseCase: GenerateArrayUseCase
) : BaseViewModel<CompareState, CompareIntent>(
    initialState = CompareState()
) {
    override fun send(intent: CompareIntent) {
        viewModelScope.launch {
            when (intent) {
                is CompareIntent.SelectAlgorithm1 -> updateAlgorithm1(intent)
                is CompareIntent.SelectAlgorithm2 -> updateAlgorithm2(intent)
                is CompareIntent.SetArraySize -> updateArraySize(intent)
                is CompareIntent.StartComparison -> startComparison()
                is CompareIntent.TogglePlay -> togglePlay()
                is CompareIntent.SetSpeed -> updateState { copy(playbackSpeed = intent.speed) }
                is CompareIntent.SetGeneratorType -> updateState { copy(generatorType = intent.type) }
                CompareIntent.Reset -> reset()
            }
        }
    }

    private fun startComparison() {
        val currentState = state.value
        if (currentState.isRunning) return

        updateState {
            copy(
                isRunning = true,
                isPlaying = false,
                currentStepIndex1 = 0,
                currentStepIndex2 = 0,
                algorithm1Result = null,
                algorithm2Result = null
            )
        }

        execute {
            // Generate array based on selected pattern
            val input = generateArrayUseCase(currentState.arraySize, currentState.generatorType)
            updateState { copy(initialArray = input) }

            // Run algorithms in parallel
            val deferred1 = async(Dispatchers.Default) {
                executeSortUseCase.execute(currentState.selectedAlgorithm1, input)
            }
            val deferred2 = async(Dispatchers.Default) {
                executeSortUseCase.execute(currentState.selectedAlgorithm2, input)
            }

            val result1 = deferred1.await()
            val result2 = deferred2.await()

            updateState {
                copy(
                    isRunning = false,
                    algorithm1Result = result1,
                    algorithm2Result = result2
                )
            }
        }
    }

    private fun togglePlay() {
        if (state.value.isPlaying) {
            updateState { copy(isPlaying = false) }
        } else {
            startAutoPlay()
        }
    }

    private fun startAutoPlay() {
        val currentState = state.value
        if (currentState.isPlaying) return
        
        // Check if we have results and not at the end
        val res1 = currentState.algorithm1Result
        val res2 = currentState.algorithm2Result
        if (res1 == null || res2 == null) return
        
        if (currentState.currentStepIndex1 >= res1.steps.size - 1 && 
            currentState.currentStepIndex2 >= res2.steps.size - 1) {
            return
        }

        updateState { copy(isPlaying = true) }
        
        execute {
            while (state.value.isPlaying) {
                val delayTime = (500L / state.value.playbackSpeed.coerceAtLeast(0.1f)).toLong()
                delay(delayTime)
                
                if (!state.value.isPlaying) break
                
                val current = state.value
                val r1 = current.algorithm1Result
                val r2 = current.algorithm2Result
                
                val canStep1 = r1 != null && current.currentStepIndex1 < r1.steps.size - 1
                val canStep2 = r2 != null && current.currentStepIndex2 < r2.steps.size - 1
                
                if (canStep1 || canStep2) {
                    updateState {
                        copy(
                            currentStepIndex1 = if (canStep1) currentStepIndex1 + 1 else currentStepIndex1,
                            currentStepIndex2 = if (canStep2) currentStepIndex2 + 1 else currentStepIndex2
                        )
                    }
                } else {
                    updateState { copy(isPlaying = false) }
                    break
                }
            }
        }
    }

    private fun reset() {
        updateState {
            copy(
                isPlaying = false,
                currentStepIndex1 = 0,
                currentStepIndex2 = 0
            )
        }
    }

    private fun updateAlgorithm1(intent: CompareIntent.SelectAlgorithm1) {
        updateState { copy(selectedAlgorithm1 = intent.sortType) }
    }

    private fun updateAlgorithm2(intent: CompareIntent.SelectAlgorithm2) {
        updateState { copy(selectedAlgorithm2 = intent.sortType) }
    }

    private fun updateArraySize(intent: CompareIntent.SetArraySize) {
        updateState { copy(arraySize = intent.size) }
    }
}
