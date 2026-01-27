package dotnet.sort.presentation.feature.quiz

import dotnet.sort.domain.quiz.model.QuizFeedback

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortBar
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.atoms.BarState
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortInfoRow
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * Quiz画面。
 *
 * @param state Quiz画面の状態
 * @param onIntent ユーザーアクションのコールバック
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
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
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
                items = listOf(
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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SpacingTokens.M),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            contentPadding = PaddingValues(
                top = SpacingTokens.FloatingTopBarInset,
                bottom = SpacingTokens.FloatingBottomBarInset,
            ),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                QuizContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndices by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.L)
    ) {
        if (!state.isGameActive) {
            if (state.showSummary) {
                QuizSummary(
                    state = state,
                    onRetry = { onIntent(QuizIntent.StartGame) },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Start screen
                SortText(
                    text = "🎮",
                    style = SortTheme.typography.displayLarge,
                )
                SortText(
                    text = "Sorting Speed Quiz",
                    style = SortTheme.typography.displayMedium,
                    color = SortTheme.colorScheme.primary,
                )
                SortText(
                    text = "Guess which elements will be swapped next!",
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = SpacingTokens.M),
                )
                SortButton(
                    text = "Start Game",
                    onClick = { onIntent(QuizIntent.StartGame) },
                    modifier = Modifier.padding(top = SpacingTokens.L)
                )
            }
        } else {
            // Game active
            val question = state.currentQuestion
            
            // Score and Timer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SortText(
                    text = "Score: ${state.score}",
                    style = SortTheme.typography.titleLarge,
                    color = SortTheme.colorScheme.primary
                )
                if (state.consecutiveCorrectCount > 1) {
                    SortText(
                        text = "Combo x${state.consecutiveCorrectCount}!",
                        style = SortTheme.typography.titleMedium,
                        color = SortTheme.colorScheme.secondary
                    )
                }
                SortText(
                    text = "Time: ${state.timeLeftSeconds}s",
                    style = SortTheme.typography.titleLarge,
                    color = if (state.timeLeftSeconds <= 3) {
                        SortTheme.colorScheme.error
                    } else {
                        SortTheme.colorScheme.onSurface
                    }
                )
            }
            
            if (question != null) {
                // Algorithm name
                SortText(
                    text = "Algorithm: ${question.algorithmType.displayName}",
                    style = SortTheme.typography.titleMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant
                )
                
                // Question
                SortText(
                    text = "Which two elements will be swapped next?",
                    style = SortTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = SpacingTokens.M)
                )
                
                // Array visualization
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom
                ) {
                    val maxValue = question.currentArray.maxOrNull() ?: 1
                    question.currentArray.forEachIndexed { index, value ->
                        val isSelected = selectedIndices?.let { 
                            it.first == index || it.second == index 
                        } ?: false
                        
                        val feedback = state.feedback
                        val barState = when {
                            feedback is QuizFeedback.Correct && isSelected -> BarState.Sorted
                            feedback is QuizFeedback.Incorrect && isSelected -> BarState.Comparing
                            feedback is QuizFeedback.Incorrect && 
                                (feedback.correctIndices.first == index || 
                                 feedback.correctIndices.second == index) -> BarState.Swapping
                            isSelected -> BarState.Selected
                            else -> BarState.Default
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = state.feedback == null) {
                                    selectedIndices = when {
                                        selectedIndices == null -> index to -1
                                        selectedIndices!!.first == index -> null
                                        selectedIndices!!.second == index -> selectedIndices!!.first to -1
                                        selectedIndices!!.second == -1 -> {
                                            val first = selectedIndices!!.first
                                            if (first < index) first to index else index to first
                                        }
                                        else -> index to -1
                                    }
                                }
                        ) {
                            SortBar(
                                value = value,
                                maxValue = maxValue,
                                state = barState,
                                modifier = Modifier.weight(1f)
                            )
                            SortText(
                                text = value.toString(),
                                style = SortTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                
                // Feedback
                when (val feedback = state.feedback) {
                    is QuizFeedback.Correct -> {
                        SortText(
                            text = "✅ Correct! +${feedback.scoreDelta} points",
                            style = SortTheme.typography.titleMedium,
                            color = SortTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = SpacingTokens.M)
                        )
                        SortButton(
                            text = "Next Question",
                            onClick = { 
                                selectedIndices = null
                                onIntent(QuizIntent.NextQuestion) 
                            },
                            modifier = Modifier.padding(top = SpacingTokens.M)
                        )
                    }
                    is QuizFeedback.Incorrect -> {
                        SortText(
                            text = "❌ Incorrect! Correct answer: indices ${feedback.correctIndices.first} and ${feedback.correctIndices.second}",
                            style = SortTheme.typography.titleMedium,
                            color = SortTheme.colorScheme.error,
                            modifier = Modifier.padding(top = SpacingTokens.M)
                        )
                        SortButton(
                            text = "Next Question",
                            onClick = { 
                                selectedIndices = null
                                onIntent(QuizIntent.NextQuestion) 
                            },
                            modifier = Modifier.padding(top = SpacingTokens.M)
                        )
                    }
                    null -> {
                        // Submit button
                        SortButton(
                            text = "Submit Answer",
                            onClick = {
                                selectedIndices?.let { indices ->
                                    if (indices.second != -1) {
                                        onIntent(QuizIntent.SubmitAnswer(indices))
                                    }
                                }
                            },
                            enabled = selectedIndices?.second != -1,
                            modifier = Modifier.padding(top = SpacingTokens.M)
                        )
                    }
                }
                
                // End game button
                SortButton(
                    text = "End Game",
                    onClick = { onIntent(QuizIntent.EndGame) },
                    modifier = Modifier.padding(top = SpacingTokens.S)
                )
            }
        }
    }
}

@Composable
private fun QuizSummary(
    state: QuizState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val total = state.totalAnsweredQuestions
    val accuracy = if (total == 0) 0 else (state.correctAnswers * 100) / total
    val weakness = state.incorrectCounts.entries
        .sortedByDescending { it.value }
        .take(3)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.L)
    ) {
        SortText(
            text = "🏁 Quiz Summary",
            style = SortTheme.typography.displayMedium,
            color = SortTheme.colorScheme.primary
        )

        SortSectionCard(title = "Results") {
            SortInfoRow(label = "Accuracy", value = "$accuracy%")
            SortInfoRow(label = "Total Score", value = state.score.toString())
            SortInfoRow(label = "Longest Streak", value = state.longestCorrectStreak.toString())
        }

        SortSectionCard(title = "Weak Algorithms") {
            if (weakness.isEmpty()) {
                SortText(
                    text = "No incorrect answers yet",
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant
                )
            } else {
                weakness.forEach { entry ->
                    SortInfoRow(
                        label = entry.key.displayName,
                        value = "${entry.value} misses"
                    )
                }
            }
        }

        SortButton(
            text = "Play Again",
            onClick = onRetry
        )
    }
}
