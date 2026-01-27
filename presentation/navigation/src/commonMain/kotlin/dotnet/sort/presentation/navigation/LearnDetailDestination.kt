package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.learn.LearnDetailScreen
import dotnet.sort.presentation.feature.learn.LearnViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Learn 詳細画面のナビゲーションを NavGraph に登録する。
 */
fun NavGraphBuilder.learnDetailDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.LearnDetail> {
        val viewModel: LearnViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        LearnDetailScreen(
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
            onBackClick = onBackClick,
        )
    }
}

/**
 * Learn 詳細画面へ遷移する。
 */
fun NavController.navigateToLearnDetail() {
    navigate(Screen.LearnDetail)
}
