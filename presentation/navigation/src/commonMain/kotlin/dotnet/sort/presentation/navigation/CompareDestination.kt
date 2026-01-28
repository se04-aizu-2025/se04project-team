package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.compare.CompareViewModel
import dotnet.sort.presentation.feature.compare.CompareScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Compare 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
fun NavGraphBuilder.compareDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.Compare> {
        val viewModel: CompareViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        CompareScreen(
            isHomeSelected = currentScreen is Screen.Home,
            isSortSelected = currentScreen is Screen.Sort,
            isLearnSelected = currentScreen is Screen.Learn,
            isCompareSelected = currentScreen is Screen.Compare,
            isSettingsSelected = currentScreen is Screen.Settings,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSort = onNavigateToSort,
            onNavigateToLearn = onNavigateToLearn,
            onNavigateToCompare = onNavigateToCompare,
            onNavigateToSettings = onNavigateToSettings,
            state = state,
            onIntent = viewModel::send,
            onBackClick = onBackClick,
        )
    }
}

/**
 * Compare 画面へ遷移する。
 */
fun NavController.navigateToCompare() {
    navigate(Screen.Compare)
}
