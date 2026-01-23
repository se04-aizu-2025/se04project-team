package dotnet.sort.di

import dotnet.sort.database.DatabaseDriverFactory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module

@Module
class WasmJsDataModule {
    @Single
    fun provideDatabaseDriverFactory(): DatabaseDriverFactory = DatabaseDriverFactory()
}

actual val platformDataModule = WasmJsDataModule().module
