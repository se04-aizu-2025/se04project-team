package dotnet.sort.repository

import dotnet.sort.domain.model.Language
import dotnet.sort.storage.SettingsStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single

@Single
class SettingsRepositoryImpl : SettingsRepository {
    private val storage = SettingsStorage()
    private val _isDarkTheme = MutableStateFlow(storage.getBoolean(KEY_DARK_THEME, false))
    private val _language = MutableStateFlow(
        Language.fromCode(storage.getString(KEY_LANGUAGE, Language.ENGLISH.code))
    )

    override val isDarkTheme: Flow<Boolean> = _isDarkTheme.asStateFlow()
    override val language: Flow<Language> = _language.asStateFlow()

    override suspend fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        storage.putBoolean(KEY_DARK_THEME, isDark)
    }

    override suspend fun setLanguage(language: Language) {
        _language.value = language
        storage.putString(KEY_LANGUAGE, language.code)
    }

    private companion object {
        const val KEY_DARK_THEME = "settings.darkTheme"
        const val KEY_LANGUAGE = "settings.language"
    }
}
