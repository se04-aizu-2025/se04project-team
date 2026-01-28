package dotnet.sort.presentation.common.i18n

import dotnet.sort.domain.model.Language

actual object LocaleManager {
    actual fun setLocale(language: Language) {
        kotlinx.browser.document.documentElement?.setAttribute("lang", language.code)
    }
}
