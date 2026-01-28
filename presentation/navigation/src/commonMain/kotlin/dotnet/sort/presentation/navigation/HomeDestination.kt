package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.home.HomeScreen
import dotnet.sort.presentation.feature.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Home 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 */
fun NavGraphBuilder.homeDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    composable<Screen.Home> {
        val viewModel: HomeViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        HomeScreen(
            isHomeSelected = currentScreen is Screen.Home,
            isSortSelected = currentScreen is Screen.Sort,
            isLearnSelected = currentScreen is Screen.Learn,
            isCompareSelected = currentScreen is Screen.Compare,
            isSettingsSelected = currentScreen is Screen.Settings,
            state = state,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSort = onNavigateToSort,
            onNavigateToLearn = onNavigateToLearn,
            onNavigateToCompare = onNavigateToCompare,
            onNavigateToQuiz = onNavigateToQuiz,
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}

/**
 * Home 画面へ遷移する。
 */
fun NavController.navigateToHome() {
    navigate(Screen.Home)
}
