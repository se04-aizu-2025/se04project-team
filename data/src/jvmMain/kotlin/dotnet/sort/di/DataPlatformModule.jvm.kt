package dotnet.sort.di

import dotnet.sort.database.DatabaseDriverFactory

actual fun createDatabaseDriverFactory(): DatabaseDriverFactory = DatabaseDriverFactory()
