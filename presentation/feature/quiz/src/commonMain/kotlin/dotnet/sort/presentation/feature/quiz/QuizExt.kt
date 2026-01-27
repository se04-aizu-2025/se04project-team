package dotnet.sort.presentation.feature.quiz

import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.quiz_diff_easy
import dotnet.sort.designsystem.generated.resources.quiz_diff_hard
import dotnet.sort.designsystem.generated.resources.quiz_diff_medium
import dotnet.sort.designsystem.generated.resources.quiz_mode_guess_algo
import dotnet.sort.designsystem.generated.resources.quiz_mode_speed_swap
import org.jetbrains.compose.resources.StringResource

fun QuizMode.toDisplayName(): StringResource {
    return when (this) {
        QuizMode.SPEED_SWAP -> Res.string.quiz_mode_speed_swap
        QuizMode.GUESS_ALGORITHM -> Res.string.quiz_mode_guess_algo
    }
}

fun QuizDifficulty.toDisplayName(): StringResource {
    return when (this) {
        QuizDifficulty.EASY -> Res.string.quiz_diff_easy
        QuizDifficulty.MEDIUM -> Res.string.quiz_diff_medium
        QuizDifficulty.HARD -> Res.string.quiz_diff_hard
    }
}
