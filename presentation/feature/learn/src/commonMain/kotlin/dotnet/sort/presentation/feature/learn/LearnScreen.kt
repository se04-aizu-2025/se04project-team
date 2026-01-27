package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.utils.toDisplayName
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import dotnet.sort.model.SortType

/**
 * Learn 画面。
 */
@Composable
fun LearnScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isSettingsSelected: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToDetail: (SortType) -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<LearnViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigationTarget) {
        state.navigationTarget?.let { sortType ->
            onNavigateToDetail(sortType)
            viewModel.send(LearnIntent.ConsumeNavigation)
        }
    }

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.nav_learn),
                onBackClick = onBackClick,
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
                            icon = SortIcons.Settings,
                            contentDescription = stringResource(Res.string.nav_settings),
                            selected = isSettingsSelected,
                            onClick = onNavigateToSettings,
                        ),
                    ),
            )
        },
    ) { padding ->
        LearnContent(
            state = state,
            onIntent = { viewModel.send(it) },
            modifier = Modifier.fillMaxSize().padding(padding),
        )
    }
}

@Composable
private fun LearnContent(
    state: LearnState,
    onIntent: (LearnIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val descriptions = mapOf(
        SortType.BUBBLE to Res.string.learn_desc_bubble,
        SortType.SELECTION to Res.string.learn_desc_selection,
        SortType.INSERTION to Res.string.learn_desc_insertion,
        SortType.SHELL to Res.string.learn_desc_shell,
        SortType.MERGE to Res.string.learn_desc_merge,
        SortType.QUICK to Res.string.learn_desc_quick,
        SortType.HEAP to Res.string.learn_desc_heap,
    )
    val icons = mapOf(
        SortType.BUBBLE to "🫧",
        SortType.SELECTION to "🎯",
        SortType.INSERTION to "🧩",
        SortType.SHELL to "🐚",
        SortType.MERGE to "🔀",
        SortType.QUICK to "⚡",
        SortType.HEAP to "⛰️",
    )

    LazyColumn(
        modifier = modifier.padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
    ) {
        items(state.algorithms, key = { it.name }) { type ->
            SortCard(
                title = stringResource(type.toDisplayName()),
                description = descriptions[type]?.let { stringResource(it) } ?: "",
                icon = icons[type] ?: "📘",
                onClick = {
                    onIntent(LearnIntent.SelectAlgorithm(type))
                },
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
        }
    }
}
