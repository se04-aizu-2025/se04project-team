package dotnet.sort.presentation.feature.learn

import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import org.koin.core.annotation.Factory

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.StringResource

data class LearnAlgorithmItem(
    val type: SortType,
    val description: StringResource,
    val icon: String,
)

data class LearnState(
    val algorithms: List<LearnAlgorithmItem> = emptyList(),
    val details: Map<SortType, LearnAlgorithmDetail> = emptyMap(),
    val selectedAlgorithm: SortType? = null,
) : UiState

sealed class LearnIntent : Intent {
    data class SelectAlgorithm(val type: SortType) : LearnIntent()
}

@Factory
class LearnViewModel : BaseViewModel<LearnState, LearnIntent>(
    LearnState(
        algorithms = buildAlgorithmItems(),
        details = buildAlgorithmDetails(),
    ),
) {
    override fun send(intent: LearnIntent) {
        when (intent) {
            is LearnIntent.SelectAlgorithm -> {
                updateState { copy(selectedAlgorithm = intent.type) }
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
        SortType.BUBBLE to "🫧",
        SortType.SELECTION to "🎯",
        SortType.INSERTION to "🧩",
        SortType.SHELL to "🐚",
        SortType.MERGE to "🔀",
        SortType.QUICK to "⚡",
        SortType.HEAP to "⛰️",
    )
    return SortType.entries.mapNotNull { type ->
        val desc = descriptions[type]
        if (desc != null) {
            LearnAlgorithmItem(
                type = type,
                description = desc,
                icon = icons[type] ?: "📘",
            )
        } else {
            null
        }
    }
}
