package dotnet.sort.domain.quiz.usecase

import org.koin.core.annotation.Factory
import kotlin.math.min

/**
 * クイズのスコアを計算するユースケース。
 */
@Factory
class CalculateQuizScoreUseCase {

    /**
     * スコアの増分を計算します。
     *
     * @param isCorrect 正解かどうか
     * @param timeLeftSeconds 残り時間（秒）
     * @param currentCombo 現在のコンボ数（今回の正解を含まない、前回の連続正解数）
     * @return 獲得スコア
     */
    operator fun invoke(isCorrect: Boolean, timeLeftSeconds: Int, currentCombo: Int): Int {
        if (!isCorrect) return 0

        val baseScore = 10
        // 残り時間 * 2 (最大20)
        val speedBonus = (timeLeftSeconds * 2).coerceAtMost(20)
        
        // 今回の正解でコンボが1増える
        val newCombo = currentCombo + 1
        // コンボ数 * 5 (最大25)
        val comboBonus = (min(newCombo, 5) * 5).coerceAtMost(25)

        return baseScore + speedBonus + comboBonus
    }
}
