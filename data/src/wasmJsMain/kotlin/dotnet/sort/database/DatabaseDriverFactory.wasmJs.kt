package dotnet.sort.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.koin.core.annotation.Single
import org.w3c.dom.Worker

private fun sqlJsWorkerUrl(): String =
    js(
        """new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url).toString()""",
    )

@Single
@OptIn(ExperimentalMultiplatform::class)
actual class DatabaseDriverFactory {
    @OptIn(ExperimentalWasmJsInterop::class)
    actual fun createDriver(): SqlDriver {
        val worker = Worker(sqlJsWorkerUrl())
        return WebWorkerDriver(worker)
    }
}
