package dotnet.sort.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dotnet.sort.presentation.feature.home.HomeScreen
import dotnet.sort.presentation.feature.sort.SortScreen

/**
 * The main entry point for navigation in the application.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onNavigateToSort = { navController.navigate(Screen.Sort) },
                onNavigateToLearn = { navController.navigate(Screen.Learn) },
                onNavigateToCompare = { navController.navigate(Screen.Compare) },
                onNavigateToSettings = { navController.navigate(Screen.Settings) }
            )
        }

        composable<Screen.Sort> {
            SortScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Screen.Settings> {
             Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Settings Screen (Coming Soon)")
            }
        }
        
        composable<Screen.Learn> {
             Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Learn Screen (Coming Soon)")
            }
        }
        
        composable<Screen.Compare> {
             Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Compare Screen (Coming Soon)")
            }
        }
    }
}
