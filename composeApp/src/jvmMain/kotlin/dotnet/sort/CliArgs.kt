package dotnet.sort

data class CliArgs(
    val algorithm: String? = null,
    val size: Int? = null,
    val showHelp: Boolean = false,
    val showVerbose: Boolean = false
)

class CliParser {
    fun parse(args: Array<String>): CliArgs {
        var algorithm: String? = null
        var size: Int? = null
        var showHelp = false
        var showVerbose = false

        var i = 0
        while (i < args.size) {
            when (val arg = args[i]) {
                "--help", "-h" -> showHelp = true
                "--verbose", "-v" -> showVerbose = true
                "--algorithm", "-a" -> {
                    if (i + 1 < args.size) {
                        algorithm = args[i + 1]
                        i++
                    } else {
                        println("Error: Missing value for $arg")
                        return CliArgs(showHelp = true) // Return help state on error
                    }
                }
                "--size", "-s" -> {
                    if (i + 1 < args.size) {
                        size = args[i + 1].toIntOrNull()
                        if (size == null) {
                            println("Error: Invalid integer value for $arg: ${args[i + 1]}")
                            return CliArgs(showHelp = true)
                        }
                        i++
                    } else {
                        println("Error: Missing value for $arg")
                        return CliArgs(showHelp = true)
                    }
                }
                else -> {
                    println("Error: Unknown argument '$arg'")
                    return CliArgs(showHelp = true)
                }
            }
            i++
        }

        return CliArgs(algorithm, size, showHelp, showVerbose)
    }

    fun printUsage() {
        println("""
            Usage: cli [options]
            
            Options:
              --algorithm, -a <name>   Specify sorting algorithm (e.g., Bubble, Quick)
              --size, -s <int>         Specify array size
              --verbose, -v            Enable verbose output
              --help, -h               Show this help message
        """.trimIndent())
    }
}
