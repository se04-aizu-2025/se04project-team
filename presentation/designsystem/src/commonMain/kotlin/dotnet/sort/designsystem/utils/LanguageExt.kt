package dotnet.sort.designsystem.utils

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.language_english
import dotnet.sort.designsystem.generated.resources.language_japanese
import dotnet.sort.domain.model.Language
import org.jetbrains.compose.resources.StringResource

fun Language.toDisplayName(): StringResource {
    return when (this) {
        Language.ENGLISH -> Res.string.language_english
        Language.JAPANESE -> Res.string.language_japanese
    }
}
