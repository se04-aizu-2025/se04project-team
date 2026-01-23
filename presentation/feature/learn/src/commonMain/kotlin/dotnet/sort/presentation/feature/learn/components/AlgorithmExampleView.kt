package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortButton
import dotnet.sort.designsystem.components.atoms.SortIcon
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.ColorTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmExample
import dotnet.sort.presentation.feature.learn.model.AlgorithmExampleStep
import dotnet.sort.presentation.feature.learn.model.StepModificationType

@Composable
fun AlgorithmExampleView(
    example: AlgorithmExample,
    onNavigateToSort: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(SpacingTokens.M)
    ) {
        // Visualizer Button
        SortButton(
            text = "View in Visualizer",
            onClick = onNavigateToSort,
            icon = Icons.Default.PlayArrow,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Initial State
        SortSectionCard(title = "Initial Array") {
            ArrayValueRow(
                values = example.initialArray,
                modifier = Modifier.padding(SpacingTokens.M)
            )
        }

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Steps
        SortSectionCard(title = "Execution Steps") {
            Column(
                modifier = Modifier.padding(SpacingTokens.S)
            ) {
                example.steps.forEach { step ->
                    StepItem(step = step)
                    Spacer(modifier = Modifier.height(SpacingTokens.S))
                }
            }
        }
    }
}

@Composable
private fun StepItem(
    step: AlgorithmExampleStep,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SortTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .padding(SpacingTokens.M)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(SortTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(horizontal = SpacingTokens.S, vertical = SpacingTokens.XS)
            ) {
                SortText(
                    text = "Step ${step.stepIndex}",
                    style = SortTheme.typography.labelMedium,
                    color = SortTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(SpacingTokens.M))
            SortText(
                text = step.description,
                style = SortTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.height(SpacingTokens.M))
        
        ArrayValueRow(
            values = step.arrayState,
            highlightIndices = step.highlightIndices,
            modificationType = step.modificationType
        )
    }
}

@Composable
private fun ArrayValueRow(
    values: List<Int>,
    highlightIndices: List<Int> = emptyList(),
    modificationType: StepModificationType = StepModificationType.NONE,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        values.forEachIndexed { index, value ->
            val isHighlighted = index in highlightIndices
            val backgroundColor = if (isHighlighted) {
                when (modificationType) {
                    StepModificationType.COMPARE -> ColorTokens.BarComparing
                    StepModificationType.SWAP -> ColorTokens.BarSwapping
                    StepModificationType.SET -> ColorTokens.BarSorted
                    else -> SortTheme.colorScheme.primaryContainer
                }
            } else {
                SortTheme.colorScheme.surface
            }
            
            val textColor = if (isHighlighted) {
                 SortTheme.colorScheme.onPrimary
            } else {
                SortTheme.colorScheme.onSurface
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .background(backgroundColor, RoundedCornerShape(4.dp))
                    .then(
                        if (!isHighlighted) {
                             Modifier.background(SortTheme.colorScheme.surface, RoundedCornerShape(4.dp))
                                     .then(Modifier) // placeholder for border if needed
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                SortText(
                    text = value.toString(),
                    color = textColor,
                    style = SortTheme.typography.titleMedium
                )
            }
        }
    }
}
