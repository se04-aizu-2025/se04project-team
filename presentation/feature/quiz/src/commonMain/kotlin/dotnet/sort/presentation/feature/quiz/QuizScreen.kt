package dotnet.sort.presentation.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * Quiz画面。
 *
 * ゲーミフィケーション機能として、ソートアルゴリズムに関するクイズを提供します。
 *
 * @param isHomeSelected Home選択状態
 * @param isSortSelected Sort選択状態
 * @param isLearnSelected Learn選択状態
 * @param isCompareSelected Compare選択状態
 * @param isQuizSelected Quiz選択状態
 * @param isSettingsSelected Settings選択状態
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param onBackClick 戻るボタンのコールバック
 * @param modifier Modifier
 */
@Composable
fun QuizScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isQuizSelected: Boolean,
    isSettingsSelected: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = "Quiz",
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            SortBottomBar(
                items =
                    listOf(
                        SortBottomBarItem(
                            icon = SortIcons.Home,
                            contentDescription = "Home",
                            selected = isHomeSelected,
                            onClick = onNavigateToHome,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Sort,
                            contentDescription = "Sort",
                            selected = isSortSelected,
                            onClick = onNavigateToSort,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Learn,
                            contentDescription = "Learn",
                            selected = isLearnSelected,
                            onClick = onNavigateToLearn,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Compare,
                            contentDescription = "Compare",
                            selected = isCompareSelected,
                            onClick = onNavigateToCompare,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Settings,
                            contentDescription = "Settings",
                            selected = isSettingsSelected,
                            onClick = onNavigateToSettings,
                        ),
                    ),
            )
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = SpacingTokens.M),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            contentPadding =
                PaddingValues(
                    top = SpacingTokens.FloatingTopBarInset,
                    bottom = SpacingTokens.FloatingBottomBarInset,
                ),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SortText(
                        text = "🎮",
                        style = SortTheme.typography.displayLarge,
                    )

                    SortText(
                        text = "Quiz Mode",
                        style = SortTheme.typography.displayMedium,
                        color = SortTheme.colorScheme.primary,
                    )

                    SortText(
                        text = "Coming Soon",
                        style = SortTheme.typography.titleMedium,
                        color = SortTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = SpacingTokens.S),
                    )

                    SortText(
                        text = "Test your knowledge of sorting algorithms with interactive quizzes!",
                        style = SortTheme.typography.bodyMedium,
                        color = SortTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = SpacingTokens.M),
                    )
                }
            }
        }
    }
}
