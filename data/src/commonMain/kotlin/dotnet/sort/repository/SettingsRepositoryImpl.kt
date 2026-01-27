package dotnet.sort.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import dotnet.sort.domain.model.VisualizationTheme
import dotnet.sort.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

@Single
class SettingsRepositoryImpl : SettingsRepository {
    private val _isDarkTheme = MutableStateFlow(false) // Default to Light for now, or match system
    private val _visualizationTheme = MutableStateFlow(VisualizationTheme.KOTLIN)
    private val _isSoundEnabled = MutableStateFlow(true)
    private val _soundVolume = MutableStateFlow(0.5f)
    // In a real app, we would load from DataStore here.
    
    override val isDarkTheme: Flow<Boolean> = _isDarkTheme.asStateFlow()
    override val visualizationTheme: Flow<VisualizationTheme> = _visualizationTheme.asStateFlow()
    override val isSoundEnabled: Flow<Boolean> = _isSoundEnabled.asStateFlow()
    override val soundVolume: Flow<Float> = _soundVolume.asStateFlow()

    override suspend fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

    override suspend fun setVisualizationTheme(theme: VisualizationTheme) {
        _visualizationTheme.value = theme
    }

    override suspend fun setSoundEnabled(isEnabled: Boolean) {
        _isSoundEnabled.value = isEnabled
    }

    override suspend fun setSoundVolume(volume: Float) {
        _soundVolume.value = volume.coerceIn(0f, 1f)
    }
}
