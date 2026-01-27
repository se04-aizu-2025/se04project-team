package dotnet.sort.domain.repository

import kotlinx.coroutines.flow.Flow
import dotnet.sort.domain.model.VisualizationTheme

interface SettingsRepository {
    val isDarkTheme: Flow<Boolean>
    val visualizationTheme: Flow<VisualizationTheme>
    val isSoundEnabled: Flow<Boolean>
    val soundVolume: Flow<Float>
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setVisualizationTheme(theme: VisualizationTheme)
    suspend fun setSoundEnabled(isEnabled: Boolean)
    suspend fun setSoundVolume(volume: Float)
}
