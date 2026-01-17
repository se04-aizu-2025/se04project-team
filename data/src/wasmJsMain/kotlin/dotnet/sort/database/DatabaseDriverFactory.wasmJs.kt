package dotnet.sort.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.koin.core.annotation.Single
import org.w3c.dom.Worker

private const val SqlJsWorkerPath = "sqljs.worker.js"

@Single
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val worker = Worker(SqlJsWorkerPath)
        return WebWorkerDriver(worker)
    }
}
