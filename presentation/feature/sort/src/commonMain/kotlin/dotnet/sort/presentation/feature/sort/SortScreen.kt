package dotnet.sort.presentation.feature.sort

import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import dotnet.sort.designsystem.components.atoms.SortIcon
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortBottomSheet
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.common_done
import dotnet.sort.designsystem.generated.resources.common_settings
import dotnet.sort.designsystem.generated.resources.nav_compare
import dotnet.sort.designsystem.generated.resources.nav_home
import dotnet.sort.designsystem.generated.resources.nav_learn
import dotnet.sort.designsystem.generated.resources.nav_quiz
import dotnet.sort.designsystem.generated.resources.nav_settings
import dotnet.sort.designsystem.generated.resources.nav_sort
import dotnet.sort.designsystem.generated.resources.sort_controls_settings
import dotnet.sort.designsystem.generated.resources.sort_title
import dotnet.sort.designsystem.tokens.SpacingTokens
import org.jetbrains.compose.resources.stringResource
import dotnet.sort.presentation.feature.sort.components.AlgorithmSelector
import dotnet.sort.presentation.feature.sort.components.DescriptionDisplay
import dotnet.sort.presentation.feature.sort.components.MetricsDisplay
import dotnet.sort.presentation.feature.sort.components.SortControlPanel
import dotnet.sort.presentation.feature.sort.components.SortSettingsPanel
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
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
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
    onNavigateToQuiz: () -> Unit,
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
                        text = stringResource(Res.string.sort_controls_settings),
                        style = SortTheme.typography.titleLarge,
                        color = SortTheme.colorScheme.onSurface,
                    )
                    SortButton(
                        text = stringResource(Res.string.common_done),
                        onClick = { showSettingsSheet = false },
                        style = SortButtonStyle.Text,
                    )
                }

                AlgorithmSelector(
                    selectedAlgorithm = state.algorithm,
                    onAlgorithmSelected = { onIntent(SortIntent.SelectAlgorithm(it)) },
                    enabled = !state.isLoading && !state.isPlaying,
                )

                SortSettingsPanel(
                    arraySize = state.arraySize,
                    onArraySizeChange = { onIntent(SortIntent.SetArraySize(it)) },
                    playbackSpeed = state.playbackSpeed,
                    onSpeedChange = { onIntent(SortIntent.SetSpeed(it)) },
                    enabled = !state.isLoading,
                )
                
                Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
            }
        }
    }

    SortScaffold(
        modifier =
            modifier
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown) {
                        when (event.key) {
                            Key.Spacebar -> {
                                if (state.isPlaying) {
                                    onIntent(SortIntent.PauseSort)
                                } else if (state.sortResult != null) {
                                    onIntent(SortIntent.ResumeSort)
                                } else {
                                    onIntent(SortIntent.StartSort)
                                }
                                true
                            }
                            Key.DirectionRight -> {
                                if (!state.isPlaying && state.sortResult != null) {
                                    onIntent(SortIntent.StepForward)
                                    true
                                } else {
                                    false
                                }
                            }
                            Key.DirectionLeft -> {
                                if (!state.isPlaying && state.sortResult != null) {
                                    onIntent(SortIntent.StepBackward)
                                    true
                                } else {
                                    false
                                }
                            }
                            else -> false
                        }
                    } else {
                        false
                    }
                }.focusRequester(focusRequester)
                .focusable(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.sort_title),
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
        SortContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

// ... SortContent updates below ...

@Composable
fun SortContent(
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val isLandscape = maxWidth > SpacingTokens.LandscapeBreakpoint
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SpacingTokens.M)
                .padding(
                     top = SpacingTokens.FloatingTopBarInset,
                     bottom = SpacingTokens.FloatingBottomBarInset,
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
        ) {
            // Visualizer with fixed height (main feature)
            SortVisualizer(
                array = state.currentNumbers,
                highlightIndices = state.highlightingIndices,
                sortedIndices = state.sortedIndices,
                description = state.stepDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SpacingTokens.VisualizerHeight),
            )

            // Control Panel
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
                currentStep = state.currentStepIndex,
                totalSteps = state.sortResult?.steps?.size ?: 0,
                onSeek = { onIntent(SortIntent.SeekTo(it)) },
                enabled = !state.isLoading,
            )

            // Step Description
            DescriptionDisplay(
                description = state.stepDescription,
                modifier = Modifier.fillMaxWidth(),
            )

            // Metrics Display
            MetricsDisplay(
                 metrics = state.sortResult?.complexityMetrics,
            )
        }
    }
}
