package dotnet.sort.presentation.common.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dotnet.sort.domain.model.Language

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key

val LocalAppLanguage = staticCompositionLocalOf { Language.ENGLISH }

@Composable
fun ProvideAppLanguage(
    language: Language,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(language) {
        updateSystemLocale(language)
    }

    key(language) {
        CompositionLocalProvider(LocalAppLanguage provides language) {
            content()
        }
    }
}
