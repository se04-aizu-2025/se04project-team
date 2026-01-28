package dotnet.sort.presentation.feature.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortIcon
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortBottomSheet
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.ColorTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.QuizScoreEntry
import dotnet.sort.model.ScorePeriod
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

/**
 * Quiz 画面。
 * 
 * 状態に応じて適切なコンテンツを表示:
 * - SetupContent: クイズ開始前（設定・履歴）
 * - PlayingContent: クイズ中（問題・選択肢）
 * - ResultContent: 回答後（正誤表示）
 * - SummaryContent: クイズ終了後（サマリー）
 */
@OptIn(ExperimentalMaterial3Api::class)
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
    var showSettingsSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.quiz_title),
                onBackClick = onBackClick,
                endAction = {
                    if (!state.isRunning) {
                        SortIconButton(
                            icon = SortIcons.Settings,
                            contentDescription = stringResource(Res.string.quiz_settings),
                            onClick = { showSettingsSheet = true }
                        )
                    }
                }
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
                        selected = true,
                        onClick = {},
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
        // Determine which content to show based on state
        // Note: We check isRunning BEFORE isCorrect to avoid brief flicker during transition
        // When moving to next question, isRunning becomes true before isCorrect is cleared
        val contentType = when {
            state.showSummary -> QuizContentType.SUMMARY
            state.isRunning && state.isCorrect == null && state.question != null -> QuizContentType.PLAYING
            state.isCorrect != null -> QuizContentType.RESULT
            else -> QuizContentType.SETUP
        }

        AnimatedContent(
            targetState = contentType,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "QuizContentTransition"
        ) { type ->
            when (type) {
                QuizContentType.SETUP -> QuizSetupContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                        .padding(SpacingTokens.M),
                )
                QuizContentType.PLAYING -> QuizPlayingContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(SpacingTokens.M),
                )
                QuizContentType.RESULT -> QuizResultContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(SpacingTokens.M),
                )
                QuizContentType.SUMMARY -> QuizSummaryContent(
                    state = state,
                    onIntent = onIntent,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                        .padding(SpacingTokens.M),
                )
            }
        }

        if (showSettingsSheet) {
            SortBottomSheet(
                onDismissRequest = { showSettingsSheet = false },
                sheetState = sheetState,
            ) {
                QuizSettingsSheetContent(
                    state = state,
                    onIntent = onIntent,
                    onDismiss = { showSettingsSheet = false },
                )
            }
        }
    }
}

private enum class QuizContentType {
    SETUP, PLAYING, RESULT, SUMMARY
}

/**
 * クイズ開始前のセットアップ画面
 */
@Composable
private fun QuizSetupContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(SpacingTokens.FloatingTopBarInset))

        // Welcome section
        SortText(
            text = stringResource(Res.string.quiz_title),
            style = SortTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Current settings display
        SortSectionCard(title = stringResource(Res.string.quiz_settings)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_mode_label))
                SortText(text = stringResource(state.mode.toLabel()), fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(SpacingTokens.S))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_difficulty_label))
                SortText(text = stringResource(state.difficulty.toLabel()), fontWeight = FontWeight.Medium)
            }
        }

        // Start button (prominent)
        SortButton(
            text = stringResource(Res.string.quiz_start),
            onClick = { onIntent(QuizIntent.StartQuiz) },
            style = SortButtonStyle.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(SpacingTokens.XXL),
        )

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Score history section
        SortSectionCard(title = stringResource(Res.string.quiz_score_trend)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
            ) {
                ScorePeriod.entries.forEach { period ->
                    SortButton(
                        text = stringResource(
                            when (period) {
                                ScorePeriod.DAILY -> Res.string.quiz_period_daily
                                ScorePeriod.WEEKLY -> Res.string.quiz_period_weekly
                                ScorePeriod.ALL -> Res.string.quiz_period_all
                            }
                        ),
                        onClick = { onIntent(QuizIntent.SelectScorePeriod(period)) },
                        style = if (state.scorePeriod == period) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            if (state.scoreHistory.isNotEmpty()) {
                ScoreTrendChart(
                    scores = state.scoreHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SpacingTokens.ChartHeightSmall),
                )

                Spacer(modifier = Modifier.height(SpacingTokens.S))

                val bestScore = state.scoreHistory.maxOf { it.score }
                val averageScore = state.scoreHistory.map { it.score }.average().toInt()
                val latestScore = state.scoreHistory.first().score

                SortText(text = stringResource(Res.string.quiz_latest_format, latestScore))
                SortText(text = stringResource(Res.string.quiz_best_format, bestScore))
                SortText(text = stringResource(Res.string.quiz_average_format, averageScore))
            } else {
                SortText(text = stringResource(Res.string.quiz_no_scores))
            }
        }

        Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
    }
}

/**
 * クイズ実行中の画面 - 問題と選択肢のみに集中
 */
@Composable
private fun QuizPlayingContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier.height(SpacingTokens.FloatingTopBarInset))

        // Progress and timer header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Timer display (large and prominent)
            SortText(
                text = "${(state.timeRemainingMs / 1000)}s",
                style = SortTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = if (state.timeRemainingMs < 3000) ColorTokens.Error else SortTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(SpacingTokens.S))

            // Progress indicator
            SortText(
                text = stringResource(Res.string.quiz_question_progress_format, state.questionIndex + 1, state.totalQuestions),
                style = SortTheme.typography.titleMedium,
            )

            // Score and streak
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                SortText(
                    text = stringResource(Res.string.quiz_score_format, state.score),
                    style = SortTheme.typography.bodyMedium,
                )
                SortText(
                    text = "  •  ",
                    style = SortTheme.typography.bodyMedium,
                )
                SortText(
                    text = stringResource(Res.string.quiz_streak_format, state.streak),
                    style = SortTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        // Question
        state.question?.let { question ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                SortText(
                    text = stringResource(question.prompt),
                    style = SortTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Hint button and display
                if (state.mode == QuizMode.GUESS_ALGORITHM) {
                    val hint = question.hint
                    if (hint != null) {
                        Spacer(modifier = Modifier.height(SpacingTokens.M))
                        SortButton(
                            text = stringResource(if (state.showHint) Res.string.quiz_hint_hide else Res.string.quiz_hint_show),
                            onClick = { onIntent(QuizIntent.ToggleHint) },
                            style = SortButtonStyle.Text,
                        )
                        if (state.showHint) {
                            Spacer(modifier = Modifier.height(SpacingTokens.S))
                            SortText(
                                text = stringResource(Res.string.quiz_hint_prefix) + stringResource(hint),
                                style = SortTheme.typography.bodyMedium,
                                color = SortTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }

        // Options - large touch targets
        Column(
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.S),
        ) {
            state.question?.options?.forEachIndexed { index, option ->
                SortButton(
                    text = stringResource(option),
                    onClick = { onIntent(QuizIntent.SelectOption(index)) },
                    style = if (state.selectedIndex == index) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SpacingTokens.XXL),
                )
            }
        }

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Submit button
        SortButton(
            text = stringResource(Res.string.quiz_submit),
            onClick = { onIntent(QuizIntent.SubmitAnswer) },
            style = SortButtonStyle.Primary,
            enabled = state.selectedIndex != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(SpacingTokens.XXL),
        )

        Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
    }
}

/**
 * 回答結果表示画面
 */
@Composable
private fun QuizResultContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Result icon (large and clear)
        val isCorrect = state.isCorrect ?: false
        
        SortIcon(
            imageVector = if (isCorrect) SortIcons.Check else SortIcons.Close,
            contentDescription = if (isCorrect) "Correct" else "Incorrect",
            tint = if (isCorrect) ColorTokens.Success else ColorTokens.Error,
            modifier = Modifier.size(SpacingTokens.IconSizeXL),
        )

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        SortText(
            text = stringResource(if (isCorrect) Res.string.quiz_correct else Res.string.quiz_incorrect),
            style = SortTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = if (isCorrect) ColorTokens.Success else ColorTokens.Error,
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        // Current score
        SortText(
            text = stringResource(Res.string.quiz_score_format, state.score),
            style = SortTheme.typography.titleLarge,
        )

        if (state.streak > 1) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.XS),
            ) {
                SortIcon(
                    imageVector = SortIcons.Fire,
                    contentDescription = "Streak",
                    tint = ColorTokens.Warning,
                    modifier = Modifier.size(SpacingTokens.IconSizeM),
                )
                SortText(
                    text = stringResource(Res.string.quiz_streak_format, state.streak),
                    style = SortTheme.typography.titleMedium,
                    color = ColorTokens.Warning,
                )
            }
        }

        Spacer(modifier = Modifier.height(SpacingTokens.XL))

        // Next button
        SortButton(
            text = stringResource(Res.string.quiz_next_question),
            onClick = { onIntent(QuizIntent.NextQuestion) },
            style = SortButtonStyle.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(SpacingTokens.XXL),
        )
    }
}

/**
 * クイズ終了後のサマリー画面
 */
@Composable
private fun QuizSummaryContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(SpacingTokens.FloatingTopBarInset))

        SortText(
            text = stringResource(Res.string.quiz_summary),
            style = SortTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Score display (large)
        SortText(
            text = "${state.score}",
            style = SortTheme.typography.displayLarge,
            color = SortTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        SortText(
            text = stringResource(Res.string.quiz_points),
            style = SortTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        SortSectionCard(title = stringResource(Res.string.quiz_result)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_mode_label))
                SortText(text = stringResource(state.mode.toLabel()))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_difficulty_label))
                SortText(text = stringResource(state.difficulty.toLabel()))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_correct_label))
                SortText(text = "${state.correctCount}/${state.totalQuestions}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SortText(text = stringResource(Res.string.quiz_longest_streak_label))
                SortText(text = "${state.longestStreak}")
            }
        }

        // Comparison with history
        if (state.scoreHistory.isNotEmpty()) {
            val bestScore = state.scoreHistory.maxOf { it.score }
            val averageScore = state.scoreHistory.map { it.score }.average().toInt()

            SortSectionCard(title = stringResource(Res.string.quiz_comparison)) {
                if (state.score >= bestScore) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SpacingTokens.XS),
                    ) {
                        SortIcon(
                            imageVector = SortIcons.Trophy,
                            contentDescription = null,
                            tint = ColorTokens.Warning,
                            modifier = Modifier.size(SpacingTokens.IconSizeM),
                        )
                        SortText(
                            text = stringResource(Res.string.quiz_new_best_score),
                            style = SortTheme.typography.titleMedium,
                            color = ColorTokens.Warning,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        SortText(text = stringResource(Res.string.quiz_best_score))
                        SortText(text = "$bestScore")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SortText(text = stringResource(Res.string.quiz_average_score))
                    SortText(text = "$averageScore")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Play again button
        SortButton(
            text = stringResource(Res.string.quiz_play_again),
            onClick = { onIntent(QuizIntent.StartQuiz) },
            style = SortButtonStyle.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(SpacingTokens.XXL),
        )

        Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
    }
}

/**
 * 設定シートのコンテンツ
 */
@Composable
private fun QuizSettingsSheetContent(
    state: QuizState,
    onIntent: (QuizIntent) -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SortText(
                text = stringResource(Res.string.quiz_settings),
                style = SortTheme.typography.titleLarge,
            )
            SortButton(
                text = stringResource(Res.string.quiz_done),
                onClick = onDismiss,
                style = SortButtonStyle.Text,
            )
        }

        SortSectionCard(title = stringResource(Res.string.quiz_mode_label)) {
            Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                QuizMode.entries.forEach { mode ->
                    SortButton(
                        text = stringResource(mode.toLabel()),
                        onClick = { onIntent(QuizIntent.SelectMode(mode)) },
                        style = if (state.mode == mode) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        SortSectionCard(title = stringResource(Res.string.quiz_difficulty_label)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
            ) {
                QuizDifficulty.entries.forEach { difficulty ->
                    SortButton(
                        text = stringResource(difficulty.toLabel()),
                        onClick = { onIntent(QuizIntent.SelectDifficulty(difficulty)) },
                        style = if (state.difficulty == difficulty) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(SpacingTokens.XL))
    }
}

@Composable
private fun ScoreTrendChart(
    scores: List<QuizScoreEntry>,
    modifier: Modifier = Modifier,
) {
    val sortedScores = scores.sortedBy { it.createdAtMillis }.takeLast(10)
    if (sortedScores.size < 2) {
        SortText(text = stringResource(Res.string.quiz_not_enough_data))
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
                color = ColorTokens.KotlinPurple,
                radius = 6f,
                center = Offset(x, y),
            )
        }

        drawPath(
            path = path,
            color = ColorTokens.KotlinPurple,
            style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round),
        )
    }
}

private fun QuizMode.toLabel(): org.jetbrains.compose.resources.StringResource = when(this) {
    QuizMode.SPEED_SWAP -> Res.string.quiz_mode_speed_swap
    QuizMode.GUESS_ALGORITHM -> Res.string.quiz_mode_guess_algo
}

private fun QuizDifficulty.toLabel(): org.jetbrains.compose.resources.StringResource = when(this) {
    QuizDifficulty.EASY -> Res.string.quiz_diff_easy
    QuizDifficulty.MEDIUM -> Res.string.quiz_diff_medium
    QuizDifficulty.HARD -> Res.string.quiz_diff_hard
}
