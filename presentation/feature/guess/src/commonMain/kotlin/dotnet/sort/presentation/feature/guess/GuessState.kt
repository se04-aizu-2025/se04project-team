package dotnet.sort.presentation.feature.guess

import dotnet.sort.presentation.common.viewmodel.UiState
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.model.SortSnapshot

/**
 * Guessゲーム画面のUI状態。
 *
 * @property isLoading ローディング状態
 * @property currentArray 現在の配列状態
 * @property highlightIndices ハイライトするインデックス
 * @property availableAlgorithms 選択可能なアルゴリズムリスト
 * @property selectedAlgorithm ユーザーが選択したアルゴリズム
 * @property correctAlgorithm 正解のアルゴリズム
 * @property gamePhase ゲームの現在のフェーズ
 * @property hint ヒント情報
 * @property score 現在のスコア
 * @property isAnimationPlaying アニメーション再生中かどうか
 */
data class GuessState(
    val isLoading: Boolean = false,
    val currentArray: List<Int> = emptyList(),
    val highlightIndices: List<Int> = emptyList(),
    val availableAlgorithms: List<SortType> = emptyList(),
    val selectedAlgorithm: SortType? = null,
    val correctAlgorithm: SortType? = null,
    val gamePhase: GuessGamePhase = GuessGamePhase.WAITING,
    val hint: GuessHint? = null,
    val score: Int = 0,
    val isAnimationPlaying: Boolean = false,
    val currentStepIndex: Int = 0,
    val totalSteps: Int = 0,
    val currentSnapshot: SortSnapshot? = null
) : UiState

/**
 * ゲームのフェーズ
 */
enum class GuessGamePhase {
    /** ゲーム開始待ち */
    WAITING,

    /** アニメーション再生中 */
    ANIMATING,

    /** 回答選択中 */
    SELECTING,

    /** 正解/不正解表示中 */
    RESULT,

    /** ゲーム終了 */
    FINISHED
}

/**
 * ヒント情報
 */
data class GuessHint(
    val timeComplexity: String,
    val spaceComplexity: String,
    val description: String
)