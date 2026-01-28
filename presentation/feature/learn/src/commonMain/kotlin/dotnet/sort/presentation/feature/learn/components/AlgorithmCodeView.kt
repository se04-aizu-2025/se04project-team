package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import dotnet.sort.designsystem.components.atoms.SortIconButton
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.ColorTokens
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmImplementation
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

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
            SortSectionCard(title = stringResource(Res.string.learn_description)) {
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
        SortSectionCard(title = stringResource(Res.string.learn_implementation_kotlin)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingTokens.S)
            ) {
                // Code Container
                Surface(
                    color = SortTheme.colorScheme.codeContainer,
                    shape = RoundedCornerShape(SpacingTokens.CornerRadiusSmall),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Header row with Copy button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SortTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = SpacingTokens.M, vertical = SpacingTokens.XS),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SortText(
                                text = stringResource(Res.string.learn_language_kotlin),
                                style = SortTheme.typography.labelMedium,
                                color = SortTheme.colorScheme.onSurfaceVariant
                            )
                            SortIconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(implementation.code))
                                },
                                icon = Icons.Default.ContentCopy,
                                contentDescription = stringResource(Res.string.learn_copy_code),
                                tint = SortTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.height(SpacingTokens.CodeButtonHeight)
                            )
                        }

                        // Code Content with Line Numbers
                        SelectionContainer {
                            Row(modifier = Modifier.padding(SpacingTokens.M)) {
                                // Line Numbers
                                val lineCount = implementation.code.lines().size
                                SortText(
                                    text = (1..lineCount).joinToString("\n"),
                                    style = SortTheme.typography.bodyMedium.copy(
                                        fontFamily = FontFamily.Monospace,
                                        lineHeight = SortTheme.typography.bodyMedium.lineHeight * 1.2
                                    ),
                                    color = SortTheme.colorScheme.onCodeContainer.copy(alpha = 0.5f),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.End,
                                    modifier = Modifier.padding(end = SpacingTokens.M)
                                )

                                // Code
                                SortText(
                                    text = highlightCode(implementation.code),
                                    style = SortTheme.typography.bodyMedium.copy(
                                        fontFamily = FontFamily.Monospace,
                                        lineHeight = SortTheme.typography.bodyMedium.lineHeight * 1.2
                                    ),
                                    color = SortTheme.colorScheme.onCodeContainer,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun highlightCode(code: String): AnnotatedString {
    val keywords = listOf("fun", "val", "var", "for", "while", "if", "else", "return", "break", "continue", "true", "false", "null", "class", "object", "package", "import", "data")
    val types = listOf("Int", "IntArray", "String", "Boolean", "Unit", "List", "Array")
    
    return buildAnnotatedString {
        val lines = code.lines()
        lines.forEachIndexed { index, line ->
            val words = line.split(Regex("(?<=\\W)|(?=\\W)"))
            
            words.forEach { word ->
                when {
                    word in keywords -> {
                        withStyle(style = SpanStyle(color = ColorTokens.SyntaxKeyword)) {
                            append(word)
                        }
                    }
                    word in types -> {
                         withStyle(style = SpanStyle(color = ColorTokens.SyntaxType)) {
                            append(word)
                        }
                    }
                    word.startsWith("//") -> {
                         withStyle(style = SpanStyle(color = ColorTokens.SyntaxComment)) {
                            append(word)
                        }
                    }
                    word.matches(Regex("\\d+")) -> {
                         withStyle(style = SpanStyle(color = ColorTokens.SyntaxNumber)) {
                            append(word)
                        }
                    }
                    else -> append(word)
                }
            }
            
            if (index < lines.size - 1) {
                append("\n")
            }
        }
    }
}
