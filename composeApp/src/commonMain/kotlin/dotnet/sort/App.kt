package dotnet.sort

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.presentation.common.viewmodel.ThemeViewModel
import dotnet.sort.presentation.navigation.AppNavigation
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

/**
 * The common entry point for the application.
 *
 * It sets up the [SortTheme] and the main [AppNavigation].
 * It also ensures Koin context is available for Compose (if needed, though startKoin is usually platform side).
 * Wrapping in KoinContext is sometimes needed if startKoin is completely external to Compose logic,
 * but here we just need to ensure we are inside the Koin scope.
 * Since we use `startKoin` in main.kt, `koin-compose` usually works out of the box.
 * We can wrap in `KoinContext` just in case, or leave it to standard CompositionLocal.
 */
@Composable
fun App() {
    KoinContext {
        val themeViewModel = koinViewModel<ThemeViewModel>()
        val state by themeViewModel.state.collectAsState()

        SortTheme(darkTheme = state.isDarkTheme) {
            AppNavigation()
        }
    }
}
