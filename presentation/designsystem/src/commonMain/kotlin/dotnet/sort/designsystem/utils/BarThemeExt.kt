package dotnet.sort.designsystem.utils

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.theme_forest
import dotnet.sort.designsystem.generated.resources.theme_kotlin
import dotnet.sort.designsystem.generated.resources.theme_ocean
import dotnet.sort.domain.model.BarColorTheme
import org.jetbrains.compose.resources.StringResource

fun BarColorTheme.toDisplayName(): StringResource {
    return when (this) {
        BarColorTheme.KOTLIN -> Res.string.theme_kotlin
        BarColorTheme.OCEAN -> Res.string.theme_ocean
        BarColorTheme.FOREST -> Res.string.theme_forest
    }
}
