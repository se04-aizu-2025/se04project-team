package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.nav_compare
import dotnet.sort.designsystem.generated.resources.nav_home
import dotnet.sort.designsystem.generated.resources.nav_learn
import dotnet.sort.designsystem.generated.resources.nav_quiz
import dotnet.sort.designsystem.generated.resources.nav_settings
import dotnet.sort.designsystem.generated.resources.nav_sort
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.SortType
import org.jetbrains.compose.resources.stringResource

/**
 * Learn 画面。
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
fun LearnScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isSettingsSelected: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToLearnDetail: (SortType) -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    state: LearnState,
    onIntent: (LearnIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        LearnContent(
            state = state,
            onIntent = onIntent,
            onNavigateToLearnDetail = onNavigateToLearnDetail,
            modifier = Modifier.fillMaxSize().padding(padding),
        )
    }
}

@Composable
private fun LearnContent(
    state: LearnState,
    onIntent: (LearnIntent) -> Unit,
    onNavigateToLearnDetail: (SortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
    ) {
        items(state.algorithms, key = { it.type.name }) { item ->
            SortCard(
                title = stringResource(item.title),
                description = stringResource(item.description),
                icon = item.icon,
                onClick = {
                    onIntent(LearnIntent.SelectAlgorithm(item.type))
                    onNavigateToLearnDetail(item.type)
                },
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpacingTokens.FloatingBottomBarInset))
        }
    }
}
