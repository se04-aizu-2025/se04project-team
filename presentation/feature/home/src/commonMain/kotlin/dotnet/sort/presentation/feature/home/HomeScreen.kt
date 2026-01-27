package dotnet.sort.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortCard
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import org.jetbrains.compose.resources.stringResource

/**
 * ホーム画面のオプションデータ。
 */
private data class HomeOption(
    val title: String,
    val description: String,
    val icon: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)

/**
 * ホーム画面。
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
 * @param onNavigateToQuiz Quiz画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param modifier Modifier
 */
@Composable
fun HomeScreen(
    isHomeSelected: Boolean,
    isSortSelected: Boolean,
    isLearnSelected: Boolean,
    isCompareSelected: Boolean,
    isSettingsSelected: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val options =
        listOf(
            HomeOption(
                title = stringResource(Res.string.home_card_visualizer_title),
                description = stringResource(Res.string.home_card_visualizer_desc),
                icon = "📊",
                onClick = onNavigateToSort,
            ),
            HomeOption(
                title = stringResource(Res.string.home_card_learn_title),
                description = stringResource(Res.string.home_card_learn_desc),
                icon = "🎓",
                onClick = onNavigateToLearn,
            ),
            HomeOption(
                title = stringResource(Res.string.home_card_compare_title),
                description = stringResource(Res.string.home_card_compare_desc),
                icon = "⚖️",
                onClick = onNavigateToCompare,
            ),
            HomeOption(
                title = stringResource(Res.string.home_card_quiz_title),
                description = stringResource(Res.string.home_card_quiz_desc),
                icon = "🧠",
                onClick = onNavigateToQuiz,
            ),
            HomeOption(
                title = stringResource(Res.string.home_card_settings_title),
                description = stringResource(Res.string.home_card_settings_desc),
                icon = "⚙️",
                onClick = onNavigateToSettings,
            ),
        )

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.home_title),
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
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = SpacingTokens.M),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
            contentPadding =
                PaddingValues(
                    top = SpacingTokens.FloatingTopBarInset,
                    bottom = SpacingTokens.FloatingBottomBarInset,
                ),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SortText(
                        text = stringResource(Res.string.app_name),
                        style = SortTheme.typography.displayMedium,
                        color = SortTheme.colorScheme.primary,
                    )

                    SortText(
                        text = stringResource(Res.string.app_description),
                        style = SortTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = SpacingTokens.L),
                    )
                }
            }

            items(options) { option ->
                SortCard(
                    title = option.title,
                    description = option.description,
                    icon = option.icon,
                    onClick = option.onClick,
                    enabled = option.enabled,
                )
            }
        }
    }
}
