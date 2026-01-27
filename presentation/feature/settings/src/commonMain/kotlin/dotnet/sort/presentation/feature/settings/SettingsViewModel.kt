package dotnet.sort.presentation.feature.settings

import androidx.lifecycle.viewModelScope
import dotnet.sort.domain.model.BarColorTheme
import dotnet.sort.domain.model.Language
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.repository.SettingsRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val appVersion: String = "1.0.0",
    val barTheme: BarColorTheme = BarColorTheme.KOTLIN,
    val isSoundEnabled: Boolean = true,
    val soundVolume: Float = 0.6f,
    val language: Language = Language.ENGLISH,
) : UiState

sealed class SettingsIntent : Intent {
    data class ToggleTheme(val isDark: Boolean) : SettingsIntent()
    data class SelectBarTheme(val theme: BarColorTheme) : SettingsIntent()
    data class ToggleSound(val enabled: Boolean) : SettingsIntent()
    data class SetSoundVolume(val volume: Float) : SettingsIntent()
    data class SelectLanguage(val language: Language) : SettingsIntent()
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
            repository.barTheme.collect { theme ->
                updateState { copy(barTheme = theme) }
            }
        }
        viewModelScope.launch {
            repository.isSoundEnabled.collect { enabled ->
                updateState { copy(isSoundEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            repository.soundVolume.collect { volume ->
                updateState { copy(soundVolume = volume) }
            }
        }
        viewModelScope.launch {
            repository.language.collect { language ->
                updateState { copy(language = language) }
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
            is SettingsIntent.SelectBarTheme -> {
                viewModelScope.launch {
                    repository.setBarTheme(intent.theme)
                }
            }
            is SettingsIntent.ToggleSound -> {
                viewModelScope.launch {
                    repository.setSoundEnabled(intent.enabled)
                }
            }
            is SettingsIntent.SetSoundVolume -> {
                viewModelScope.launch {
                    repository.setSoundVolume(intent.volume)
                }
            }
            is SettingsIntent.SelectLanguage -> {
                viewModelScope.launch {
                    repository.setLanguage(intent.language)
                }
            }
        }
    }
}
