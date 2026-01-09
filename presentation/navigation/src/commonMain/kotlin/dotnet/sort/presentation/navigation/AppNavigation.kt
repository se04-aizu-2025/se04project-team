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
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Home Screen (Coming Soon)")
            }
        }

        composable<Screen.Sort> {
            // Placeholder: integrate SortScreen when PR-28/29 is ready,
            // or if simple SortScreen exists (PR-24/25 implies minimal existence maybe?)
            // I'll check if SortScreen exists in presentation/feature/sort or if I should put placeholder
            // Checking the file list earlier, SortIntent.kt was there but SortScreen.kt was not confirmed
            // in the "viewed_code_item" list for "presentation/feature/sort" it had "SortIntent.kt"
            // Let's use a placeholder unless I confirmed SortScreen exists.
            Text("Sort Screen Placeholder")
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
