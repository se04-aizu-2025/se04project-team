package dotnet.sort.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.learn.LearnScreen

/**
 * Learn 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
fun NavGraphBuilder.learnDestination(
    onBackClick: () -> Unit
) {
    composable<Screen.Learn> {
        LearnScreen(onBackClick = onBackClick)
    }
}

/**
 * Learn 画面へ遷移する。
 */
fun NavController.navigateToLearn() {
    navigate(Screen.Learn)
}
