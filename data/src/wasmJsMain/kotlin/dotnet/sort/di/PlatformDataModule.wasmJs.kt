package dotnet.sort.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module
@ComponentScan("dotnet.sort.database")
class WasmJsDataModule

actual val platformDataModule = WasmJsDataModule().module
