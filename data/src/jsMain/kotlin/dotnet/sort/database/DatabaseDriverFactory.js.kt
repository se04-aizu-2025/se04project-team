package dotnet.sort.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.koin.core.annotation.Single
import org.w3c.dom.Worker

@Single
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val worker =
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""),
            )
        return WebWorkerDriver(worker)
    }
}
