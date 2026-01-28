package dotnet.sort.model

data class QuizScoreEntry(
    val difficulty: QuizDifficulty,
    val mode: QuizMode,
    val score: Int,
    val correctCount: Int,
    val totalQuestions: Int,
    val createdAtMillis: Long,
)
