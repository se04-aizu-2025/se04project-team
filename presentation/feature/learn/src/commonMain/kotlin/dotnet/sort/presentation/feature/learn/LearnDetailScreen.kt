package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortButtonStyle
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.utils.toDisplayName
import org.jetbrains.compose.resources.stringResource

/**
 * Learn 詳細画面。
 */
@Composable
fun LearnDetailScreen(
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
    state: LearnState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selected = state.selectedAlgorithm
    val selectedItem = state.algorithms.firstOrNull { it.type == selected }
    val detail = selected?.let { state.details[it] }

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = selectedItem?.type?.toDisplayName()?.let { stringResource(it) } ?: stringResource(Res.string.learn_detail_algorithm_default),
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
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(SpacingTokens.M),
        ) {
            SortSectionCard(title = selectedItem?.type?.toDisplayName()?.let { stringResource(it) } ?: stringResource(Res.string.learn_detail_algorithm_default)) {
                SortText(text = selectedItem?.description?.let { stringResource(it) } ?: stringResource(Res.string.learn_detail_select_prompt))
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_history)) {
                SortText(text = detail?.history ?: "-")
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_how_it_works)) {
                SortText(text = detail?.principle ?: "-")
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_complexity)) {
                SortText(text = detail?.complexity ?: "-")
                SortText(text = stringResource(Res.string.learn_detail_space_fmt, detail?.spaceComplexity ?: "-"))
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_use_cases)) {
                SortText(text = detail?.useCases ?: "-")
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_kotlin)) {
                SortText(text = detail?.kotlinCode ?: "-")
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortSectionCard(title = stringResource(Res.string.learn_detail_example_steps)) {
                SortText(text = detail?.example ?: "-")
            }

            Spacer(modifier = Modifier.height(SpacingTokens.M))

            SortButton(
                text = stringResource(Res.string.learn_detail_view_in_sort),
                onClick = onNavigateToSort,
                style = SortButtonStyle.Primary,
            )

            Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
        }
    }
}
