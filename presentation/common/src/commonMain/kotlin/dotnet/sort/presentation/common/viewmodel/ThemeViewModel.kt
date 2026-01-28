package dotnet.sort.presentation.common.viewmodel

import dotnet.sort.domain.model.BarColorTheme
import dotnet.sort.repository.SettingsRepository
import org.koin.core.annotation.Factory

data class ThemeState(
    val isDarkTheme: Boolean = false,
    val barTheme: BarColorTheme = BarColorTheme.KOTLIN,
) : UiState

sealed class ThemeIntent : Intent {
    data class SetDarkTheme(val isDark: Boolean) : ThemeIntent()
}

@Factory
class ThemeViewModel(
    private val repository: SettingsRepository
) : BaseViewModel<ThemeState, ThemeIntent>(ThemeState()) {

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
    }

    override fun send(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SetDarkTheme -> {
                execute {
                    repository.setDarkTheme(intent.isDark)
                }
            }
        }
    }
}
