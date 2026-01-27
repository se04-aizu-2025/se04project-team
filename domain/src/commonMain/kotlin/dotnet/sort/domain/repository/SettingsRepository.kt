package dotnet.sort.domain.repository

import kotlinx.coroutines.flow.Flow
import dotnet.sort.domain.model.VisualizationTheme

interface SettingsRepository {
    val isDarkTheme: Flow<Boolean>
    val visualizationTheme: Flow<VisualizationTheme>
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setVisualizationTheme(theme: VisualizationTheme)
}
