package dotnet.sort

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dotnet.sort.di.DataModule
import dotnet.sort.di.DomainModule
import dotnet.sort.di.PresentationModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(
            DataModule().module,
            DomainModule().module,
            PresentationModule().module,
        )
    }

    ComposeViewport {
        App()
    }
}
