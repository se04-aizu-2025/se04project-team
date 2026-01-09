package dotnet.sort.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

data class HomeOption(
    val title: String,
    val description: String,
    val icon: String, // Emoji
    val onClick: () -> Unit,
    val enabled: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSort: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCompare: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        HomeOption(
            title = "Visualizer",
            description = "Visualize sorting algorithms in real-time.",
            icon = "📊",
            onClick = onNavigateToSort
        ),
        HomeOption(
            title = "Learn",
            description = "Learn about different sorting algorithms.",
            icon = "🎓",
            onClick = onNavigateToLearn,
            enabled = false // Placeholder for now
        ),
        HomeOption(
            title = "Compare",
            description = "Compare performance of algorithms.",
            icon = "⚖️",
            onClick = onNavigateToCompare,
            enabled = false // Placeholder for now
        ),
        HomeOption(
            title = "Settings",
            description = "App settings and themes.",
            icon = "⚙️",
            onClick = onNavigateToSettings
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SortTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = SpacingTokens.M),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(SpacingTokens.XL))
            
            Text(
                text = "DNSort",
                style = SortTheme.typography.displayMedium,
                color = SortTheme.colorScheme.primary
            )
            
            Text(
                text = "Algorithm Visualization Tool",
                style = SortTheme.typography.titleMedium,
                color = SortTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = SpacingTokens.L)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
                contentPadding = PaddingValues(bottom = SpacingTokens.M)
            ) {
                items(options) { option ->
                    HomeCard(option = option)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(option: HomeOption) {
    Card(
        onClick = option.onClick,
        enabled = option.enabled,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        colors = CardDefaults.cardColors(
            containerColor = SortTheme.colorScheme.surface,
            contentColor = SortTheme.colorScheme.onSurface,
            disabledContainerColor = SortTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            disabledContentColor = SortTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpacingTokens.M)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = option.icon,
                    style = SortTheme.typography.displayMedium, // Large emoji used as icon
                    color = if (option.enabled) SortTheme.colorScheme.primary else SortTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(SpacingTokens.S))
                Text(
                    text = option.title,
                    style = SortTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(SpacingTokens.XS))
                Text(
                    text = option.description,
                    style = SortTheme.typography.bodySmall,
                )
            }
        }
    }
}
