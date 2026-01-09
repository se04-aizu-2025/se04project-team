package dotnet.sort.presentation.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SortTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(SpacingTokens.M)
        ) {
            // Theme Setting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpacingTokens.S),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Dark Mode",
                        style = SortTheme.typography.titleMedium,
                        color = SortTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Switch between light and dark themes",
                        style = SortTheme.typography.bodySmall,
                        color = SortTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                Switch(
                    checked = state.isDarkTheme,
                    onCheckedChange = { viewModel.send(SettingsIntent.ToggleTheme(it)) }
                )
            }

            Spacer(modifier = Modifier.height(SpacingTokens.L))

            // App Info
            Text(
                text = "App Info",
                style = SortTheme.typography.titleSmall,
                color = SortTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(SpacingTokens.S))
            Text(
                text = "Version: ${state.appVersion}",
                style = SortTheme.typography.bodyMedium,
                color = SortTheme.colorScheme.onSurface
            )
        }
    }
}
