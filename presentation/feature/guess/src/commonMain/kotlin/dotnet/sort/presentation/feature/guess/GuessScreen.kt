package dotnet.sort.presentation.feature.guess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.components.organisms.SortVisualizer
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * アルゴリズム推測ゲーム画面。
 *
 * @param isHomeSelected Home選択状態
 * @param isSortSelected Sort選択状態
 * @param isLearnSelected Learn選択状態
 * @param isCompareSelected Compare選択状態
 * @param isSettingsSelected Settings選択状態
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@Composable
fun GuessScreen(
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
    onNavigateToGuess: () -> Unit,
    onNavigateToSettings: () -> Unit,
    state: GuessState,
    onIntent: (GuessIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SortScaffold(
        modifier = modifier,
        topBar = {
            SortTopBar(
                title = "Guess the Algorithm",
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
                        icon = SortIcons.Quiz,
                        contentDescription = "Quiz",
                        selected = isQuizSelected,
                        onClick = onNavigateToQuiz,
                    ),
                    SortBottomBarItem(
                        icon = SortIcons.Guess,
                        contentDescription = "Guess",
                        selected = true, // Guess画面ではGuessが選択されている
                        onClick = onNavigateToGuess,
                    ),
                    SortBottomBarItem(
                        icon = SortIcons.Settings,
                        contentDescription = "Settings",
                        selected = isSettingsSelected,
                        onClick = onNavigateToSettings,
                    ),
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(SpacingTokens.M),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
        ) {
            // Score Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(SpacingTokens.XS)
                ) {
                    SortText(
                        text = "Score: ${state.score}",
                        style = SortTheme.typography.titleMedium
                    )
                    SortText(
                        text = "Time: ${state.timeLeftSeconds}s",
                        style = SortTheme.typography.bodyMedium,
                        color = SortTheme.colorScheme.onSurfaceVariant
                    )
                }

                when (state.gamePhase) {
                    GuessGamePhase.RESULT -> {
                        val isCorrect = state.selectedAlgorithm == state.correctAlgorithm
                        SortText(
                            text = if (isCorrect) "Correct! +100" else "Wrong!",
                            style = SortTheme.typography.titleMedium,
                            color = if (isCorrect) SortTheme.colorScheme.primary else SortTheme.colorScheme.error
                        )
                    }
                    else -> Spacer(modifier = Modifier)
                }
            }

            if (state.gamePhase == GuessGamePhase.WAITING) {
                DifficultySelector(
                    selectedDifficulty = state.difficulty,
                    onDifficultySelected = { onIntent(GuessIntent.SelectDifficulty(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Animation Area
            SortVisualizer(
                array = state.currentArray,
                highlightIndices = state.highlightIndices,
                description = when (state.gamePhase) {
                    GuessGamePhase.WAITING -> "Press 'Start Game' to begin"
                    GuessGamePhase.ANIMATING -> "Watch the sorting animation..."
                    GuessGamePhase.SELECTING -> "Which algorithm is this?"
                    GuessGamePhase.RESULT -> {
                        val isCorrect = state.selectedAlgorithm == state.correctAlgorithm
                        if (isCorrect) "Correct! It's ${state.correctAlgorithm?.displayName}" else "Wrong! It's ${state.correctAlgorithm?.displayName}"
                    }
                    GuessGamePhase.FINISHED -> "Game finished!"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            // Hint Display
            state.hint?.let { hint ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
                ) {
                    SortText(
                        text = "Hint:",
                        style = SortTheme.typography.titleSmall
                    )
                    SortText(
                        text = "Time: ${hint.timeComplexity}, Space: ${hint.spaceComplexity}",
                        style = SortTheme.typography.bodyMedium
                    )
                    SortText(
                        text = hint.description,
                        style = SortTheme.typography.bodySmall
                    )
                }
            }

            // Algorithm Selection Grid
            if (state.gamePhase == GuessGamePhase.SELECTING) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                    verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
                ) {
                    items(state.availableAlgorithms) { algorithm ->
                        val isSelected = state.selectedAlgorithm == algorithm
                        SortButton(
                            text = algorithm.displayName,
                            onClick = { onIntent(GuessIntent.SelectAlgorithm(algorithm)) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !state.isAnimationPlaying,
                            style = if (isSelected) SortButtonStyle.Primary else SortButtonStyle.Outlined
                        )
                    }
                }
            }

            // Control Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                when (state.gamePhase) {
                    GuessGamePhase.WAITING -> {
                        SortButton(
                            text = "Start Game",
                            onClick = { onIntent(GuessIntent.StartGame) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    GuessGamePhase.ANIMATING -> {
                        if (!state.isAnimationPlaying) {
                            SortButton(
                                text = "Start Animation",
                                onClick = { onIntent(GuessIntent.StartAnimation) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    GuessGamePhase.SELECTING -> {
                        SortButton(
                            text = if (state.hint == null) "Show Hint" else "Hint Shown",
                            onClick = { onIntent(GuessIntent.ShowHint) },
                            modifier = Modifier.weight(1f),
                            enabled = state.hint == null
                        )

                        state.selectedAlgorithm?.let {
                            SortButton(
                                text = "Confirm Answer",
                                onClick = { onIntent(GuessIntent.ConfirmAnswer) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    GuessGamePhase.RESULT -> {
                        SortButton(
                            text = "Next Question",
                            onClick = { onIntent(GuessIntent.NextQuestion) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    GuessGamePhase.FINISHED -> {
                        SortButton(
                            text = "Play Again",
                            onClick = { onIntent(GuessIntent.StartGame) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DifficultySelector(
    selectedDifficulty: GuessDifficulty,
    onDifficultySelected: (GuessDifficulty) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
    ) {
        SortText(
            text = "Difficulty",
            style = SortTheme.typography.titleSmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
        ) {
            GuessDifficulty.entries.forEach { difficulty ->
                val isSelected = selectedDifficulty == difficulty
                SortButton(
                    text = difficulty.displayName,
                    onClick = { onDifficultySelected(difficulty) },
                    style = if (isSelected) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        SortText(
            text = "Array ${selectedDifficulty.arraySize} • ${selectedDifficulty.timeLimitSeconds}s",
            style = SortTheme.typography.bodySmall,
            color = SortTheme.colorScheme.onSurfaceVariant
        )
    }
}