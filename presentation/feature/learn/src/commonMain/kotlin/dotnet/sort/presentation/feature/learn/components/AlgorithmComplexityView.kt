package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dotnet.sort.designsystem.components.atoms.SortDivider
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmComplexity
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlgorithmComplexityView(
    complexity: AlgorithmComplexity,
    modifier: Modifier = Modifier
) {
    val bestCaseLabel = stringResource(Res.string.learn_best_case)
    val averageCaseLabel = stringResource(Res.string.learn_average_case)
    val worstCaseLabel = stringResource(Res.string.learn_worst_case)
    val spaceCaseLabel = stringResource(Res.string.learn_space_complexity)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(SpacingTokens.M),
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.M)
    ) {
        // Time Complexity Section
        SortSectionCard(title = stringResource(Res.string.learn_time_complexity)) {
            Column(
                modifier = Modifier.padding(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                ComplexityRow(bestCaseLabel, complexity.timeComplexityBest)
                SortDivider()
                ComplexityRow(averageCaseLabel, complexity.timeComplexityAverage)
                SortDivider()
                ComplexityRow(worstCaseLabel, complexity.timeComplexityWorst)

                Spacer(modifier = Modifier.height(SpacingTokens.S))

                SortText(
                    text = complexity.timeComplexityExplanation,
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Space Complexity Section
        SortSectionCard(title = stringResource(Res.string.learn_space_complexity)) {
            Column(
                modifier = Modifier.padding(SpacingTokens.M),
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.S)
            ) {
                ComplexityRow(spaceCaseLabel, complexity.spaceComplexity)

                Spacer(modifier = Modifier.height(SpacingTokens.S))

                SortText(
                    text = complexity.spaceComplexityExplanation,
                    style = SortTheme.typography.bodyMedium,
                    color = SortTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Intuition Section
        SortSectionCard(title = stringResource(Res.string.learn_intuition)) {
            Column(modifier = Modifier.padding(SpacingTokens.M)) {
                SortText(
                    text = complexity.intuition,
                    style = SortTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ComplexityRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SortText(
            text = label,
            style = SortTheme.typography.labelMedium,
            color = SortTheme.colorScheme.onSurfaceVariant
        )
        SortText(
            text = value,
            style = SortTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = SortTheme.colorScheme.primary
        )
    }
}
