package dotnet.sort.storage

/**
 * Simple key-value storage for user settings.
 */
@OptIn(ExperimentalMultiplatform::class)
expect class SettingsStorage() {
    fun getBoolean(key: String, default: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getFloat(key: String, default: Float): Float
    fun putFloat(key: String, value: Float)
    fun getString(key: String, default: String): String
    fun putString(key: String, value: String)
}
