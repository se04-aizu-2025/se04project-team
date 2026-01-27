package dotnet.sort.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes for the application.
 */
sealed interface Screen {
    
    @Serializable
    data object Home : Screen

    @Serializable
    data object Sort : Screen

    @Serializable
    data object Settings : Screen

    @Serializable
    data object Learn : Screen

    @Serializable
    data class LearnDetail(val sortTypeName: String) : Screen

    @Serializable
    data object Compare : Screen

    @Serializable
    data object Quiz : Screen
}
