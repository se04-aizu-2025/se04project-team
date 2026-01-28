package dotnet.sort.presentation.feature.compare

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
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.sort.components.SortVisualizer

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
    state: CompareState,
    onIntent: (CompareIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = "Compare",
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
        CompareContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.fillMaxSize().padding(padding),
        )
    }
}

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
        SortSectionCard(title = "Algorithms") {
            Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                SortDropdown(
                    label = "Left",
                    selectedItem = state.leftAlgorithm,
                    items = SortType.entries.toList(),
                    onItemSelected = { onIntent(CompareIntent.SelectLeftAlgorithm(it)) },
                    itemLabel = { it.displayName },
                )
                SortDropdown(
                    label = "Right",
                    selectedItem = state.rightAlgorithm,
                    items = SortType.entries.toList(),
                    onItemSelected = { onIntent(CompareIntent.SelectRightAlgorithm(it)) },
                    itemLabel = { it.displayName },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
                ) {
                    SortButton(
                        text = "Swap",
                        onClick = { onIntent(CompareIntent.SwapAlgorithms) },
                        style = SortButtonStyle.Outlined,
                        modifier = Modifier.weight(1f),
                    )
                    SortButton(
                        text = if (state.isRunning) "Running" else "Start",
                        onClick = { onIntent(CompareIntent.StartComparison) },
                        style = SortButtonStyle.Primary,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        SortSectionCard(title = "Input pattern") {
            SortDropdown(
                label = "Pattern",
                selectedItem = state.generatorType,
                items = ArrayGeneratorType.entries.toList(),
                onItemSelected = { onIntent(CompareIntent.SelectInputPattern(it)) },
                itemLabel = { it.name.replace('_', ' ').lowercase().replaceFirstChar { c -> c.uppercase() } },
            )
        }

        SortSectionCard(title = "Visualization") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
            ) {
                SortVisualizer(
                    array = state.leftCurrentNumbers,
                    highlightIndices = state.leftHighlightIndices,
                    description = "${state.leftAlgorithm.displayName} (${state.leftStepIndex})",
                    modifier = Modifier.weight(1f),
                )
                SortVisualizer(
                    array = state.rightCurrentNumbers,
                    highlightIndices = state.rightHighlightIndices,
                    description = "${state.rightAlgorithm.displayName} (${state.rightStepIndex})",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        SortSectionCard(title = "Metrics Comparison") {
            val left = state.leftResult?.complexityMetrics
            val right = state.rightResult?.complexityMetrics
            
            Column(verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)) {
                MetricsRow(
                    label = "Comparisons",
                    leftValue = left?.comparisonCount,
                    rightValue = right?.comparisonCount,
                    leftName = state.leftAlgorithm.displayName,
                    rightName = state.rightAlgorithm.displayName,
                )
                MetricsRow(
                    label = "Swaps",
                    leftValue = left?.swapCount,
                    rightValue = right?.swapCount,
                    leftName = state.leftAlgorithm.displayName,
                    rightName = state.rightAlgorithm.displayName,
                )
                MetricsRow(
                    label = "Time (ns)",
                    leftValue = left?.executionTimeNs,
                    rightValue = right?.executionTimeNs,
                    leftName = state.leftAlgorithm.displayName,
                    rightName = state.rightAlgorithm.displayName,
                )
                if (left != null && right != null) {
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                    val totalComparison = left.comparisonCount + left.swapCount
                    val totalRight = right.comparisonCount + right.swapCount
                    val winner = when {
                        totalComparison < totalRight -> state.leftAlgorithm.displayName
                        totalRight < totalComparison -> state.rightAlgorithm.displayName
                        else -> "Tie"
                    }
                    SortText(
                        text = "🏆 Winner: $winner",
                        style = SortTheme.typography.titleMedium,
                    )
                }
            }
        }

        SortSectionCard(title = "Steps") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S),
            ) {
                SortButton(
                    text = "< Step",
                    onClick = { onIntent(CompareIntent.StepBackward) },
                    style = SortButtonStyle.Text,
                    modifier = Modifier.weight(1f),
                )
                SortButton(
                    text = "Step >",
                    onClick = { onIntent(CompareIntent.StepForward) },
                    style = SortButtonStyle.Text,
                    modifier = Modifier.weight(1f),
                )
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
