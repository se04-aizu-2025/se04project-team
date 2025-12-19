package dotnet.sort

import org.koin.core.annotation.Single

/**
 * Greeting class that provides platform-specific greeting messages.
 */
@Single
class Greeting {
    fun greet(): String {
        return "Hello, ${platform()}!"
    }
}
