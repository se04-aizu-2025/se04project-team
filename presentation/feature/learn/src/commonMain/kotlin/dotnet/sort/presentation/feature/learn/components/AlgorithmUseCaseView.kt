package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortIcon
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmUseCase
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlgorithmUseCaseView(
    useCase: AlgorithmUseCase,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
    ) {
        // Best Use Cases
        SortSectionCard(title = stringResource(Res.string.learn_best_use_cases)) {
            Column(
                modifier = Modifier.padding(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                useCase.bestUseCases.forEach { item ->
                    UseCaseItem(
                        text = item,
                        isPositive = true
                    )
                }
            }
        }

        // Not Recommended
        SortSectionCard(title = stringResource(Res.string.learn_not_recommended)) {
            Column(
                modifier = Modifier.padding(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                useCase.notRecommended.forEach { item ->
                    UseCaseItem(
                        text = item,
                        isPositive = false
                    )
                }
            }
        }

        // Real World Examples
        SortSectionCard(title = stringResource(Res.string.learn_real_world_examples)) {
            Column(
                modifier = Modifier.padding(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                useCase.realWorldExamples.forEach { item ->
                    UseCaseItem(
                        text = item,
                        isExample = true
                    )
                }
            }
        }
    }
}

@Composable
private fun UseCaseItem(
    text: String,
    isPositive: Boolean = true,
    isExample: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SpacingTokens.S)
    ) {
        SortIcon(
            imageVector = when {
                isExample -> Icons.Default.Public
                isPositive -> Icons.Default.CheckCircle
                else -> Icons.Default.Block
            },
            contentDescription = null,
            tint = when {
                isExample -> SortTheme.colorScheme.primary
                isPositive -> SortTheme.colorScheme.secondary
                else -> SortTheme.colorScheme.error
            }
        )
        SortText(
            text = text,
            style = SortTheme.typography.bodyMedium,
            color = SortTheme.colorScheme.onSurface
        )
    }
}
