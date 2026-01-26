package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.quiz.QuizScreen
import dotnet.sort.presentation.feature.quiz.QuizViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.quizDestination(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToGuess: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable<Screen.Quiz> {
        val viewModel: QuizViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        
        QuizScreen(
            state = state,
            onIntent = viewModel::send,
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
