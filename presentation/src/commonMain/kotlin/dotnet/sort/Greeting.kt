package dotnet.sort

/**
 * Greeting class that provides platform-specific greeting messages.
 */
class Greeting {
    fun greet(): String {
        return "Hello, ${platform()}!"
    }
}
