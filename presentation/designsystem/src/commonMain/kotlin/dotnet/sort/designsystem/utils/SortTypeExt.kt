package dotnet.sort.designsystem.utils

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.algo_bitonic
import dotnet.sort.designsystem.generated.resources.algo_bogo
import dotnet.sort.designsystem.generated.resources.algo_bucket
import dotnet.sort.designsystem.generated.resources.algo_bubble
import dotnet.sort.designsystem.generated.resources.algo_cocktail
import dotnet.sort.designsystem.generated.resources.algo_comb
import dotnet.sort.designsystem.generated.resources.algo_counting
import dotnet.sort.designsystem.generated.resources.algo_gnome
import dotnet.sort.designsystem.generated.resources.algo_heap
import dotnet.sort.designsystem.generated.resources.algo_insertion
import dotnet.sort.designsystem.generated.resources.algo_merge
import dotnet.sort.designsystem.generated.resources.algo_oddeven
import dotnet.sort.designsystem.generated.resources.algo_quick
import dotnet.sort.designsystem.generated.resources.algo_radix
import dotnet.sort.designsystem.generated.resources.algo_selection
import dotnet.sort.designsystem.generated.resources.algo_shell
import dotnet.sort.designsystem.generated.resources.algo_tim
import dotnet.sort.model.SortType
import org.jetbrains.compose.resources.StringResource

fun SortType.toDisplayName(): StringResource {
    return when (this) {
        SortType.BUBBLE -> Res.string.algo_bubble
        SortType.SELECTION -> Res.string.algo_selection
        SortType.INSERTION -> Res.string.algo_insertion
        SortType.SHELL -> Res.string.algo_shell
        SortType.MERGE -> Res.string.algo_merge
        SortType.QUICK -> Res.string.algo_quick
        SortType.HEAP -> Res.string.algo_heap
        SortType.COUNTING -> Res.string.algo_counting
        SortType.RADIX -> Res.string.algo_radix
        SortType.BUCKET -> Res.string.algo_bucket
        SortType.TIM -> Res.string.algo_tim
        SortType.COMB -> Res.string.algo_comb
        SortType.COCKTAIL -> Res.string.algo_cocktail
        SortType.GNOME -> Res.string.algo_gnome
        SortType.ODD_EVEN -> Res.string.algo_oddeven
        SortType.BOGO -> Res.string.algo_bogo
        SortType.BITONIC -> Res.string.algo_bitonic
    }
}
