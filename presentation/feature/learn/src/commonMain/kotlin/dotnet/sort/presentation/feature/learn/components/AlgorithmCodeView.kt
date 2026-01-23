package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmImplementation

@Composable
fun AlgorithmCodeView(
    implementation: AlgorithmImplementation,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(SpacingTokens.M)
    ) {
        // Description
        if (implementation.description.isNotEmpty()) {
            SortSectionCard(title = "Description") {
                Box(modifier = Modifier.padding(SpacingTokens.M)) {
                    SortText(
                        text = implementation.description,
                        style = SortTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpacingTokens.M))
        }

        // Code Block
        SortSectionCard(title = "Implementation (Kotlin)") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingTokens.S)
            ) {
                // Code Container
                Surface(
                    color = Color(0xFF2B2B2B), // Dark background for code
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Header row with Copy button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E1E1E)) // Slightly darker header
                                .padding(horizontal = SpacingTokens.M, vertical = SpacingTokens.XS),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                        ) {
                            SortText(
                                text = "Kotlin",
                                style = SortTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(implementation.code))
                                },
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy code",
                                    tint = Color.Gray
                                )
                            }
                        }

                        // Code Content
                        SelectionContainer {
                            SortText(
                                text = implementation.code,
                                style = SortTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = SortTheme.typography.bodyMedium.lineHeight * 1.2
                                ),
                                color = Color(0xFFA9B7C6), // Light grey text
                                modifier = Modifier.padding(SpacingTokens.M)
                            )
                        }
                    }
                }
            }
        }
    }
}
