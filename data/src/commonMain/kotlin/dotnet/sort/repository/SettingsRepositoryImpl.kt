package dotnet.sort.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import dotnet.sort.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

@Single
class SettingsRepositoryImpl : SettingsRepository {
    private val _isDarkTheme = MutableStateFlow(false) // Default to Light for now, or match system
    // In a real app, we would load from DataStore here.
    
    override val isDarkTheme: Flow<Boolean> = _isDarkTheme.asStateFlow()

    override suspend fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }
}
