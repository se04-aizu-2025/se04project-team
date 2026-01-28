package dotnet.sort.presentation.feature.quiz

import dotnet.sort.model.QuizDifficulty
import dotnet.sort.model.QuizMode
import dotnet.sort.model.ScorePeriod
import dotnet.sort.presentation.common.viewmodel.Intent

sealed class QuizIntent : Intent {
    data class SelectMode(val mode: QuizMode) : QuizIntent()
    data class SelectDifficulty(val difficulty: QuizDifficulty) : QuizIntent()
    data class SelectOption(val index: Int) : QuizIntent()
    data object StartQuiz : QuizIntent()
    data object SubmitAnswer : QuizIntent()
    data object NextQuestion : QuizIntent()
    data object ToggleHint : QuizIntent()
    data class SelectScorePeriod(val period: ScorePeriod) : QuizIntent()
}
