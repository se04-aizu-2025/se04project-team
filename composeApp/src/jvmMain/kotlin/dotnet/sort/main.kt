package dotnet.sort

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dotnet.sort.di.DataModule
import dotnet.sort.di.dataPlatformModule
import dotnet.sort.di.DomainModule
import dotnet.sort.presentation.common.di.CommonModule
import dotnet.sort.presentation.feature.home.di.HomeFeatureModule
import dotnet.sort.presentation.feature.learn.di.LearnFeatureModule
import dotnet.sort.presentation.feature.compare.di.CompareFeatureModule
import dotnet.sort.presentation.feature.quiz.di.QuizFeatureModule
import dotnet.sort.presentation.feature.settings.di.SettingsFeatureModule
import dotnet.sort.presentation.feature.sort.SortFeatureModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

fun main() {
    startKoin {
        modules(
            DataModule().module,
            dataPlatformModule,
            DomainModule().module,
            CommonModule().module,
            SettingsFeatureModule().module,
            SortFeatureModule().module,
            HomeFeatureModule().module,
            LearnFeatureModule().module,
            CompareFeatureModule().module,
            QuizFeatureModule().module,
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
