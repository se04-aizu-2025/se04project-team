package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmConcept

/**
 * アルゴリズムの動作原理・概念を表示するコンポーネント。
 *
 * @param concept アルゴリズムの概念情報
 * @param modifier Modifier
 */
@Composable
fun AlgorithmConceptsView(
    concept: AlgorithmConcept,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(SpacingTokens.M)
    ) {
        // How It Works Section
        SortSectionCard(title = "How It Works") {
            SortText(
                text = concept.howItWorks,
                style = SortTheme.typography.bodyLarge,
                color = SortTheme.colorScheme.onSurface
            )
        }
        
        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Key Idea Section
        SortSectionCard(title = "Key Idea") {
            SortText(
                text = concept.keyIdea,
                style = SortTheme.typography.bodyLarge,
                color = SortTheme.colorScheme.onSurface
            )
        }
        
        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Steps Section
        SortSectionCard(title = "Algorithm Steps") {
            concept.steps.forEachIndexed { index, step ->
                SortText(
                    text = "${index + 1}. $step",
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = SpacingTokens.XS)
                )
            }
        }
    }
}
