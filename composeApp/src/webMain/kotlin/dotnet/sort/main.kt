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
import dotnet.sort.di.platformDataModule
import dotnet.sort.presentation.common.di.CommonModule
import dotnet.sort.presentation.feature.home.di.HomeFeatureModule
import dotnet.sort.presentation.feature.settings.di.SettingsFeatureModule
import dotnet.sort.presentation.feature.sort.SortFeatureModule
import dotnet.sort.presentation.feature.learn.di.LearnFeatureModule
import dotnet.sort.presentation.feature.compare.di.CompareFeatureModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(
            DataModule().module,
            DomainModule().module,
            CommonModule,
            SettingsFeatureModule().module,
            SortFeatureModule().module,
            HomeFeatureModule().module,
            LearnFeatureModule().module,
            CompareFeatureModule().module,
            platformDataModule,
            // TODO: NavigationModule を追加
        )
    }

    ComposeViewport {
        App() // Common Entry Point
    }
}
