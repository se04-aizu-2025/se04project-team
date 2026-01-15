package dotnet.sort.presentation.feature.sort.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

@Composable
fun DescriptionDisplay(
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SortTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(SpacingTokens.S)
            )
            .padding(SpacingTokens.M),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = description.ifEmpty { "Ready" },
            style = SortTheme.typography.bodyLarge,
            color = SortTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}
