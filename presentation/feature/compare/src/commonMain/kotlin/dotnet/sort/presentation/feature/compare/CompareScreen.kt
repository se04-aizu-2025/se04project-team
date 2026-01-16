package dotnet.sort.presentation.feature.compare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * Compare 画面。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@Composable
fun CompareScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = "Compare",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SortText(
                    text = "Coming Soon",
                    style = SortTheme.typography.headlineMedium,
                    color = SortTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(SpacingTokens.M))
                SortText(
                    text = "Compare sorting algorithms performance here.",
                    style = SortTheme.typography.bodyMedium
                )
            }
        }
    }
}
