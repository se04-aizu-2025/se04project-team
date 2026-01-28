package dotnet.sort.presentation.feature.learn

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
