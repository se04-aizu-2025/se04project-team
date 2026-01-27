package dotnet.sort.presentation.feature.learn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortTopBar
import dotnet.sort.designsystem.components.organisms.SortScaffold
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.learn.components.AlgorithmOverview
import dotnet.sort.presentation.feature.learn.components.AlgorithmConceptsView
import dotnet.sort.presentation.feature.learn.components.AlgorithmComplexityView
import dotnet.sort.presentation.feature.learn.components.AlgorithmUseCaseView
import dotnet.sort.presentation.feature.learn.components.AlgorithmCodeView
import dotnet.sort.presentation.feature.learn.model.AlgorithmContentProvider

// NOTE: Tab, TabRow は Material3 を使用 (Design System に代替なし)
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * アルゴリズム詳細画面。
 *
 * Overview, Analysis, Implementation の3つのタブを持つ。
 *
 * @param sortType 表示するアルゴリズムの種類
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@Composable
fun AlgorithmDetailScreen(
    sortType: SortType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Analysis", "Complexity", "Use Case", "Implementation")

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
            // NOTE: TabRow/Tab は Design System に代替がないため Material3 を直接使用
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { SortText(text = title) }
                    )
                }
            }
            
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                when (selectedTabIndex) {
                    0 -> AlgorithmOverview(
                        history = AlgorithmContentProvider.getHistory(sortType)
                    )
                    1 -> AlgorithmConceptsView(
                        concept = AlgorithmContentProvider.getConcept(sortType)
                    )
                    2 -> AlgorithmComplexityView(
                        complexity = AlgorithmContentProvider.getComplexity(sortType)
                    )
                    3 -> AlgorithmUseCaseView(
                        useCase = AlgorithmContentProvider.getUseCase(sortType)
                    )
                    4 -> AlgorithmCodeView(
                        implementation = AlgorithmContentProvider.getImplementation(sortType)
                    )
                }
            }
        }
    }
}
