package dotnet.sort.presentation.feature.home

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import org.koin.core.annotation.Factory

sealed interface HomeIntent : dotnet.sort.presentation.common.viewmodel.Intent

data class HomeState(
    val isLoading: Boolean = false,
) : UiState

@Factory
class HomeViewModel : BaseViewModel<HomeState, HomeIntent>(HomeState()) {
    override fun send(intent: HomeIntent) = Unit
}
