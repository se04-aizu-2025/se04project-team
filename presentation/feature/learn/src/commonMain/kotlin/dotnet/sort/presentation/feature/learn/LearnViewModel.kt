package dotnet.sort.presentation.feature.learn

import dotnet.sort.designsystem.components.atoms.SortIcons
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.learn_desc_bubble
import dotnet.sort.designsystem.generated.resources.learn_desc_heap
import dotnet.sort.designsystem.generated.resources.learn_desc_insertion
import dotnet.sort.designsystem.generated.resources.learn_desc_merge
import dotnet.sort.designsystem.generated.resources.learn_desc_quick
import dotnet.sort.designsystem.generated.resources.learn_desc_selection
import dotnet.sort.designsystem.generated.resources.learn_desc_shell
import dotnet.sort.designsystem.utils.toDisplayName
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import org.koin.core.annotation.Factory

@Factory
class LearnViewModel : BaseViewModel<LearnState, LearnIntent>(
    LearnState(
        algorithms = buildAlgorithmItems(),
    ),
) {
    override fun send(intent: LearnIntent) {
        when (intent) {
            is LearnIntent.SelectAlgorithm -> {
                updateState { copy(navigationTarget = intent.sortType) }
            }
            is LearnIntent.ConsumeNavigation -> {
                updateState { copy(navigationTarget = null) }
            }
        }
    }
}

private fun buildAlgorithmItems(): List<LearnAlgorithmItem> {
    val descriptions = mapOf(
        SortType.BUBBLE to Res.string.learn_desc_bubble,
        SortType.SELECTION to Res.string.learn_desc_selection,
        SortType.INSERTION to Res.string.learn_desc_insertion,
        SortType.SHELL to Res.string.learn_desc_shell,
        SortType.MERGE to Res.string.learn_desc_merge,
        SortType.QUICK to Res.string.learn_desc_quick,
        SortType.HEAP to Res.string.learn_desc_heap,
    )
    val icons = mapOf(
        SortType.BUBBLE to SortIcons.Sort,
        SortType.SELECTION to SortIcons.Sort,
        SortType.INSERTION to SortIcons.Sort,
        SortType.SHELL to SortIcons.Sort,
        SortType.MERGE to SortIcons.Sort,
        SortType.QUICK to SortIcons.Visualizer,
        SortType.HEAP to SortIcons.Sort,
    )
    // Filter to only supported algorithms in Learn
    val supportedTypes = descriptions.keys
    
    return supportedTypes.map { type ->
        LearnAlgorithmItem(
            type = type,
            title = type.toDisplayName(),
            description = descriptions[type] ?: Res.string.learn_desc_bubble,
            icon = icons[type] ?: SortIcons.Learn,
        )
    }
}
