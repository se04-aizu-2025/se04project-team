package dotnet.sort.presentation.feature.sort

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
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
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortScreen(
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Keyboard Focus Requester
    val focusRequester = remember { FocusRequester() }

    // Launch effect to request focus when screen is shown
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = modifier
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.Spacebar -> {
                            if (state.isPlaying) onIntent(SortIntent.PauseSort)
                            else if (state.sortResult != null) onIntent(SortIntent.ResumeSort)
                            else onIntent(SortIntent.StartSort)
                            true
                        }
                        Key.DirectionRight -> {
                            if (!state.isPlaying && state.sortResult != null) {
                                onIntent(SortIntent.StepForward)
                                true
                            } else false
                        }
                        Key.DirectionLeft -> {
                            if (!state.isPlaying && state.sortResult != null) {
                                onIntent(SortIntent.StepBackward)
                                true
                            } else false
                        }
                        else -> false
                    }
                } else false
            }
            .focusRequester(focusRequester)
            .focusable(),
        containerColor = SortTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Visualizer") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        SortContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(SpacingTokens.M)
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
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val isLandscape = maxWidth > 600.dp

        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Left: Visualizer (Main Content)
                Column(modifier = Modifier.weight(1f)) {
                    DescriptionDisplay(
                        description = state.stepDescription,
                        modifier = Modifier.fillMaxWidth().padding(bottom = SpacingTokens.S)
                    )

                    SortVisualizer(
                        array = state.currentNumbers,
                        highlightIndices = state.highlightingIndices,
                        description = state.stepDescription,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.width(SpacingTokens.L))

                // Right: Controls
                Column(modifier = Modifier.width(320.dp)) {
                    AlgorithmSelector(
                        selectedAlgorithm = state.algorithm,
                        onAlgorithmSelected = { onIntent(SortIntent.SelectAlgorithm(it)) },
                        enabled = !state.isLoading && !state.isPlaying
                    )

                    Spacer(modifier = Modifier.height(SpacingTokens.M))

                    MetricsDisplay(
                        metrics = state.sortResult?.complexityMetrics
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
                        enabled = !state.isLoading
                    )
                }
            }
        } else {
            // Portrait Layout
            Column(modifier = Modifier.fillMaxSize()) {
                AlgorithmSelector(
                    selectedAlgorithm = state.algorithm,
                    onAlgorithmSelected = { onIntent(SortIntent.SelectAlgorithm(it)) },
                    enabled = !state.isLoading && !state.isPlaying
                )

                Spacer(modifier = Modifier.height(SpacingTokens.L))

                SortVisualizer(
                    array = state.currentNumbers,
                    highlightIndices = state.highlightingIndices,
                    description = state.stepDescription,
                    modifier = Modifier.weight(1f)
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
                    enabled = !state.isLoading
                )
            }
        }
    }
}
