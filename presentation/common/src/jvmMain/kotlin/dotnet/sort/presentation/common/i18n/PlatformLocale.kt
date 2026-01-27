package dotnet.sort.presentation.common.i18n

import dotnet.sort.domain.model.Language
import java.util.Locale

actual fun updateSystemLocale(language: Language) {
    val locale = Locale(language.code)
    Locale.setDefault(locale)
}
