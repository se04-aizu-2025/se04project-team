package dotnet.sort.presentation.feature.compare

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import dotnet.sort.designsystem.components.atoms.SortIcon
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortBottomSheet
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.designsystem.utils.toDisplayName
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.sort.components.SortVisualizer
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

/**
 * Compare 画面。
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
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
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
    onNavigateToQuiz: () -> Unit,
    state: CompareState,
    onIntent: (CompareIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showSettingsSheet by remember { mutableStateOf(false) }

    if (showSettingsSheet) {
        SortBottomSheet(
            onDismissRequest = { showSettingsSheet = false },
        ) {
             Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacingTokens.M)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            ) {
                 Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                     SortText(
                        text = stringResource(Res.string.common_comparison_controls),
                        style = SortTheme.typography.titleLarge,
                        color = SortTheme.colorScheme.onSurface,
                    )
                    SortButton(
                        text = stringResource(Res.string.common_done),
                        onClick = { showSettingsSheet = false },
                        style = SortButtonStyle.Text,
                    )
                }

                SortSectionCard(title = stringResource(Res.string.compare_algorithms)) {
                    Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                        SortDropdown(
                            label = stringResource(Res.string.compare_left),
                            selectedItem = state.leftAlgorithm,
                            items = SortType.entries.toList(),
                            onItemSelected = { onIntent(CompareIntent.SelectLeftAlgorithm(it)) },
                            itemLabel = { stringResource(it.toDisplayName()) },
                        )
                        SortDropdown(
                            label = stringResource(Res.string.compare_right),
                            selectedItem = state.rightAlgorithm,
                            items = SortType.entries.toList(),
                            onItemSelected = { onIntent(CompareIntent.SelectRightAlgorithm(it)) },
                            itemLabel = { stringResource(it.toDisplayName()) },
                        )
                    }
                }

                SortSectionCard(title = stringResource(Res.string.compare_input_pattern)) {
                    SortDropdown(
                        label = stringResource(Res.string.compare_pattern),
                        selectedItem = state.generatorType,
                        items = ArrayGeneratorType.entries.toList(),
                        onItemSelected = { onIntent(CompareIntent.SelectInputPattern(it)) },
                        itemLabel = { it.name.replace('_', ' ').lowercase().replaceFirstChar { c -> c.uppercase() } },
                    )
                }

                Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
            }
        }
    }

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.nav_compare),
                onBackClick = onBackClick,
                endAction = {
                    SortIconButton(
                        onClick = { showSettingsSheet = true },
                        icon = SortIcons.Compare,
                        contentDescription = stringResource(Res.string.common_settings),
                        tint = SortTheme.colorScheme.onPrimary,
                    )
                }
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
    ) { padding ->
        CompareContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.fillMaxSize().padding(padding),
        )
    }
}

// ... CompareContent updates ...

@Composable
private fun CompareContent(
    state: CompareState,
    onIntent: (CompareIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
    ) {
        Spacer(modifier = Modifier.height(SpacingTokens.FloatingTopBarInset))

        // Visualization - each visualizer gets more height
        SortSectionCard(title = stringResource(Res.string.settings_section_visualization)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            ) {
                // Left Algorithm
                Column {
                    SortText(
                        text = stringResource(state.leftAlgorithm.toDisplayName()),
                        style = SortTheme.typography.titleMedium,
                    )
                    SortVisualizer(
                        array = state.leftCurrentNumbers,
                        highlightIndices = state.leftHighlightIndices,
                        description = "Step ${state.leftStepIndex}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(SpacingTokens.VisualizerHeightCompact),
                    )
                }

                // Right Algorithm
                Column {
                    SortText(
                        text = stringResource(state.rightAlgorithm.toDisplayName()),
                        style = SortTheme.typography.titleMedium,
                    )
                    SortVisualizer(
                        array = state.rightCurrentNumbers,
                        highlightIndices = state.rightHighlightIndices,
                        description = "Step ${state.rightStepIndex}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(SpacingTokens.VisualizerHeightCompact),
                    )
                }
            }
        }

        SortSectionCard(title = stringResource(Res.string.common_controls)) {
             Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                 Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    SortButton(
                        text = stringResource(Res.string.common_swap),
                        onClick = { onIntent(CompareIntent.SwapAlgorithms) },
                        style = SortButtonStyle.Outlined,
                        modifier = Modifier.weight(1f),
                    )
                    SortButton(
                        text = stringResource(if (state.isRunning) Res.string.common_running else Res.string.common_start),
                        onClick = { onIntent(CompareIntent.StartComparison) },
                        style = SortButtonStyle.Primary,
                        modifier = Modifier.weight(1f),
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    SortButton(
                        text = stringResource(Res.string.sort_controls_step_prev),
                        onClick = { onIntent(CompareIntent.StepBackward) },
                        style = SortButtonStyle.Text,
                        modifier = Modifier.weight(1f),
                    )
                    SortButton(
                        text = stringResource(Res.string.sort_controls_step_next),
                        onClick = { onIntent(CompareIntent.StepForward) },
                        style = SortButtonStyle.Text,
                        modifier = Modifier.weight(1f),
                    )
                }
             }
        }

        SortSectionCard(title = stringResource(Res.string.common_metrics_comparison)) {
            val left = state.leftResult?.complexityMetrics
            val right = state.rightResult?.complexityMetrics
            
            val leftName = stringResource(state.leftAlgorithm.toDisplayName())
            val rightName = stringResource(state.rightAlgorithm.toDisplayName())
            
            Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                MetricsRow(
                    label = stringResource(Res.string.sort_metrics_comparisons),
                    leftValue = left?.comparisonCount,
                    rightValue = right?.comparisonCount,
                    leftName = leftName,
                    rightName = rightName,
                )
                MetricsRow(
                    label = stringResource(Res.string.common_swaps),
                    leftValue = left?.swapCount,
                    rightValue = right?.swapCount,
                    leftName = leftName,
                    rightName = rightName,
                )
                MetricsRow(
                    label = stringResource(Res.string.compare_time_ns),
                    leftValue = left?.executionTimeNs,
                    rightValue = right?.executionTimeNs,
                    leftName = leftName,
                    rightName = rightName,
                )
                if (left != null && right != null) {
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                    val totalComparison = left.comparisonCount + left.swapCount
                    val totalRight = right.comparisonCount + right.swapCount
                    val winner = when {
                        totalComparison < totalRight -> leftName
                        totalRight < totalComparison -> rightName
                        else -> stringResource(Res.string.common_tie)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SpacingTokens.XS),
                    ) {
                        SortIcon(
                            imageVector = SortIcons.Trophy,
                            contentDescription = stringResource(Res.string.common_winner),
                            tint = SortTheme.colorScheme.primary,
                            modifier = Modifier.size(SpacingTokens.IconSizeM),
                        )
                        SortText(
                            text = stringResource(Res.string.compare_winner_format, winner),
                            style = SortTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
    }
}

@Composable
private fun MetricsRow(
    label: String,
    leftValue: Long?,
    rightValue: Long?,
    leftName: String,
    rightName: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val leftStr = leftValue?.toString() ?: "-"
        val rightStr = rightValue?.toString() ?: "-"
        val winner = when {
            leftValue == null || rightValue == null -> null
            leftValue < rightValue -> leftName
            rightValue < leftValue -> rightName
            else -> null
        }
        val leftPrefix = if (winner == leftName) "✅ " else ""
        val rightPrefix = if (winner == rightName) "✅ " else ""
        
        SortText(text = label, modifier = Modifier.weight(1f))
        SortText(text = "$leftPrefix$leftStr", modifier = Modifier.weight(1f))
        SortText(text = "$rightPrefix$rightStr", modifier = Modifier.weight(1f))
    }
}
