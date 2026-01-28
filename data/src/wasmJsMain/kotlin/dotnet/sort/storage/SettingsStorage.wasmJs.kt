package dotnet.sort.storage

import kotlinx.browser.window

actual class SettingsStorage actual constructor() {
    private val storage = window.localStorage

    actual fun getBoolean(key: String, default: Boolean): Boolean {
        val value = storage.getItem(key) ?: return default
        return value.toBooleanStrictOrNull() ?: default
    }

    actual fun putBoolean(key: String, value: Boolean) {
        storage.setItem(key, value.toString())
    }

    actual fun getFloat(key: String, default: Float): Float {
        val value = storage.getItem(key) ?: return default
        return value.toFloatOrNull() ?: default
    }

    actual fun putFloat(key: String, value: Float) {
        storage.setItem(key, value.toString())
    }

    actual fun getString(key: String, default: String): String =
        storage.getItem(key) ?: default

    actual fun putString(key: String, value: String) {
        storage.setItem(key, value)
    }
}
