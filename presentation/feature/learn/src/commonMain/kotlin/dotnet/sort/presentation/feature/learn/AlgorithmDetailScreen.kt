package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.learn.components.AlgorithmOverview
import dotnet.sort.presentation.feature.learn.model.AlgorithmContentProvider

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AlgorithmDetailScreen(
    sortType: SortType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Analysis", "Implementation")

    SortScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SortTopBar(
                title = sortType.displayName,
                onBackClick = onBackClick,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxSize(), // Removed padding(SpacingTokens.M) to let scroll view handle it or sub-composables
                contentAlignment = Alignment.TopStart // Changed to TopStart for scrollable content
            ) {
                when (selectedTabIndex) {
                    0 -> AlgorithmOverview(
                        history = AlgorithmContentProvider.getHistory(sortType)
                    )
                    1 -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { 
                        Text("Analysis content coming soon (PR-55/56)") 
                    }
                    2 -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Implementation code coming soon (PR-57)")
                    }
                }
            }
        }
    }
}
