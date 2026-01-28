package dotnet.sort.domain.model

/**
 * Bar color theme presets for visualization.
 */
enum class BarColorTheme(val code: String, val displayName: String) {
    KOTLIN("kotlin", "Kotlin"),
    OCEAN("ocean", "Ocean"),
    FOREST("forest", "Forest"),
    ;

    companion object {
        fun fromCode(code: String): BarColorTheme {
            return entries.firstOrNull { it.code == code } ?: KOTLIN
        }
    }
}
