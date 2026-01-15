package dotnet.sort.presentation.common.viewmodel

import androidx.lifecycle.viewModelScope
import dotnet.sort.repository.SettingsRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class ThemeState(val isDarkTheme: Boolean = false) : UiState

sealed class ThemeIntent : Intent {
    data class SetDarkTheme(val isDark: Boolean) : ThemeIntent()
}

@Factory
class ThemeViewModel(
    private val repository: SettingsRepository
) : BaseViewModel<ThemeState, ThemeIntent>(ThemeState()) {

    init {
        viewModelScope.launch {
            repository.isDarkTheme.collect { isDark ->
                updateState { copy(isDarkTheme = isDark) }
            }
        }
    }

    override fun send(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SetDarkTheme -> {
                viewModelScope.launch {
                    repository.setDarkTheme(intent.isDark)
                }
            }
        }
    }
}
