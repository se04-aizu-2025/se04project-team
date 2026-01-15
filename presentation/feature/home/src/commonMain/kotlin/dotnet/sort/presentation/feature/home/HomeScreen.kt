package dotnet.sort.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortCard
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ホーム画面のオプションデータ。
 */
private data class HomeOption(
    val title: String,
    val description: String,
    val icon: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)

/**
 * ホーム画面。
 *
 * @param onNavigateToSort Sort画面への遷移コールバック
 * @param onNavigateToLearn Learn画面への遷移コールバック
 * @param onNavigateToCompare Compare画面への遷移コールバック
 * @param onNavigateToSettings Settings画面への遷移コールバック
 * @param modifier Modifier
 */
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
            onClick = onNavigateToLearn
        ),
        HomeOption(
            title = "Compare",
            description = "Compare performance of algorithms.",
            icon = "⚖️",
            onClick = onNavigateToCompare
        ),
        HomeOption(
            title = "Settings",
            description = "App settings and themes.",
            icon = "⚙️",
            onClick = onNavigateToSettings
        )
    )

    SortScaffold(modifier = modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = SpacingTokens.M),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(SpacingTokens.XL))

            SortText(
                text = "DNSort",
                style = SortTheme.typography.displayMedium,
                color = SortTheme.colorScheme.primary
            )

            SortText(
                text = "Algorithm Visualization Tool",
                style = SortTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = SpacingTokens.L)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                horizontalArrangement = Arrangement.spacedBy(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.M),
                contentPadding = PaddingValues(bottom = SpacingTokens.M)
            ) {
                items(options) { option ->
                    SortCard(
                        title = option.title,
                        description = option.description,
                        icon = option.icon,
                        onClick = option.onClick,
                        enabled = option.enabled
                    )
                }
            }
        }
    }
}
