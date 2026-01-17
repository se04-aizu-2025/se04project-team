package dotnet.sort.database

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.annotation.Single

@Single
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
