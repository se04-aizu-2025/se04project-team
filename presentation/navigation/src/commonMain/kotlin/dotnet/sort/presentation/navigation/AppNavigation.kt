package dotnet.sort.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * アプリケーションのメインナビゲーション。
 */
import androidx.navigation.NavHostController

/**
 * アプリケーションのメインナビゲーション。
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val routeName = backStackEntry?.destination?.route?.substringAfterLast(".")
    val currentScreen =
        when (routeName) {
            "Home" -> Screen.Home
            "Sort" -> Screen.Sort
            "Learn" -> Screen.Learn

            "Compare" -> Screen.Compare
            "Settings" -> Screen.Settings
            "Quiz" -> Screen.Home
            else -> Screen.Home
        }

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = Modifier.fillMaxSize(),
    ) {
        homeDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToQuiz = {
                navController.navigate(Screen.Quiz)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
        )

        sortDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
            onBackClick = { navController.popBackStack() },
        )

        settingsDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
            onBackClick = { navController.popBackStack() },
        )

        learnDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
            onNavigateToDetail = { sortType ->
                navController.navigateToAlgorithmDetail(sortType)
            },
            onBackClick = { navController.popBackStack() },
        )

        algorithmDetailDestination(
            onBackClick = { navController.popBackStack() },
        )

        compareDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
            onBackClick = { navController.popBackStack() },
        )

        quizDestination(
            currentScreen = currentScreen,
            onNavigateToHome = {
                if (currentScreen !is Screen.Home) navController.navigate(Screen.Home)
            },
            onNavigateToSort = {
                if (currentScreen !is Screen.Sort) navController.navigate(Screen.Sort)
            },
            onNavigateToLearn = {
                if (currentScreen !is Screen.Learn) navController.navigate(Screen.Learn)
            },
            onNavigateToCompare = {
                if (currentScreen !is Screen.Compare) navController.navigate(Screen.Compare)
            },
            onNavigateToSettings = {
                if (currentScreen !is Screen.Settings) navController.navigate(Screen.Settings)
            },
            onBackClick = { navController.popBackStack() },
        )
    }
}
