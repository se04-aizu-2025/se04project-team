package dotnet.sort

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dotnet.sort.di.DataModule
import dotnet.sort.di.DomainModule
import dotnet.sort.presentation.feature.sort.SortFeatureModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

fun main() {
    startKoin {
        modules(
            DataModule().module,
            DomainModule().module,
            SortFeatureModule().module,
            // TODO: NavigationModule を追加
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DNSort",
        ) {
            App() // Common Entry Point
        }
    }
}

