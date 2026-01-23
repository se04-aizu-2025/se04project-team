package dotnet.sort.repository

import dotnet.sort.domain.model.HistoryEventType

internal fun HistoryEventType.toDbValue(): String = name

internal fun historyEventTypeFromDb(value: String): HistoryEventType = HistoryEventType.valueOf(value)
