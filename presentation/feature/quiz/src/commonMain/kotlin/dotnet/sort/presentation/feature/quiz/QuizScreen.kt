package dotnet.sort.presentation.feature.quiz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.ScorePeriod
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * Quiz 画面。
 */
@Composable
fun QuizScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isSettingsSelected: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
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
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(SpacingTokens.M),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        ) {
            SortSectionCard(title = "Mode") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    QuizMode.entries.forEach { mode ->
                        SortButton(
                            text = mode.name,
                            onClick = { onIntent(QuizIntent.SelectMode(mode)) },
                            style = if (state.mode == mode) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            SortSectionCard(title = "Difficulty") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    QuizDifficulty.entries.forEach { difficulty ->
                        SortButton(
                            text = difficulty.name,
                            onClick = { onIntent(QuizIntent.SelectDifficulty(difficulty)) },
                            style = if (state.difficulty == difficulty) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            SortSectionCard(title = "Status") {
                SortText(text = "Time: ${state.timeRemainingMs / 1000.0}s")
                SortText(text = "Question: ${state.questionIndex + 1}/${state.totalQuestions}")
            }

            SortSectionCard(title = "Score") {
                SortText(text = "Score: ${state.score}")
                SortText(text = "Streak: ${state.streak}")
                SortText(text = "Correct: ${state.correctCount}/${state.totalQuestions}")
                SortText(text = "Longest streak: ${state.longestStreak}")
            }

            SortSectionCard(title = "Question") {
                SortText(text = state.question.prompt)
                if (state.mode == QuizMode.GUESS_ALGORITHM && state.question.hint != null) {
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                    SortButton(
                        text = if (state.showHint) "Hide Hint" else "Show Hint",
                        onClick = { onIntent(QuizIntent.ToggleHint) },
                        style = SortButtonStyle.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (state.showHint) {
                        Spacer(modifier = Modifier.height(SpacingTokens.S))
                        SortText(text = "Hint: ${state.question.hint}")
                    }
                }
            }

            SortSectionCard(title = "Options") {
                state.question.options.forEachIndexed { index, option ->
                    SortButton(
                        text = option,
                        onClick = { onIntent(QuizIntent.SelectOption(index)) },
                        style = if (state.selectedIndex == index) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                }
            }

            SortButton(
                text = "Start",
                onClick = { onIntent(QuizIntent.StartQuiz) },
                style = SortButtonStyle.Primary,
                modifier = Modifier.fillMaxWidth(),
            )

            SortButton(
                text = "Submit",
                onClick = { onIntent(QuizIntent.SubmitAnswer) },
                style = SortButtonStyle.Primary,
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.isCorrect != null) {
                SortSectionCard(title = "Result") {
                    SortText(text = if (state.isCorrect) "Correct!" else "Incorrect.")
                }
                SortButton(
                    text = "Next",
                    onClick = { onIntent(QuizIntent.NextQuestion) },
                    style = SortButtonStyle.Outlined,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (state.showSummary) {
                SortSectionCard(title = "Summary") {
                    SortText(text = "Mode: ${state.mode}")
                    SortText(text = "Difficulty: ${state.difficulty}")
                    SortText(text = "Score: ${state.score}")
                    SortText(text = "Correct: ${state.correctCount}/${state.totalQuestions}")
                    SortText(text = "Longest streak: ${state.longestStreak}")

                    if (state.scoreHistory.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(SpacingTokens.M))

                        val bestScore = state.scoreHistory.maxOf { it.score }
                        val averageScore = state.scoreHistory.map { it.score }.average().toInt()
                        val improvement = if (state.scoreHistory.size > 1) {
                            state.score - state.scoreHistory[1].score
                        } else {
                            0
                        }

                        SortText(
                            text = if (state.score >= bestScore) "🏆 New Best Score!" else "Best: $bestScore",
                        )
                        SortText(text = "Average: $averageScore")
                        if (improvement != 0) {
                            SortText(
                                text = "vs Previous: ${if (improvement > 0) "+$improvement" else "$improvement"}",
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(SpacingTokens.M))
                    SortButton(
                        text = "Play Again",
                        onClick = { onIntent(QuizIntent.StartQuiz) },
                        style = SortButtonStyle.Primary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            SortSectionCard(title = "Score Trend") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    ScorePeriod.entries.forEach { period ->
                        SortButton(
                            text = when (period) {
                                ScorePeriod.DAILY -> "Day"
                                ScorePeriod.WEEKLY -> "Week"
                                ScorePeriod.ALL -> "All"
                            },
                            onClick = { onIntent(QuizIntent.SelectScorePeriod(period)) },
                            style = if (state.scorePeriod == period) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(SpacingTokens.S))

                if (state.scoreHistory.isNotEmpty()) {
                    val bestScore = state.scoreHistory.maxOf { it.score }
                    val averageScore = state.scoreHistory.map { it.score }.average().toInt()
                    val latestScore = state.scoreHistory.first().score

                    ScoreTrendChart(
                        scores = state.scoreHistory,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                    )

                    Spacer(modifier = Modifier.height(SpacingTokens.S))

                    SortText(text = "Latest: $latestScore")
                    SortText(text = "Best: $bestScore")
                    SortText(text = "Average: $averageScore")
                } else {
                    SortText(text = "No scores in this period")
                }
            }

            if (state.leaderboard.isNotEmpty()) {
                SortSectionCard(title = "Leaderboard") {
                    state.leaderboard.takeLast(5).reversed().forEach { entry ->
                        SortText(text = "${entry.mode} - ${entry.difficulty}")
                        SortText(text = "Score: ${entry.score}")
                        SortText(text = "Correct: ${entry.correctCount}/${entry.totalQuestions}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
        }
    }
}

@Composable
private fun ScoreTrendChart(
    scores: List<QuizScoreEntry>,
    modifier: Modifier = Modifier,
) {
    val sortedScores = scores.sortedBy { it.createdAtMillis }.takeLast(10)
    if (sortedScores.size < 2) {
        SortText(text = "Not enough data for trend")
        return
    }

    Canvas(modifier = modifier) {
        val maxScore = sortedScores.maxOf { it.score }.coerceAtLeast(1)
        val minScore = sortedScores.minOf { it.score }
        val xStep = size.width / (sortedScores.size - 1).coerceAtLeast(1)
        val scoreRange = (maxScore - minScore).coerceAtLeast(1)

        val path = Path()
        sortedScores.forEachIndexed { index, entry ->
            val x = xStep * index
            val normalized = (entry.score - minScore).toFloat() / scoreRange.toFloat()
            val y = size.height - (size.height * normalized)
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
            drawCircle(
                color = androidx.compose.ui.graphics.Color(0xFF7F52FF),
                radius = 4f,
                center = Offset(x, y),
            )
        }

        drawPath(
            path = path,
            color = androidx.compose.ui.graphics.Color(0xFF7F52FF),
            style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round),
        )
    }
}
