package dotnet.sort.repository

import dotnet.sort.domain.model.SortType

internal fun SortType.toDbValue(): String = name

internal fun sortTypeFromDb(value: String): SortType = SortType.valueOf(value)
