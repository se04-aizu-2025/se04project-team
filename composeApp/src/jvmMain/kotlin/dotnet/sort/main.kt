package dotnet.sort

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dotnet.sort.di.DataModule
import dotnet.sort.di.DomainModule
import dotnet.sort.di.PresentationModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

fun main() {
    startKoin {
        modules(
            DataModule().module,
            DomainModule().module,
            PresentationModule().module,
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "dotnet",
        ) {
            App()
        }
    }
}
