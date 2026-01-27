package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.quiz.QuizScreen
import dotnet.sort.presentation.feature.quiz.QuizViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Quiz 機能のナビゲーションを NavGraph に登録する。
 */
fun NavGraphBuilder.quizDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.Quiz> {
        val viewModel: QuizViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        QuizScreen(
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
 * Quiz 画面へ遷移する。
 */
fun NavController.navigateToQuiz() {
    navigate(Screen.Quiz)
}
