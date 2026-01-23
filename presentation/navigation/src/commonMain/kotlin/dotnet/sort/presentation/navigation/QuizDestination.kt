package dotnet.sort.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.quiz.QuizScreen

/**
 * Quiz 機能のナビゲーションを NavGraph に登録する。
 *
 * @param currentScreen 現在の画面
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param onBackClick 戻るボタンのコールバック
 */
fun NavGraphBuilder.quizDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.Quiz> {
        QuizScreen(
            isHomeSelected = currentScreen is Screen.Home,
            isSortSelected = currentScreen is Screen.Sort,
            isLearnSelected = currentScreen is Screen.Learn,
            isCompareSelected = currentScreen is Screen.Compare,
            isQuizSelected = currentScreen is Screen.Quiz,
            isSettingsSelected = currentScreen is Screen.Settings,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSort = onNavigateToSort,
            onNavigateToLearn = onNavigateToLearn,
            onNavigateToCompare = onNavigateToCompare,
            onNavigateToQuiz = onNavigateToQuiz,
            onNavigateToSettings = onNavigateToSettings,
            onBackClick = onBackClick,
        )
    }
}

/**
 * Quiz 画面へ遷移する。
 */
fun NavController.navigateToQuiz() {
    navigate(Screen.Quiz)
}
