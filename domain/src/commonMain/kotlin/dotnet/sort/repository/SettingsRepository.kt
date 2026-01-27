package dotnet.sort.repository

import dotnet.sort.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isDarkTheme: Flow<Boolean>
    val language: Flow<Language>
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setLanguage(language: Language)
}
