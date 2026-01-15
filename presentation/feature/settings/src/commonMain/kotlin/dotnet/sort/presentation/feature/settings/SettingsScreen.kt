package dotnet.sort.presentation.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.atoms.SortTopBar
import dotnet.sort.designsystem.components.molecules.SortSettingsRow
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * 設定画面。
 *
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@Composable
fun SettingsScreen(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = "Settings",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        SettingsContent(
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Theme Setting
        SortSettingsRow(
            title = "Dark Mode",
            description = "Switch between light and dark themes",
            checked = state.isDarkTheme,
            onCheckedChange = { onIntent(SettingsIntent.ToggleTheme(it)) }
        )

        Spacer(modifier = Modifier.height(SpacingTokens.L))

        // App Info
        SortText(
            text = "App Info",
            style = SortTheme.typography.titleSmall,
            color = SortTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(SpacingTokens.S))
        SortText(
            text = "Version: ${state.appVersion}",
            style = SortTheme.typography.bodyMedium
        )
    }
}
