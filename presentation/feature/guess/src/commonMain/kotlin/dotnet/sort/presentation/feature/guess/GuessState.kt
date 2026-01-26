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
 * @property difficulty 難易度設定
 * @property timeLeftSeconds 残り時間（秒）
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
    val currentSnapshot: SortSnapshot? = null,
    val difficulty: GuessDifficulty = GuessDifficulty.MEDIUM,
    val timeLeftSeconds: Int = GuessDifficulty.MEDIUM.timeLimitSeconds
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
 * Guessゲームの難易度。
 *
 * @property displayName 表示名
 * @property arraySize 問題で使用する配列サイズ
 * @property timeLimitSeconds 制限時間（秒）
 */
enum class GuessDifficulty(
    val displayName: String,
    val arraySize: Int,
    val timeLimitSeconds: Int
) {
    /** 初級: 小さい配列・制限時間長め */
    EASY("Easy", arraySize = 10, timeLimitSeconds = 25),

    /** 中級: 中程度の配列 */
    MEDIUM("Medium", arraySize = 20, timeLimitSeconds = 15),

    /** 上級: 大きい配列・制限時間短め */
    HARD("Hard", arraySize = 30, timeLimitSeconds = 8)
}

/**
 * ヒント情報
 */
data class GuessHint(
    val timeComplexity: String,
    val spaceComplexity: String,
    val description: String
)