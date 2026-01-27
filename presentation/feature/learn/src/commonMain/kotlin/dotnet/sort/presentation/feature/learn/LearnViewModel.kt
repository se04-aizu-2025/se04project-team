package dotnet.sort.presentation.feature.learn

import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import org.koin.core.annotation.Factory

data class LearnAlgorithmItem(
    val type: SortType,
    val title: String,
    val description: String,
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
        SortType.BUBBLE to "Adjacent swaps, simple but slow.",
        SortType.SELECTION to "Find min and place it.",
        SortType.INSERTION to "Build sorted prefix gradually.",
        SortType.SHELL to "Gap-based insertion optimization.",
        SortType.MERGE to "Divide and merge efficiently.",
        SortType.QUICK to "Partition around a pivot.",
        SortType.HEAP to "Heap-based selection of max/min.",
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
    return SortType.entries.map { type ->
        LearnAlgorithmItem(
            type = type,
            title = type.displayName,
            description = descriptions[type] ?: "",
            icon = icons[type] ?: "📘",
        )
    }
}
