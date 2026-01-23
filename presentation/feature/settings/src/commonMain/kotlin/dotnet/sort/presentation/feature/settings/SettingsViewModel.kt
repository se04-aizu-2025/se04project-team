package dotnet.sort.presentation.feature.settings

import androidx.lifecycle.viewModelScope
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.domain.repository.SettingsRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val appVersion: String = "1.0.0"
) : UiState

sealed class SettingsIntent : Intent {
    data class ToggleTheme(val isDark: Boolean) : SettingsIntent()
}

@Factory
class SettingsViewModel(
    private val repository: SettingsRepository
) : BaseViewModel<SettingsState, SettingsIntent>(SettingsState()) {

    init {
        viewModelScope.launch {
            repository.isDarkTheme.collect { isDark ->
                updateState { copy(isDarkTheme = isDark) }
            }
        }
    }

    override fun send(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ToggleTheme -> {
                viewModelScope.launch {
                    repository.setDarkTheme(intent.isDark)
                }
            }
        }
    }
}
