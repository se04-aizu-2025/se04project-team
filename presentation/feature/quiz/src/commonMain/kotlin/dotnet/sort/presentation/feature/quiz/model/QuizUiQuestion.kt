package dotnet.sort.presentation.feature.quiz.model

import dotnet.sort.model.SortType
import org.jetbrains.compose.resources.StringResource

data class QuizUiQuestion(
    val prompt: StringResource,
    val options: List<StringResource>,
    val correctIndex: Int,
    val algorithmType: SortType? = null,
    val hint: StringResource? = null,
)
