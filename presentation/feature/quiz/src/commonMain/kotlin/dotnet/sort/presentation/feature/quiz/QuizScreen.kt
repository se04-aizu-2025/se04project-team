package dotnet.sort.presentation.feature.quiz

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
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.presentation.feature.quiz.toDisplayName
import org.jetbrains.compose.resources.stringResource
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
                title = stringResource(Res.string.nav_quiz),
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            SortBottomBar(
                items =
                    listOf(
                        SortBottomBarItem(
                            icon = SortIcons.Home,
                            contentDescription = stringResource(Res.string.nav_home),
                            selected = isHomeSelected,
                            onClick = onNavigateToHome,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Sort,
                            contentDescription = stringResource(Res.string.nav_sort),
                            selected = isSortSelected,
                            onClick = onNavigateToSort,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Learn,
                            contentDescription = stringResource(Res.string.nav_learn),
                            selected = isLearnSelected,
                            onClick = onNavigateToLearn,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Compare,
                            contentDescription = stringResource(Res.string.nav_compare),
                            selected = isCompareSelected,
                            onClick = onNavigateToCompare,
                        ),
                        SortBottomBarItem(
                            icon = SortIcons.Settings,
                            contentDescription = stringResource(Res.string.nav_settings),
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
            SortSectionCard(title = stringResource(Res.string.quiz_mode_label)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    QuizMode.entries.forEach { mode ->
                        SortButton(
                            text = stringResource(mode.toDisplayName()),
                            onClick = { onIntent(QuizIntent.SelectMode(mode)) },
                            style = if (state.mode == mode) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                            modifier = Modifier.weight(1f),
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
                            text = stringResource(difficulty.toDisplayName()),
                            onClick = { onIntent(QuizIntent.SelectDifficulty(difficulty)) },
                            style = if (state.difficulty == difficulty) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            SortSectionCard(title = stringResource(Res.string.quiz_status_label)) {
                SortText(text = stringResource(Res.string.quiz_time_format, state.timeRemainingMs / 1000.0))
                SortText(text = stringResource(Res.string.quiz_question_progress_format, state.questionIndex + 1, state.totalQuestions))
            }

            SortSectionCard(title = stringResource(Res.string.quiz_score_section)) {
                SortText(text = stringResource(Res.string.quiz_score_format, state.score))
                SortText(text = stringResource(Res.string.quiz_streak_format, state.streak))
                SortText(text = stringResource(Res.string.quiz_correct_format, state.correctCount, state.totalQuestions))
                SortText(text = stringResource(Res.string.quiz_longest_streak_format, state.longestStreak))
            }

            SortSectionCard(title = stringResource(Res.string.quiz_question_section)) {
                SortText(text = stringResource(state.question.prompt))
                if (state.mode == QuizMode.GUESS_ALGORITHM && state.question.hint != null) {
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                    SortButton(
                        text = if (state.showHint) stringResource(Res.string.quiz_hint_hide) else stringResource(Res.string.quiz_hint_show),
                        onClick = { onIntent(QuizIntent.ToggleHint) },
                        style = SortButtonStyle.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (state.showHint) {
                        Spacer(modifier = Modifier.height(SpacingTokens.S))
                        SortText(text = "${stringResource(Res.string.quiz_hint_prefix)}${state.question.hint?.let { stringResource(it) }}")
                    }
                }
            }

            SortSectionCard(title = stringResource(Res.string.quiz_options_section)) {
                state.question.options.forEachIndexed { index, option ->
                    SortButton(
                        text = stringResource(option),
                        onClick = { onIntent(QuizIntent.SelectOption(index)) },
                        style = if (state.selectedIndex == index) SortButtonStyle.Primary else SortButtonStyle.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                }
            }

            SortButton(
                text = stringResource(Res.string.quiz_action_start),
                onClick = { onIntent(QuizIntent.StartQuiz) },
                style = SortButtonStyle.Primary,
                modifier = Modifier.fillMaxWidth(),
            )

            SortButton(
                text = stringResource(Res.string.quiz_action_submit),
                onClick = { onIntent(QuizIntent.SubmitAnswer) },
                style = SortButtonStyle.Primary,
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.isCorrect != null) {
                SortSectionCard(title = stringResource(Res.string.quiz_result_label)) {
                    SortText(text = if (state.isCorrect) stringResource(Res.string.quiz_result_correct) else stringResource(Res.string.quiz_result_incorrect))
                }
                SortButton(
                    text = stringResource(Res.string.quiz_action_next),
                    onClick = { onIntent(QuizIntent.NextQuestion) },
                    style = SortButtonStyle.Outlined,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (state.showSummary) {
                SortSectionCard(title = stringResource(Res.string.quiz_summary_label)) {
                    SortText(text = "${stringResource(Res.string.quiz_mode_label)}: ${stringResource(state.mode.toDisplayName())}")
                    SortText(text = "${stringResource(Res.string.quiz_difficulty_label)}: ${stringResource(state.difficulty.toDisplayName())}")
                    SortText(text = stringResource(Res.string.quiz_score_format, state.score))
                    SortText(text = stringResource(Res.string.quiz_correct_format, state.correctCount, state.totalQuestions))
                    SortText(text = stringResource(Res.string.quiz_longest_streak_format, state.longestStreak))
                    SortButton(text = stringResource(Res.string.quiz_action_play_again), onClick = { onIntent(QuizIntent.StartQuiz) })
                }
            }

            if (state.leaderboard.isNotEmpty()) {
                SortSectionCard(title = stringResource(Res.string.quiz_leaderboard_label)) {
                    state.leaderboard.takeLast(5).reversed().forEach { entry ->
                        SortText(text = "${stringResource(entry.mode.toDisplayName())} - ${stringResource(entry.difficulty.toDisplayName())}")
                        SortText(text = stringResource(Res.string.quiz_score_format, entry.score))
                        SortText(text = stringResource(Res.string.quiz_correct_format, entry.correctCount, entry.totalQuestions))
                    }
                }
            }

            Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
        }
    }
}
