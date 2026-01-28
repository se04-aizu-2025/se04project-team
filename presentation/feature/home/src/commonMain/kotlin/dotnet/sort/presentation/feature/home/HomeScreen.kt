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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortIcons
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
import dotnet.sort.model.SortType
import dotnet.sort.usecase.LearningStatistics
import dotnet.sort.usecase.ProficiencyLevel
import dotnet.sort.usecase.QuizScoreSummary
import kotlin.time.Duration.Companion.milliseconds

/**
 * ホーム画面のオプションデータ。
 */
private data class HomeOption(
    val title: String,
    val description: String,
    val icon: String,
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
                title = "Visualizer",
                description = "Visualize sorting algorithms in real-time.",
                icon = "📊",
                onClick = onNavigateToSort,
            ),
            HomeOption(
                title = "Learn",
                description = "Learn about different sorting algorithms.",
                icon = "🎓",
                onClick = onNavigateToLearn,
            ),
            HomeOption(
                title = "Compare",
                description = "Compare performance of algorithms.",
                icon = "⚖️",
                onClick = onNavigateToCompare,
            ),
            HomeOption(
                title = "Quiz",
                description = "Test your algorithm knowledge.",
                icon = "🧠",
                onClick = onNavigateToQuiz,
            ),
            HomeOption(
                title = "Settings",
                description = "App settings and themes.",
                icon = "⚙️",
                onClick = onNavigateToSettings,
            ),
        )

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = "Home",
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
                        text = "DNSort",
                        style = SortTheme.typography.displayMedium,
                        color = SortTheme.colorScheme.primary,
                    )

                    SortText(
                        text = "Algorithm Visualization Tool",
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
                    title = option.title,
                    description = option.description,
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
        title = "📊 Learning Progress",
        modifier = modifier.padding(vertical = SpacingTokens.M),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        ) {
            if (statistics == null && quizSummary == null) {
                SortText(
                    text = "Loading learning statistics...",
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
                        text = "Total Learning Time",
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
                        text = "Total Sessions",
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
                    text = "Algorithm Proficiency",
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
                    text = "Quiz Summary",
                    style = SortTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = SpacingTokens.S),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = "Attempts", style = SortTheme.typography.bodyLarge)
                    SortText(text = "${quizSummary.totalAttempts}", style = SortTheme.typography.bodyLarge)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = "Best Score", style = SortTheme.typography.bodyLarge)
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
                    SortText(text = "Average Score", style = SortTheme.typography.bodyLarge)
                    SortText(text = "${quizSummary.averageScore}", style = SortTheme.typography.bodyLarge)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortText(text = "Latest", style = SortTheme.typography.bodyLarge)
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
            LinearProgressIndicator(
                progress = proficiency.progress,
                modifier = Modifier
                    .weight(0.5f)
                    .height(SpacingTokens.ProgressBarHeight),
                color = proficiency.color,
            )

            SortText(
                text = proficiency.displayName,
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

private val ProficiencyLevel.displayName: String
    get() = when (this) {
        ProficiencyLevel.NONE -> "None"
        ProficiencyLevel.BEGINNER -> "Beginner"
        ProficiencyLevel.INTERMEDIATE -> "Intermediate"
        ProficiencyLevel.EXPERT -> "Expert"
    }

private val ProficiencyLevel.color: androidx.compose.ui.graphics.Color
    @Composable get() = when (this) {
        ProficiencyLevel.NONE -> SortTheme.colorScheme.onSurfaceVariant
        ProficiencyLevel.BEGINNER -> ColorTokens.ProficiencyBeginner
        ProficiencyLevel.INTERMEDIATE -> ColorTokens.ProficiencyIntermediate
        ProficiencyLevel.EXPERT -> ColorTokens.ProficiencyExpert
    }
