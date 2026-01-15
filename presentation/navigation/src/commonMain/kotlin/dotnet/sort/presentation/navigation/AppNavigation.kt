package dotnet.sort.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 * アプリケーションのメインナビゲーション。
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        homeDestination(
            onNavigateToSort = { navController.navigate(Screen.Sort) },
            onNavigateToLearn = { navController.navigate(Screen.Learn) },
            onNavigateToCompare = { navController.navigate(Screen.Compare) },
            onNavigateToSettings = { navController.navigate(Screen.Settings) }
        )

        sortDestination(
            onBackClick = { navController.popBackStack() }
        )

        settingsDestination(
            onBackClick = { navController.popBackStack() }
        )

        learnDestination(
            onBackClick = { navController.popBackStack() }
        )

        compareDestination(
            onBackClick = { navController.popBackStack() }
        )
    }
}
