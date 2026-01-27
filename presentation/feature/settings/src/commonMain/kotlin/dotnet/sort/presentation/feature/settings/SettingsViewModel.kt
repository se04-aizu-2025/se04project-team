package dotnet.sort.presentation.feature.settings

import androidx.lifecycle.viewModelScope
import dotnet.sort.domain.repository.SettingsRepository
import dotnet.sort.domain.model.VisualizationTheme
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val visualizationTheme: VisualizationTheme = VisualizationTheme.KOTLIN,
    val isSoundEnabled: Boolean = true,
    val soundVolume: Float = 0.5f,
    val appVersion: String = "1.0.0"
) : UiState

sealed class SettingsIntent : Intent {
    data class ToggleTheme(val isDark: Boolean) : SettingsIntent()
    data class SelectVisualizationTheme(val theme: VisualizationTheme) : SettingsIntent()
    data class ToggleSound(val isEnabled: Boolean) : SettingsIntent()
    data class SetSoundVolume(val volume: Float) : SettingsIntent()
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
        viewModelScope.launch {
            repository.visualizationTheme.collect { theme ->
                updateState { copy(visualizationTheme = theme) }
            }
        }
        viewModelScope.launch {
            repository.isSoundEnabled.collect { isEnabled ->
                updateState { copy(isSoundEnabled = isEnabled) }
            }
        }
        viewModelScope.launch {
            repository.soundVolume.collect { volume ->
                updateState { copy(soundVolume = volume) }
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
            is SettingsIntent.SelectVisualizationTheme -> {
                viewModelScope.launch {
                    repository.setVisualizationTheme(intent.theme)
                }
            }
            is SettingsIntent.ToggleSound -> {
                viewModelScope.launch {
                    repository.setSoundEnabled(intent.isEnabled)
                }
            }
            is SettingsIntent.SetSoundVolume -> {
                viewModelScope.launch {
                    repository.setSoundVolume(intent.volume)
                }
            }
        }
    }
}
