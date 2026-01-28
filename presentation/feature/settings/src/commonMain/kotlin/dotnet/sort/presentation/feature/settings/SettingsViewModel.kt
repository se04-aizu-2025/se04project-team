package dotnet.sort.presentation.feature.settings

import dotnet.sort.domain.model.BarColorTheme
import dotnet.sort.domain.model.Language
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.repository.SettingsRepository
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
        execute {
            repository.isDarkTheme.collect { isDark ->
                updateState { copy(isDarkTheme = isDark) }
            }
        }
        execute {
            repository.barTheme.collect { theme ->
                updateState { copy(barTheme = theme) }
            }
        }
        execute {
            repository.isSoundEnabled.collect { enabled ->
                updateState { copy(isSoundEnabled = enabled) }
            }
        }
        execute {
            repository.soundVolume.collect { volume ->
                updateState { copy(soundVolume = volume) }
            }
        }
        execute {
            repository.language.collect { language ->
                updateState { copy(language = language) }
            }
        }
    }

    override fun send(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ToggleTheme -> {
                execute {
                    repository.setDarkTheme(intent.isDark)
                }
            }
            is SettingsIntent.SelectBarTheme -> {
                execute {
                    repository.setBarTheme(intent.theme)
                }
            }
            is SettingsIntent.ToggleSound -> {
                execute {
                    repository.setSoundEnabled(intent.enabled)
                }
            }
            is SettingsIntent.SetSoundVolume -> {
                execute {
                    repository.setSoundVolume(intent.volume)
                }
            }
            is SettingsIntent.SelectLanguage -> {
                execute {
                    repository.setLanguage(intent.language)
                }
            }
        }
    }
}
