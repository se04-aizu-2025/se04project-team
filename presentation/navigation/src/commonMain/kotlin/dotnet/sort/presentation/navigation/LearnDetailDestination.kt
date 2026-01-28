package dotnet.sort.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.learn.AlgorithmDetailScreen

/**
 * Learn Detail 画面のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
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
    composable<Screen.LearnDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<Screen.LearnDetail>()
        val sortType = runCatching { SortType.valueOf(args.sortTypeName) }
            .getOrElse { SortType.BUBBLE }

        AlgorithmDetailScreen(
            sortType = sortType,
            onBackClick = onBackClick,
        )
    }
}

/**
 * Learn Detail 画面へ遷移する。
 */
fun NavController.navigateToLearnDetail(sortType: SortType) {
    navigate(Screen.LearnDetail(sortTypeName = sortType.name))
}
