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
    data object LearnDetail : Screen

    @Serializable
    data object Compare : Screen

    @Serializable
    @Serializable
    data object Quiz : Screen

    @Serializable
    data class AlgorithmDetail(val sortTypeString: String) : Screen
}
