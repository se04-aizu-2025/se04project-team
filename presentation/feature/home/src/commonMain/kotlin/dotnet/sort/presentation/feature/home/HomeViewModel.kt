package dotnet.sort.presentation.feature.home

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.usecase.GetLearningStatisticsUseCase
import dotnet.sort.usecase.LearningStatistics
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

sealed interface HomeIntent : dotnet.sort.presentation.common.viewmodel.Intent

data class HomeState(
    val isLoading: Boolean = false,
    val learningStatistics: LearningStatistics? = null,
) : UiState

@Factory
class HomeViewModel(
    private val getLearningStatisticsUseCase: GetLearningStatisticsUseCase,
) : BaseViewModel<HomeState, HomeIntent>(HomeState()) {

    init {
        loadLearningStatistics()
    }

    override fun send(intent: HomeIntent) = Unit

    private fun loadLearningStatistics() {
        execute {
            updateState { copy(isLoading = true) }

            getLearningStatisticsUseCase()
                .catch { error ->
                    // エラーハンドリング
                    updateState { copy(isLoading = false) }
                }
                .collectLatest { statistics ->
                    updateState {
                        copy(
                            isLoading = false,
                            learningStatistics = statistics,
                        )
                    }
                }
        }
    }
}
