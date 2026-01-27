package dotnet.sort.presentation.common.viewmodel

import androidx.lifecycle.viewModelScope
import dotnet.sort.domain.model.VisualizationTheme
import dotnet.sort.domain.repository.SettingsRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class ThemeState(
    val isDarkTheme: Boolean = false,
    val visualizationTheme: VisualizationTheme = VisualizationTheme.KOTLIN
) : UiState

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
        viewModelScope.launch {
            repository.visualizationTheme.collect { theme ->
                updateState { copy(visualizationTheme = theme) }
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
