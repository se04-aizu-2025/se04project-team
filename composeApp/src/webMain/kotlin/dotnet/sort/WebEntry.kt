package dotnet.sort

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dotnet.sort.di.DataModule
import dotnet.sort.di.platformDataModule
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

@OptIn(ExperimentalComposeUiApi::class)
fun runWebApp() {
    startKoin {
        modules(
            DataModule().module,
            platformDataModule,
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

    ComposeViewport {
        App()
    }
}
