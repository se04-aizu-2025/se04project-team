package dotnet.sort.presentation.feature.compare

import androidx.lifecycle.viewModelScope
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class CompareViewModel : BaseViewModel<CompareState, CompareIntent>(
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
        updateState { copy(isRunning = true) }
        // TODO: Implement comparison execution logic (PR-60)
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
