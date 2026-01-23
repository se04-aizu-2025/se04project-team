package dotnet.sort.domain.quiz.model

import dotnet.sort.domain.model.SortType

/**
 * クイズの問題を表すデータクラス。
 *
 * @property id 問題の一意なID
 * @property algorithmType 出題されるアルゴリズムの種類
 * @property currentArray スワップ前の配列状態
 * @property correctIndices 次に交換されるべき2つのインデックス (昇順であることが望ましい)
 * @property timeLimitSeconds 制限時間（秒）
 */
data class QuizQuestion(
    val id: String,
    val algorithmType: SortType,
    val currentArray: List<Int>,
    val correctIndices: Pair<Int, Int>,
    val timeLimitSeconds: Int = 10
)
