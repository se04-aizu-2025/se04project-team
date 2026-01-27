package dotnet.sort.storage

import java.util.prefs.Preferences

actual class SettingsStorage actual constructor() {
    private val preferences = Preferences.userRoot().node("dotnet.sort.settings")

    actual fun getBoolean(key: String, default: Boolean): Boolean =
        preferences.getBoolean(key, default)

    actual fun putBoolean(key: String, value: Boolean) {
        preferences.putBoolean(key, value)
    }

    actual fun getFloat(key: String, default: Float): Float {
        val raw = preferences.get(key, default.toString())
        return raw.toFloatOrNull() ?: default
    }

    actual fun putFloat(key: String, value: Float) {
        preferences.put(key, value.toString())
    }

    actual fun getString(key: String, default: String): String =
        preferences.get(key, default)

    actual fun putString(key: String, value: String) {
        preferences.put(key, value)
    }
}
