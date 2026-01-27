package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.learn.LearnViewModel
import dotnet.sort.presentation.feature.learn.LearnScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Learn 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
import androidx.navigation.toRoute
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.learn.AlgorithmDetailScreen

/**
 * Learn 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
fun NavGraphBuilder.learnDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToLearnDetail: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetail: (SortType) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.Learn> {
        val viewModel: LearnViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        LearnScreen(
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
            onNavigateToDetail = onNavigateToDetail,
            onBackClick = onBackClick,
        )
    }
}

/**
 * AlgorithmDetail 画面を NavGraph に登録する。
 */
fun NavGraphBuilder.algorithmDetailDestination(
    onBackClick: () -> Unit,
) {
    composable<Screen.AlgorithmDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<Screen.AlgorithmDetail>()
        val sortType = SortType.valueOf(args.sortTypeString)
        AlgorithmDetailScreen(
            sortType = sortType,
            onBackClick = onBackClick
        )
    }
}

/**
 * AlgorithmDetail 画面へ遷移する。
 */
fun NavController.navigateToAlgorithmDetail(sortType: SortType) {
    navigate(Screen.AlgorithmDetail(sortTypeString = sortType.name))
}

/**
 * Learn 画面へ遷移する。
 */
fun NavController.navigateToLearn() {
    navigate(Screen.Learn)
}
