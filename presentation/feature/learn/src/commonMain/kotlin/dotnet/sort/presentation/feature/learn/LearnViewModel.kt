package dotnet.sort.presentation.feature.learn

import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import org.koin.core.annotation.Factory

@Factory
class LearnViewModel : BaseViewModel<LearnState, LearnIntent>(LearnState()) {

    override fun send(intent: LearnIntent) {
        when (intent) {
            is LearnIntent.SelectAlgorithm -> {
                // TODO: Handle navigation in PR-52
            }
        }
    }
}
