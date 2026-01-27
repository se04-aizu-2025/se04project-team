package dotnet.sort.repository

import dotnet.sort.model.QuizDifficulty

internal fun QuizDifficulty.toDbValue(): String = name

internal fun quizDifficultyFromDb(value: String): QuizDifficulty = QuizDifficulty.valueOf(value)
