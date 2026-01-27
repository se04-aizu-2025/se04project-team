package dotnet.sort.presentation.feature.learn

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import org.koin.core.annotation.Factory

@Factory
class LearnViewModel : BaseViewModel<LearnState, LearnIntent>(LearnState()) {

    override fun send(intent: LearnIntent) {
        when (intent) {
            is LearnIntent.SelectAlgorithm -> {
                updateState { copy(navigationTarget = intent.sortType) }
            }
            is LearnIntent.ConsumeNavigation -> {
                updateState { copy(navigationTarget = null) }
            }
        }
    }
}
