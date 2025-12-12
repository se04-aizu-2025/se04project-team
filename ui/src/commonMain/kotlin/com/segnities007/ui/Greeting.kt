package com.segnities007.ui

/**
 * Greeting class that provides platform-specific greeting messages.
 */
class Greeting {
    fun greet(): String {
        return "Hello, ${platform()}!"
    }
}
