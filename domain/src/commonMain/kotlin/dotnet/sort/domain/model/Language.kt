package dotnet.sort.domain.model

/**
 * Supported application languages.
 */
enum class Language(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    JAPANESE("ja", "日本語"),
    ;

    companion object {
        fun fromCode(code: String): Language {
            return entries.firstOrNull { it.code == code } ?: ENGLISH
        }
    }
}
