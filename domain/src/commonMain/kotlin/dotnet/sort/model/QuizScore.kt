package dotnet.sort.model

/**
 * クイズ成績データ。
 */
data class QuizScore(
    val id: Long? = null,
    val correctCount: Int,
    val incorrectCount: Int,
    val longestStreak: Int,
    val score: Int,
    val durationMillis: Long,
    val difficulty: QuizDifficulty,
    val mode: QuizMode,
    val algorithmType: SortType?,
    val quizVersion: String,
    val createdAtMillis: Long,
)

/**
 * クイズ難易度。
 */
enum class QuizDifficulty {
    EASY,
    MEDIUM,
    HARD,
}

/**
 * クイズモード。
 */
enum class QuizMode {
    SPEED_SWAP,
    GUESS_ALGORITHM,
}

/**
 * スコア推移の表示期間。
 */
enum class ScorePeriod {
    /** 直近24時間 */
    DAILY,

    /** 直近7日間 */
    WEEKLY,

    /** 全期間 */
    ALL,
}
