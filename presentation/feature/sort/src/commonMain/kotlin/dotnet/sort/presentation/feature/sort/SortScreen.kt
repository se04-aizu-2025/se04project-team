package dotnet.sort.presentation.feature.sort

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.sort.components.AlgorithmSelector
import dotnet.sort.presentation.feature.sort.components.SortControlPanel
import dotnet.sort.presentation.feature.sort.components.SortVisualizer
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel<SortViewModel>(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(SpacingTokens.M)
        ) {
            val isLandscape = maxWidth > 600.dp

            if (isLandscape) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // Left: Visualizer (Main Content)
                    Column(modifier = Modifier.weight(1f)) {
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
                            onAlgorithmSelected = { viewModel.send(SortIntent.SelectAlgorithm(it)) },
                            enabled = !state.isLoading && !state.isPlaying
                        )
                        Spacer(modifier = Modifier.height(SpacingTokens.M))
                        SortControlPanel(
                            isPlaying = state.isPlaying,
                            onPlayPauseClick = {
                                if (state.isPlaying) {
                                    viewModel.send(SortIntent.PauseSort)
                                } else {
                                    if (state.sortResult == null) {
                                        viewModel.send(SortIntent.StartSort)
                                    } else {
                                        viewModel.send(SortIntent.ResumeSort)
                                    }
                                }
                            },
                            onResetClick = { viewModel.send(SortIntent.ResetSort) },
                            onStepForwardClick = { viewModel.send(SortIntent.StepForward) },
                            onStepBackwardClick = { viewModel.send(SortIntent.StepBackward) },
                            arraySize = state.arraySize,
                            onArraySizeChange = { viewModel.send(SortIntent.SetArraySize(it)) },
                            playbackSpeed = state.playbackSpeed,
                            onSpeedChange = { viewModel.send(SortIntent.SetSpeed(it)) },
                            currentStep = state.currentStepIndex,
                            totalSteps = state.sortResult?.steps?.size ?: 0,
                            onSeek = { viewModel.send(SortIntent.SeekTo(it)) },
                            enabled = !state.isLoading
                        )
                    }
                }
            } else {
                // Portrait Layout
                Column(modifier = Modifier.fillMaxSize()) {
                    AlgorithmSelector(
                        selectedAlgorithm = state.algorithm,
                        onAlgorithmSelected = { viewModel.send(SortIntent.SelectAlgorithm(it)) },
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
                                    viewModel.send(SortIntent.PauseSort)
                                } else {
                                    if (state.sortResult == null) {
                                        viewModel.send(SortIntent.StartSort)
                                    } else {
                                        viewModel.send(SortIntent.ResumeSort)
                                    }
                                }
                            },
                            onResetClick = { viewModel.send(SortIntent.ResetSort) },
                            onStepForwardClick = { viewModel.send(SortIntent.StepForward) },
                            onStepBackwardClick = { viewModel.send(SortIntent.StepBackward) },
                            arraySize = state.arraySize,
                            onArraySizeChange = { viewModel.send(SortIntent.SetArraySize(it)) },
                            playbackSpeed = state.playbackSpeed,
                            onSpeedChange = { viewModel.send(SortIntent.SetSpeed(it)) },
                            currentStep = state.currentStepIndex,
                            totalSteps = state.sortResult?.steps?.size ?: 0,
                            onSeek = { viewModel.send(SortIntent.SeekTo(it)) },
                            enabled = !state.isLoading
                        )
                }
            }
        }
    }
}
