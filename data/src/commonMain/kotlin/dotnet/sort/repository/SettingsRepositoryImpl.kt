package dotnet.sort.repository

import dotnet.sort.domain.model.BarColorTheme
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
    private val _barTheme = MutableStateFlow(
        BarColorTheme.fromCode(storage.getString(KEY_BAR_THEME, BarColorTheme.KOTLIN.code))
    )
    private val _isSoundEnabled = MutableStateFlow(storage.getBoolean(KEY_SOUND_ENABLED, true))
    private val _soundVolume = MutableStateFlow(storage.getFloat(KEY_SOUND_VOLUME, 0.6f))

    override val isDarkTheme: Flow<Boolean> = _isDarkTheme.asStateFlow()
    override val language: Flow<Language> = _language.asStateFlow()
    override val barTheme: Flow<BarColorTheme> = _barTheme.asStateFlow()
    override val isSoundEnabled: Flow<Boolean> = _isSoundEnabled.asStateFlow()
    override val soundVolume: Flow<Float> = _soundVolume.asStateFlow()

    override suspend fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        storage.putBoolean(KEY_DARK_THEME, isDark)
    }

    override suspend fun setLanguage(language: Language) {
        _language.value = language
        storage.putString(KEY_LANGUAGE, language.code)
    }

    override suspend fun setBarTheme(theme: BarColorTheme) {
        _barTheme.value = theme
        storage.putString(KEY_BAR_THEME, theme.code)
    }

    override suspend fun setSoundEnabled(enabled: Boolean) {
        _isSoundEnabled.value = enabled
        storage.putBoolean(KEY_SOUND_ENABLED, enabled)
    }

    override suspend fun setSoundVolume(volume: Float) {
        _soundVolume.value = volume
        storage.putFloat(KEY_SOUND_VOLUME, volume)
    }

    private companion object {
        const val KEY_DARK_THEME = "settings.darkTheme"
        const val KEY_LANGUAGE = "settings.language"
        const val KEY_BAR_THEME = "settings.barTheme"
        const val KEY_SOUND_ENABLED = "settings.soundEnabled"
        const val KEY_SOUND_VOLUME = "settings.soundVolume"
    }
}
