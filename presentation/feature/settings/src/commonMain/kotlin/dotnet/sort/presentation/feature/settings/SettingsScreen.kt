package dotnet.sort.presentation.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import dotnet.sort.designsystem.utils.toDisplayName
import org.jetbrains.compose.resources.stringResource
import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.components.atoms.SortSlider
import dotnet.sort.domain.model.Language
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortBottomBar
import dotnet.sort.designsystem.components.molecules.SortBottomBarItem
import dotnet.sort.designsystem.components.molecules.SortSettingsRow
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.domain.model.BarColorTheme

/**
 * 設定画面。
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
fun SettingsScreen(
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
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = stringResource(Res.string.settings_title),
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
        SettingsContent(
            state = state,
            onIntent = onIntent,
            modifier =
                Modifier
                    .fillMaxSize(),
        )
    }
}

/**
 * 設定画面のコンテンツ。ステートレスでPreview可能。
 *
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param modifier Modifier
 */
@Composable
fun SettingsContent(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SpacingTokens.M)
                .padding(
                    top = SpacingTokens.FloatingTopBarInset,
                    bottom = SpacingTokens.FloatingBottomBarInset,
                ),
    ) {
        SortSettingsRow(
            title = stringResource(Res.string.settings_dark_mode_title),
            description = stringResource(Res.string.settings_dark_mode_desc),
            checked = state.isDarkTheme,
            onCheckedChange = { onIntent(SettingsIntent.ToggleTheme(it)) },
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

         SortText(
            text = stringResource(Res.string.settings_section_language),
            style = SortTheme.typography.titleSmall,
            color = SortTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        SortDropdown(
            label = stringResource(Res.string.settings_language_label),
            selectedItem = state.language,
            items = Language.entries.toList(),
            onItemSelected = { onIntent(SettingsIntent.SelectLanguage(it)) },
            itemLabel = { stringResource(it.toDisplayName()) },
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        SortText(
            text = stringResource(Res.string.settings_section_visualization),
            style = SortTheme.typography.titleSmall,
            color = SortTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        SortDropdown(
            label = stringResource(Res.string.settings_bar_theme_label),
            selectedItem = state.barTheme,
            items = BarColorTheme.entries.toList(),
            onItemSelected = { onIntent(SettingsIntent.SelectBarTheme(it)) },
            itemLabel = { stringResource(it.toDisplayName()) },
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        SortText(
            text = stringResource(Res.string.settings_section_sound),
            style = SortTheme.typography.titleSmall,
            color = SortTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        SortSettingsRow(
            title = stringResource(Res.string.settings_sound_effects_title),
            description = stringResource(Res.string.settings_sound_effects_desc),
            checked = state.isSoundEnabled,
            onCheckedChange = { onIntent(SettingsIntent.ToggleSound(it)) },
        )
        SortSlider(
            label = stringResource(Res.string.settings_volume_label),
            valueLabel = "${(state.soundVolume * 100).toInt()}%",
            value = state.soundVolume,
            onValueChange = { onIntent(SettingsIntent.SetSoundVolume(it)) },
            valueRange = 0f..1f,
            steps = 0,
            enabled = state.isSoundEnabled,
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        SortText(
            text = stringResource(Res.string.settings_section_about),
            style = SortTheme.typography.titleSmall,
            color = SortTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        SortText(
            text = "${stringResource(Res.string.settings_version_prefix)}${state.appVersion}",
            style = SortTheme.typography.bodyMedium,
        )
    }
}
