package dotnet.sort.repository

import dotnet.sort.model.QuizMode

internal fun QuizMode.toDbValue(): String = name

internal fun quizModeFromDb(value: String): QuizMode = QuizMode.valueOf(value)
