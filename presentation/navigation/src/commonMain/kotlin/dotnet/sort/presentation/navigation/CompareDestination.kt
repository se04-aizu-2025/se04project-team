package dotnet.sort.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.compare.CompareScreen

/**
 * Compare 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
fun NavGraphBuilder.compareDestination(
    onBackClick: () -> Unit
) {
    composable<Screen.Compare> {
        CompareScreen(onBackClick = onBackClick)
    }
}

/**
 * Compare 画面へ遷移する。
 */
fun NavController.navigateToCompare() {
    navigate(Screen.Compare)
}
