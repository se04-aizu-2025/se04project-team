package dotnet.sort.presentation.feature.compare

import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.Intent
import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import org.koin.core.annotation.Factory

data class CompareState(
    val leftAlgorithm: SortType = SortType.BUBBLE,
    val rightAlgorithm: SortType = SortType.QUICK,
    val generatorType: ArrayGeneratorType = ArrayGeneratorType.RANDOM,
    val baseArray: List<Int> = emptyList(),
    val leftResult: SortResult? = null,
    val rightResult: SortResult? = null,
    val compareResult: CompareResult? = null,
    val leftCurrentNumbers: List<Int> = emptyList(),
    val rightCurrentNumbers: List<Int> = emptyList(),
    val leftHighlightIndices: List<Int> = emptyList(),
    val rightHighlightIndices: List<Int> = emptyList(),
    val leftStepIndex: Int = 0,
    val rightStepIndex: Int = 0,
    val isRunning: Boolean = false,
) : UiState

data class CompareResult(
    val left: SortResult,
    val right: SortResult,
)

sealed class CompareIntent : Intent {
    data class SelectLeftAlgorithm(val type: SortType) : CompareIntent()
    data class SelectRightAlgorithm(val type: SortType) : CompareIntent()
    data class SelectInputPattern(val type: ArrayGeneratorType) : CompareIntent()
    data object SwapAlgorithms : CompareIntent()
    data object StartComparison : CompareIntent()
    data object StepForward : CompareIntent()
    data object StepBackward : CompareIntent()
}

@Factory
class CompareViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val generateArrayUseCase: GenerateArrayUseCase,
) : BaseViewModel<CompareState, CompareIntent>(CompareState()) {
    override fun send(intent: CompareIntent) {
        when (intent) {
            is CompareIntent.SelectLeftAlgorithm -> updateState { copy(leftAlgorithm = intent.type) }
            is CompareIntent.SelectRightAlgorithm -> updateState { copy(rightAlgorithm = intent.type) }
            is CompareIntent.SelectInputPattern -> updateState { copy(generatorType = intent.type) }
            CompareIntent.SwapAlgorithms -> updateState {
                copy(leftAlgorithm = rightAlgorithm, rightAlgorithm = leftAlgorithm)
            }
            CompareIntent.StartComparison -> startComparison()
            CompareIntent.StepForward -> stepForward()
            CompareIntent.StepBackward -> stepBackward()
        }
    }

    private fun startComparison() {
        execute {
            updateState { copy(isRunning = true) }
            val array = generateArrayUseCase(state.value.baseArraySize(), state.value.generatorType)
            val leftResult = executeSortUseCase.execute(state.value.leftAlgorithm, array)
            val rightResult = executeSortUseCase.execute(state.value.rightAlgorithm, array)

            updateState {
                copy(
                    isRunning = false,
                    baseArray = array,
                    leftResult = leftResult,
                    rightResult = rightResult,
                    compareResult = CompareResult(leftResult, rightResult),
                    leftStepIndex = 0,
                    rightStepIndex = 0,
                    leftCurrentNumbers = leftResult.steps.firstOrNull()?.arrayState ?: array,
                    rightCurrentNumbers = rightResult.steps.firstOrNull()?.arrayState ?: array,
                    leftHighlightIndices = leftResult.steps.firstOrNull()?.highlightingIndices ?: emptyList(),
                    rightHighlightIndices = rightResult.steps.firstOrNull()?.highlightingIndices ?: emptyList(),
                )
            }
        }
    }

    private fun stepForward() {
        val state = state.value
        val leftResult = state.leftResult ?: return
        val rightResult = state.rightResult ?: return
        val leftNext = (state.leftStepIndex + 1).coerceAtMost(leftResult.steps.lastIndex)
        val rightNext = (state.rightStepIndex + 1).coerceAtMost(rightResult.steps.lastIndex)
        updateStep(leftNext, rightNext)
    }

    private fun stepBackward() {
        val state = state.value
        val leftPrev = (state.leftStepIndex - 1).coerceAtLeast(0)
        val rightPrev = (state.rightStepIndex - 1).coerceAtLeast(0)
        updateStep(leftPrev, rightPrev)
    }

    private fun updateStep(leftIndex: Int, rightIndex: Int) {
        val state = state.value
        val leftResult = state.leftResult ?: return
        val rightResult = state.rightResult ?: return
        val leftStep = leftResult.steps.getOrNull(leftIndex)
        val rightStep = rightResult.steps.getOrNull(rightIndex)
        updateState {
            copy(
                leftStepIndex = leftIndex,
                rightStepIndex = rightIndex,
                leftCurrentNumbers = leftStep?.arrayState ?: leftCurrentNumbers,
                rightCurrentNumbers = rightStep?.arrayState ?: rightCurrentNumbers,
                leftHighlightIndices = leftStep?.highlightingIndices ?: leftHighlightIndices,
                rightHighlightIndices = rightStep?.highlightingIndices ?: rightHighlightIndices,
            )
        }
    }
}

private fun CompareState.baseArraySize(): Int {
    return 50
}
