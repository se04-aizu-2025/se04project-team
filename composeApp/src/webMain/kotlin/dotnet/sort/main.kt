package dotnet.sort

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import dotnet.sort.di.DataModule
import dotnet.sort.di.DomainModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(
            DataModule().module,
            DomainModule().module,
            // TODO: NavigationModule を追加
        )
    }

    ComposeViewport {
        // TODO: Navigation から App Composable を取得
        AppPlaceholder()
    }
}

@Composable
fun AppPlaceholder() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("DNSort - Coming Soon")
        }
    }
}
