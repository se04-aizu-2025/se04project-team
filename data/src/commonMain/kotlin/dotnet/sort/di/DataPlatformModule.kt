package dotnet.sort.di

import dotnet.sort.database.DatabaseDriverFactory
import org.koin.dsl.module

expect fun createDatabaseDriverFactory(): DatabaseDriverFactory

val dataPlatformModule = module {
    single { createDatabaseDriverFactory() }
}
