package dotnet.sort.presentation.common.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dotnet.sort.domain.model.Language

val LocalAppLanguage = staticCompositionLocalOf { Language.ENGLISH }

@Composable
fun ProvideAppLanguage(
    language: Language,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAppLanguage provides language) {
        content()
    }
}
