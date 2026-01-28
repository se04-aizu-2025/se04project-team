package dotnet.sort.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortProgressIndicator
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortCard
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.ColorTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.app_name
import dotnet.sort.designsystem.generated.resources.dashboard_algo_proficiency
import dotnet.sort.designsystem.generated.resources.dashboard_attempts
import dotnet.sort.designsystem.generated.resources.dashboard_avg_score
import dotnet.sort.designsystem.generated.resources.dashboard_best_score
import dotnet.sort.designsystem.generated.resources.dashboard_latest_score
import dotnet.sort.designsystem.generated.resources.dashboard_loading
import dotnet.sort.designsystem.generated.resources.dashboard_quiz_summary
import dotnet.sort.designsystem.generated.resources.dashboard_title
import dotnet.sort.designsystem.generated.resources.dashboard_total_sessions
import dotnet.sort.designsystem.generated.resources.dashboard_total_time
import dotnet.sort.designsystem.generated.resources.home_card_compare_desc
import dotnet.sort.designsystem.generated.resources.home_card_compare_title
import dotnet.sort.designsystem.generated.resources.home_card_learn_desc
import dotnet.sort.designsystem.generated.resources.home_card_learn_title
import dotnet.sort.designsystem.generated.resources.home_card_quiz_desc
import dotnet.sort.designsystem.generated.resources.home_card_quiz_title
import dotnet.sort.designsystem.generated.resources.home_card_settings_desc
import dotnet.sort.designsystem.generated.resources.home_card_settings_title
import dotnet.sort.designsystem.generated.resources.home_card_visualizer_desc
import dotnet.sort.designsystem.generated.resources.home_card_visualizer_title
import dotnet.sort.designsystem.generated.resources.home_subtitle
import dotnet.sort.designsystem.generated.resources.home_title
import dotnet.sort.designsystem.generated.resources.nav_compare
import dotnet.sort.designsystem.generated.resources.nav_home
import dotnet.sort.designsystem.generated.resources.nav_learn
import dotnet.sort.designsystem.generated.resources.nav_quiz
import dotnet.sort.designsystem.generated.resources.nav_settings
import dotnet.sort.designsystem.generated.resources.nav_sort
import dotnet.sort.designsystem.generated.resources.prof_beginner
import dotnet.sort.designsystem.generated.resources.prof_expert
import dotnet.sort.designsystem.generated.resources.prof_intermediate
import dotnet.sort.designsystem.generated.resources.prof_none
import dotnet.sort.model.SortType
import dotnet.sort.usecase.LearningStatistics
import dotnet.sort.usecase.ProficiencyLevel
import dotnet.sort.usecase.QuizScoreSummary
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds

/**
 * ホーム画面のオプションデータ。
 */
private data class HomeOption(
    val title: StringResource,
    val description: StringResource,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)

/**
 * ホーム画面。
 *
 * @param isHomeSelected Home選択状態
 * @param isSortSelected Sort選択状態
 * @param isLearnSelected Learn選択状態
 * @param isCompareSelected Compare選択状態
 * @param isSettingsSelected Settings選択状態
 * @param state ホーム画面の状態
 * @param onNavigateToHome Home画面への遷移コールバック
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param modifier Modifier
 */
@Composable
fun HomeScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isSettingsSelected: Boolean,
    state: HomeState,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val options =
        listOf(
            HomeOption(
                title = Res.string.home_card_visualizer_title,
                description = Res.string.home_card_visualizer_desc,
                icon = SortIcons.Visualizer,
                onClick = onNavigateToSort,
            ),
            HomeOption(
                title = Res.string.home_card_learn_title,
                description = Res.string.home_card_learn_desc,
                icon = SortIcons.Learn,
                onClick = onNavigateToLearn,
            ),
            HomeOption(
                title = Res.string.home_card_compare_title,
                description = Res.string.home_card_compare_desc,
                icon = SortIcons.Compare,
                onClick = onNavigateToCompare,
            ),
            HomeOption(
                title = Res.string.home_card_quiz_title,
                description = Res.string.home_card_quiz_desc,
                icon = SortIcons.Quiz,
                onClick = onNavigateToQuiz,
            ),
            HomeOption(
                title = Res.string.home_card_settings_title,
                description = Res.string.home_card_settings_desc,
                icon = SortIcons.Settings,
                onClick = onNavigateToSettings,
            ),
        )

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.home_title),
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
                            icon = SortIcons.Quiz,
                            contentDescription = stringResource(Res.string.nav_quiz),
                            selected = false,
                            onClick = onNavigateToQuiz,
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
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = SpacingTokens.GridCellMinWidth),
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
                        text = stringResource(Res.string.app_name),
                        style = SortTheme.typography.displayMedium,
                        color = SortTheme.colorScheme.primary,
                    )

                    SortText(
                        text = stringResource(Res.string.home_subtitle),
                        style = SortTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = SpacingTokens.L),
                    )
                }
            }

            // 学習進捗ダッシュボード
            item(span = { GridItemSpan(maxLineSpan) }) {
                LearningProgressDashboard(
                    statistics = state.learningStatistics,
                    quizSummary = state.quizSummary,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            items(options) { option ->
                SortCard(
                    title = stringResource(option.title),
                    description = stringResource(option.description),
                    icon = option.icon,
                    onClick = option.onClick,
                    enabled = option.enabled,
                )
            }
        }
    }
}

/**
 * 学習進捗ダッシュボード。
 */
@Composable
private fun LearningProgressDashboard(
    statistics: LearningStatistics?,
    quizSummary: QuizScoreSummary?,
    modifier: Modifier = Modifier,
) {
    SortSectionCard(
        title = stringResource(Res.string.dashboard_title),
        modifier = modifier.padding(vertical = SpacingTokens.M),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        ) {
            if (statistics == null && quizSummary == null) {
                SortText(
                    text = stringResource(Res.string.dashboard_loading),
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (statistics != null) {
                // 総学習時間
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(
                        text = stringResource(Res.string.dashboard_total_time),
                        style = SortTheme.typography.bodyLarge,
                    )
                    SortText(
                        text = formatDuration(statistics.totalLearningTimeMs),
                        style = SortTheme.typography.bodyLarge,
                        color = SortTheme.colorScheme.primary,
                    )
                }

                // 総セッション数
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(
                        text = stringResource(Res.string.dashboard_total_sessions),
                        style = SortTheme.typography.bodyLarge,
                    )
                    SortText(
                        text = "${statistics.totalSessions}",
                        style = SortTheme.typography.bodyLarge,
                        color = SortTheme.colorScheme.primary,
                    )
                }

                Spacer(modifier = Modifier.height(SpacingTokens.M))

                // アルゴリズム別習熟度
                SortText(
                    text = stringResource(Res.string.dashboard_algo_proficiency),
                    style = SortTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = SpacingTokens.S),
                )

                SortType.entries.forEach { algorithm ->
                    val proficiency = statistics.algorithmProficiency[algorithm] ?: ProficiencyLevel.NONE
                    AlgorithmProficiencyRow(
                        algorithm = algorithm,
                        proficiency = proficiency,
                    )
                }
            }

            if (quizSummary != null) {
                Spacer(modifier = Modifier.height(SpacingTokens.M))

                SortText(
                    text = stringResource(Res.string.dashboard_quiz_summary),
                    style = SortTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = SpacingTokens.S),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = stringResource(Res.string.dashboard_attempts), style = SortTheme.typography.bodyLarge)
                    SortText(text = "${quizSummary.totalAttempts}", style = SortTheme.typography.bodyLarge)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = stringResource(Res.string.dashboard_best_score), style = SortTheme.typography.bodyLarge)
                    SortText(
                        text = "${quizSummary.bestScore}",
                        style = SortTheme.typography.bodyLarge,
                        color = SortTheme.colorScheme.primary,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = stringResource(Res.string.dashboard_avg_score), style = SortTheme.typography.bodyLarge)
                    SortText(text = "${quizSummary.averageScore}", style = SortTheme.typography.bodyLarge)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = stringResource(Res.string.dashboard_latest_score), style = SortTheme.typography.bodyLarge)
                    SortText(text = "${quizSummary.latestScore}", style = SortTheme.typography.bodyLarge)
                }
            }
        }
    }
}

/**
 * アルゴリズム習熟度行。
 */
@Composable
private fun AlgorithmProficiencyRow(
    algorithm: SortType,
    proficiency: ProficiencyLevel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SortText(
            text = algorithm.displayName,
            style = SortTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SortProgressIndicator(
                progress = proficiency.progress,
                modifier = Modifier.weight(0.5f),
                color = proficiency.color,
            )

            SortText(
                text = stringResource(proficiency.displayName),
                style = SortTheme.typography.bodySmall,
                color = proficiency.color,
            )
        }
    }
}

/**
 * 時間をフォーマットする。
 */
private fun formatDuration(milliseconds: Long): String {
    val duration = milliseconds.milliseconds
    val minutes = duration.inWholeMinutes
    val seconds = duration.inWholeSeconds % 60

    return when {
        minutes > 0 -> "${minutes}m ${seconds}s"
        else -> "${seconds}s"
    }
}

/**
 * ProficiencyLevelの拡張プロパティ。
 */
private val ProficiencyLevel.progress: Float
    get() = when (this) {
        ProficiencyLevel.NONE -> 0f
        ProficiencyLevel.BEGINNER -> 0.33f
        ProficiencyLevel.INTERMEDIATE -> 0.66f
        ProficiencyLevel.EXPERT -> 1f
    }

private val ProficiencyLevel.displayName: StringResource
    get() = when (this) {
        ProficiencyLevel.NONE -> Res.string.prof_none
        ProficiencyLevel.BEGINNER -> Res.string.prof_beginner
        ProficiencyLevel.INTERMEDIATE -> Res.string.prof_intermediate
        ProficiencyLevel.EXPERT -> Res.string.prof_expert
    }

private val ProficiencyLevel.color: Color
    @Composable get() = when (this) {
        ProficiencyLevel.NONE -> SortTheme.colorScheme.onSurfaceVariant
        ProficiencyLevel.BEGINNER -> ColorTokens.ProficiencyBeginner
        ProficiencyLevel.INTERMEDIATE -> ColorTokens.ProficiencyIntermediate
        ProficiencyLevel.EXPERT -> ColorTokens.ProficiencyExpert
    }
