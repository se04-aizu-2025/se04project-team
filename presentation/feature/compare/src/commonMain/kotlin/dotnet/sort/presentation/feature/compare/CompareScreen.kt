package dotnet.sort.presentation.feature.compare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.model.ComplexityMetrics
import org.koin.compose.viewmodel.koinViewModel
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.components.atoms.SortSlider
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortIcon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Pause
import dotnet.sort.designsystem.components.organisms.SortVisualizer
import dotnet.sort.model.SortType
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            val viewModel = koinViewModel<CompareViewModel>()
            val state by viewModel.state.collectAsState()
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(SpacingTokens.M)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SortText(
                    text = "Select Algorithms to Compare",
                    style = SortTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(SpacingTokens.L))
                
                SortDropdown(
                    label = "Algorithm 1",
                    selectedItem = state.selectedAlgorithm1,
                    items = SortType.entries,
                    onItemSelected = { viewModel.send(CompareIntent.SelectAlgorithm1(it)) },
                    itemLabel = { it.displayName },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(SpacingTokens.M))
                
                SortDropdown(
                    label = "Algorithm 2",
                    selectedItem = state.selectedAlgorithm2,
                    items = SortType.entries,
                    onItemSelected = { viewModel.send(CompareIntent.SelectAlgorithm2(it)) },
                    itemLabel = { it.displayName },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(SpacingTokens.M))
                
                SortSlider(
                    label = "Array Size",
                    valueLabel = state.arraySize.toString(),
                    value = state.arraySize.toFloat(),
                    onValueChange = { viewModel.send(CompareIntent.SetArraySize(it.toInt())) },
                    valueRange = 10f..100f,
                    steps = 8, // 10, 20, 30, ..., 100
                    enabled = !state.isRunning,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(SpacingTokens.XL))
                
                SortButton(
                    text = if (state.isRunning) "Running..." else "Start Comparison",
                    onClick = { viewModel.send(CompareIntent.StartComparison) },
                    enabled = !state.isRunning,
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.algorithm1Result != null && state.algorithm2Result != null) {
                    Spacer(modifier = Modifier.height(SpacingTokens.L))

                    // Playback Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SortIconButton(
                            icon = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (state.isPlaying) "Pause" else "Play",
                            onClick = { viewModel.send(CompareIntent.TogglePlay) }
                        )
                        Spacer(modifier = Modifier.width(SpacingTokens.M))
                        SortIconButton(
                            icon = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            onClick = { viewModel.send(CompareIntent.Reset) }
                        )
                    }

                    SortSlider(
                        label = "Playback Speed",
                        value = state.playbackSpeed,
                        onValueChange = { viewModel.send(CompareIntent.SetSpeed(it)) },
                        valueRange = 0.5f..5.0f,
                        valueLabel = "${state.playbackSpeed.toString().take(3)}x",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(SpacingTokens.L))

                    // Visualization Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            SortText(
                                text = state.selectedAlgorithm1.displayName,
                                style = SortTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            val step1 = state.algorithm1Result?.steps?.getOrNull(state.currentStepIndex1)
                            SortVisualizer(
                                array = step1?.arrayState ?: emptyList(),
                                highlightIndices = step1?.highlightingIndices ?: emptyList(),
                                description = "",
                                modifier = Modifier.height(200.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            SortText(
                                text = state.selectedAlgorithm2.displayName,
                                style = SortTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            val step2 = state.algorithm2Result?.steps?.getOrNull(state.currentStepIndex2)
                            SortVisualizer(
                                array = step2?.arrayState ?: emptyList(),
                                highlightIndices = step2?.highlightingIndices ?: emptyList(),
                                description = "",
                                modifier = Modifier.height(200.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(SpacingTokens.L))

                    SortSectionCard(title = "Comparison Metrics") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            MetricsColumn(
                                title = state.selectedAlgorithm1.displayName,
                                metrics = state.algorithm1Result!!.complexityMetrics,
                                modifier = Modifier.weight(1f)
                            )
                            MetricsColumn(
                                title = state.selectedAlgorithm2.displayName,
                                metrics = state.algorithm2Result!!.complexityMetrics,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricsColumn(
    title: String,
    metrics: ComplexityMetrics,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(SpacingTokens.S),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SortText(text = title, style = SortTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        MetricRow(label = "Time", value = formatNs(metrics.executionTimeNs))
        MetricRow(label = "Compares", value = metrics.comparisonCount.toString())
        MetricRow(label = "Swaps", value = metrics.swapCount.toString())
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortText(text = "$label:", style = SortTheme.typography.bodySmall)
        SortText(text = value, style = SortTheme.typography.bodySmall, color = SortTheme.colorScheme.primary)
    }
}

private fun formatNs(ns: Long): String {
    return when {
        ns > 1_000_000_000 -> "${(ns / 1_000_000_000.0).toString().take(4)} s"
        ns > 1_000_000 -> "${(ns / 1_000_000.0).toInt()} ms"
        ns > 1_000 -> "${(ns / 1_000.0).toInt()} μs"
        else -> "$ns ns"
    }
}
