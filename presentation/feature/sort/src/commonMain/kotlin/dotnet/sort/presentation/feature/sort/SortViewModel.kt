package dotnet.sort.presentation.feature.sort

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.presentation.common.viewmodel.UnidirectionalViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SortState(
    val isLoading: Boolean = false
) : UiState

class SortViewModel : BaseViewModel(), UnidirectionalViewModel<SortState, SortIntent> {

    private val _state = MutableStateFlow(SortState())
    override val state: StateFlow<SortState> = _state.asStateFlow()

    override fun send(intent: SortIntent) {
        when (intent) {
            // TODO: Implement intent handling in PR-30
            else -> {
                println("SortViewModel received intent: $intent")
            }
        }
    }
}
