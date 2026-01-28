package dotnet.sort.presentation.common.i18n

import dotnet.sort.domain.model.Language

expect object LocaleManager {
    fun setLocale(language: Language)
}
