package dotnet.sort.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dotnet.sort.presentation.feature.guess.GuessScreen
import dotnet.sort.presentation.feature.guess.GuessViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Guess 機能のナビゲーションを NavGraph に登録する。
 *
 * @param currentScreen 現在の画面
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToGuess Guess画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param onBackClick 戻るボタンのコールバック
 */
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.guessDestination(
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
    composable<Screen.Guess> {
        val viewModel: GuessViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        GuessScreen(
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
            onNavigateToGuess = onNavigateToGuess,
            onNavigateToSettings = onNavigateToSettings,
            onBackClick = onBackClick,
        )
    }
}

/**
 * Guess 画面へ遷移する。
 */
fun NavController.navigateToGuess() {
    navigate(Screen.Guess)
}