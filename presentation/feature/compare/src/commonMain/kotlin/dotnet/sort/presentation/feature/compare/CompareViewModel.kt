package dotnet.sort.presentation.feature.compare

import androidx.lifecycle.viewModelScope
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            }
        }
    }

    private fun startComparison() {
        val currentState = state.value
        if (currentState.isRunning) return

        updateState {
            copy(
                isRunning = true,
                algorithm1Result = null,
                algorithm2Result = null
            )
        }

        execute {
            // Generate random array for comparison
            val input = generateArrayUseCase(currentState.arraySize, ArrayGeneratorType.RANDOM)

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
