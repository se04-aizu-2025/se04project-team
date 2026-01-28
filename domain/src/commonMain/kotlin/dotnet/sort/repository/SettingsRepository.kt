package dotnet.sort.repository

import dotnet.sort.domain.model.BarColorTheme
import dotnet.sort.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isDarkTheme: Flow<Boolean>
    val language: Flow<Language>
    val barTheme: Flow<BarColorTheme>
    val isSoundEnabled: Flow<Boolean>
    val soundVolume: Flow<Float>
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setLanguage(language: Language)
    suspend fun setBarTheme(theme: BarColorTheme)
    suspend fun setSoundEnabled(enabled: Boolean)
    suspend fun setSoundVolume(volume: Float)
}
