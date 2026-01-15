package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SortTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Learn") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                         Text("<") // Text icon for stability
                    }
                }
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
                Text(
                    text = "Coming Soon",
                    style = SortTheme.typography.headlineMedium,
                    color = SortTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(SpacingTokens.M))
                Text(
                    text = "Learn about sorting algorithms here.",
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurface
                )
            }
        }
    }
}
