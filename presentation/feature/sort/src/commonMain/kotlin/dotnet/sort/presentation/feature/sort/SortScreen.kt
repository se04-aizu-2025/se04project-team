package dotnet.sort.presentation.feature.sort

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.sort.components.AlgorithmSelector
import dotnet.sort.presentation.feature.sort.components.SortControlPanel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel<SortViewModel>(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = SortTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(SpacingTokens.M)
        ) {
            AlgorithmSelector(
                selectedAlgorithm = state.algorithm,
                onAlgorithmSelected = { viewModel.send(SortIntent.SelectAlgorithm(it)) },
                enabled = !state.isLoading && !state.isPlaying
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
                enabled = !state.isLoading
            )
        }
    }
}
