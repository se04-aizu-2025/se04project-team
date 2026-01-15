package dotnet.sort.presentation.common.di

import dotnet.sort.presentation.common.viewmodel.ThemeViewModel
import org.koin.dsl.module

val CommonModule = module {
    factory { ThemeViewModel(get()) }
}
