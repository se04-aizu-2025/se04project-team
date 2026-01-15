package dotnet.sort.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.home.HomeScreen

/**
 * Home 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 */
fun NavGraphBuilder.homeDestination(
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    composable<Screen.Home> {
        HomeScreen(
            onNavigateToSort = onNavigateToSort,
            onNavigateToLearn = onNavigateToLearn,
            onNavigateToCompare = onNavigateToCompare,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

/**
 * Home 画面へ遷移する。
 */
fun NavController.navigateToHome() {
    navigate(Screen.Home)
}
