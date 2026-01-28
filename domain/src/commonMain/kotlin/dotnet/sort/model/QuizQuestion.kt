package dotnet.sort.model

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val algorithmType: SortType? = null,
    val hint: String? = null,
)
