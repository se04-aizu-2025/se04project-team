package dotnet.sort.presentation.feature.guess

import dotnet.sort.domain.model.SortResult
import dotnet.sort.domain.model.SortSnapshot
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.usecase.ExecuteSortUseCase
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import dotnet.sort.presentation.common.viewmodel.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import androidx.lifecycle.viewModelScope
import org.koin.core.annotation.Factory

@Factory
class GuessViewModel(
    private val executeSortUseCase: ExecuteSortUseCase
) : BaseViewModel<GuessState, GuessIntent>(GuessState()) {

    private var animationJob: Job? = null
    private var currentSortSteps: List<SortSnapshot> = emptyList()

    override fun send(intent: GuessIntent) {
        when (intent) {
            is GuessIntent.StartGame -> startGame()
            is GuessIntent.StartAnimation -> startAnimation()
            is GuessIntent.SelectAlgorithm -> selectAlgorithm(intent.algorithm)
            is GuessIntent.ConfirmAnswer -> confirmAnswer()
            is GuessIntent.ShowHint -> showHint()
            is GuessIntent.NextQuestion -> nextQuestion()
            is GuessIntent.EndGame -> endGame()
        }
    }

    private fun startGame() {
        // 利用可能なアルゴリズムを設定
        val availableAlgorithms = SortType.entries.toList().shuffled()

        updateState {
            copy(
                availableAlgorithms = availableAlgorithms,
                gamePhase = GuessGamePhase.WAITING,
                score = 0,
                selectedAlgorithm = null,
                correctAlgorithm = null,
                hint = null,
                isAnimationPlaying = false,
                currentStepIndex = 0,
                totalSteps = 0,
                currentSnapshot = null
            )
        }
    }

    private fun nextQuestion() {
        // ランダムに正解アルゴリズムを選択
        val correctAlgorithm = SortType.entries.random()

        updateState {
            copy(
                correctAlgorithm = correctAlgorithm,
                selectedAlgorithm = null,
                hint = null,
                gamePhase = GuessGamePhase.ANIMATING,
                isAnimationPlaying = false,
                currentStepIndex = 0,
                totalSteps = 0,
                currentSnapshot = null
            )
        }

        // ソート実行
        viewModelScope.launch {
            val result = executeSortUseCase.execute(
                correctAlgorithm,
                (1..20).shuffled() // 小さめの配列で
            )
            currentSortSteps = result.steps
            updateState {
                copy(
                    totalSteps = result.steps.size,
                    currentSnapshot = result.steps.firstOrNull(),
                    currentArray = result.steps.firstOrNull()?.arrayState ?: emptyList(),
                    highlightIndices = result.steps.firstOrNull()?.highlightingIndices ?: emptyList()
                )
            }
        }
    }

    private fun startAnimation() {
        if (currentSortSteps.isEmpty()) return

        updateState { copy(isAnimationPlaying = true, gamePhase = GuessGamePhase.ANIMATING) }

        animationJob?.cancel()
        animationJob = viewModelScope.launch {
            for (i in 0 until currentSortSteps.size) {
                updateState {
                    copy(
                        currentStepIndex = i,
                        currentSnapshot = currentSortSteps[i],
                        currentArray = currentSortSteps[i].arrayState,
                        highlightIndices = currentSortSteps[i].highlightingIndices
                    )
                }
                delay(500) // アニメーション速度
            }

            updateState {
                copy(
                    isAnimationPlaying = false,
                    gamePhase = GuessGamePhase.SELECTING
                )
            }
        }
    }

    private fun selectAlgorithm(algorithm: SortType) {
        updateState { copy(selectedAlgorithm = algorithm) }
    }

    private fun confirmAnswer() {
        val isCorrect = state.value.selectedAlgorithm == state.value.correctAlgorithm
        val newScore = if (isCorrect) state.value.score + 1 else state.value.score

        updateState {
            copy(
                gamePhase = GuessGamePhase.RESULT,
                score = newScore
            )
        }
    }

    private fun showHint() {
        val hint = when (state.value.correctAlgorithm) {
            SortType.BUBBLE -> GuessHint("O(n²)", "O(1)", "隣接する要素を比較して交換するアルゴリズム")
            SortType.SELECTION -> GuessHint("O(n²)", "O(1)", "最小値を見つけて先頭に移動するアルゴリズム")
            SortType.INSERTION -> GuessHint("O(n²)", "O(1)", "要素を適切な位置に挿入するアルゴリズム")
            SortType.MERGE -> GuessHint("O(n log n)", "O(n)", "分割統治法を使用するアルゴリズム")
            SortType.QUICK -> GuessHint("O(n log n)", "O(log n)", "ピボットを使用して分割するアルゴリズム")
            SortType.HEAP -> GuessHint("O(n log n)", "O(1)", "ヒープ構造を使用するアルゴリズム")
            SortType.SHELL -> GuessHint("O(n log² n)", "O(1)", "ギャップを使用した改良版挿入ソート")
            null -> null
        }

        updateState { copy(hint = hint) }
    }

    private fun endGame() {
        updateState {
            copy(
                gamePhase = GuessGamePhase.FINISHED
            )
        }
        animationJob?.cancel()
    }
}