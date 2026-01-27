package dotnet.sort.presentation.feature.sort
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.sort.components.AlgorithmSelector
import dotnet.sort.presentation.feature.sort.components.DescriptionDisplay
import dotnet.sort.presentation.feature.sort.components.MetricsDisplay
import dotnet.sort.presentation.feature.sort.components.SortControlPanel
import dotnet.sort.presentation.feature.sort.components.SortVisualizer

/**
 * ソート可視化画面。
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
fun SortScreen(
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
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SortScaffold(
        modifier = modifier,
        topBar = {
            SortTopBar(
                title = "Visualizer",
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
        SortContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

/**
 * ソート画面のコンテンツ。ステートレスでPreview可能。
 *
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param modifier Modifier
 */
@Composable
fun SortContent(
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val isLandscape = maxWidth > 600.dp

        if (isLandscape) {
            Row(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            start = SpacingTokens.M,
                            end = SpacingTokens.M,
                            top = SpacingTokens.FloatingTopBarInset,
                            bottom = SpacingTokens.FloatingBottomBarInset,
                        ),
            ) {
                // Left: Visualizer (Main Content)
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(end = SpacingTokens.M),
                ) {
                    DescriptionDisplay(
                        description = state.stepDescription,
                        modifier = Modifier.fillMaxWidth().padding(bottom = SpacingTokens.M),
                    )

                    SortVisualizer(
                        array = state.currentNumbers,
                        highlightIndices = state.highlightingIndices,
                        description = state.stepDescription,
                        modifier = Modifier.weight(1f),
                    )
                }

                // Right: Controls (scrollable)
                Column(
                    modifier =
                        Modifier
                            .width(320.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(start = SpacingTokens.M),
                ) {
                    AlgorithmSelector(
                        selectedAlgorithm = state.algorithm,
                        onAlgorithmSelected = { onIntent(SortIntent.SelectAlgorithm(it)) },
                        enabled = !state.isLoading && !state.isPlaying,
                    )

                    Spacer(modifier = Modifier.height(SpacingTokens.M))

                    MetricsDisplay(
                        metrics = state.sortResult?.complexityMetrics,
                    )

                    Spacer(modifier = Modifier.height(SpacingTokens.M))

                    SortControlPanel(
                        isPlaying = state.isPlaying,
                        onPlayPauseClick = {
                            if (state.isPlaying) {
                                onIntent(SortIntent.PauseSort)
                            } else {
                                if (state.sortResult == null) {
                                    onIntent(SortIntent.StartSort)
                                } else {
                                    onIntent(SortIntent.ResumeSort)
                                }
                            }
                        },
                        onResetClick = { onIntent(SortIntent.ResetSort) },
                        onShuffleClick = { onIntent(SortIntent.ShuffleArray) },
                        onStepForwardClick = { onIntent(SortIntent.StepForward) },
                        onStepBackwardClick = { onIntent(SortIntent.StepBackward) },
                        arraySize = state.arraySize,
                        onArraySizeChange = { onIntent(SortIntent.SetArraySize(it)) },
                        playbackSpeed = state.playbackSpeed,
                        onSpeedChange = { onIntent(SortIntent.SetSpeed(it)) },
                        currentStep = state.currentStepIndex,
                        totalSteps = state.sortResult?.steps?.size ?: 0,
                        onSeek = { onIntent(SortIntent.SeekTo(it)) },
                        enabled = !state.isLoading,
                    )
                }
            }
        } else {
            // Portrait Layout (scrollable)
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            start = SpacingTokens.M,
                            end = SpacingTokens.M,
                            top = SpacingTokens.FloatingTopBarInset,
                            bottom = SpacingTokens.FloatingBottomBarInset,
                        ),
            ) {
                AlgorithmSelector(
                    selectedAlgorithm = state.algorithm,
                    onAlgorithmSelected = { onIntent(SortIntent.SelectAlgorithm(it)) },
                    enabled = !state.isLoading && !state.isPlaying,
                )

                Spacer(modifier = Modifier.height(SpacingTokens.L))

                SortVisualizer(
                    array = state.currentNumbers,
                    highlightIndices = state.highlightingIndices,
                    description = state.stepDescription,
                    modifier = Modifier.heightIn(min = 240.dp, max = 440.dp),
                )

                Spacer(modifier = Modifier.height(SpacingTokens.L))

                MetricsDisplay(
                    metrics = state.sortResult?.complexityMetrics,
                )

                Spacer(modifier = Modifier.height(SpacingTokens.L))

                SortControlPanel(
                    isPlaying = state.isPlaying,
                    onPlayPauseClick = {
                        if (state.isPlaying) {
                            onIntent(SortIntent.PauseSort)
                        } else {
                            if (state.sortResult == null) {
                                onIntent(SortIntent.StartSort)
                            } else {
                                onIntent(SortIntent.ResumeSort)
                            }
                        }
                    },
                    onResetClick = { onIntent(SortIntent.ResetSort) },
                    onShuffleClick = { onIntent(SortIntent.ShuffleArray) },
                    onStepForwardClick = { onIntent(SortIntent.StepForward) },
                    onStepBackwardClick = { onIntent(SortIntent.StepBackward) },
                    arraySize = state.arraySize,
                    onArraySizeChange = { onIntent(SortIntent.SetArraySize(it)) },
                    playbackSpeed = state.playbackSpeed,
                    onSpeedChange = { onIntent(SortIntent.SetSpeed(it)) },
                    currentStep = state.currentStepIndex,
                    totalSteps = state.sortResult?.steps?.size ?: 0,
                    onSeek = { onIntent(SortIntent.SeekTo(it)) },
                    enabled = !state.isLoading,
                )
            }
        }
    }
}
