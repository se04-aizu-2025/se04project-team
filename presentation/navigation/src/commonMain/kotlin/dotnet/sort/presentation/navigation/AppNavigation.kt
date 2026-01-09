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
            androidx.compose.foundation.layout.Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text("Home Screen (Coming Soon)")
                androidx.compose.material3.Button(onClick = { navController.navigate(Screen.Sort) }) {
                    Text("Go to Sort Screen")
                }
            }
        }

        composable<Screen.Sort> {
            SortScreen()
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
