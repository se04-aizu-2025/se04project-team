package dotnet.sort.repository

import dotnet.sort.model.SortType

internal fun SortType.toDbValue(): String = name

internal fun sortTypeFromDb(value: String): SortType = SortType.valueOf(value)
