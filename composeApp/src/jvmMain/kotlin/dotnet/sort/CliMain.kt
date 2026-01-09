package dotnet.sort

fun main(args: Array<String>) {
    val parser = CliParser()
    val argsToParse = args
    val cliArgs = if (argsToParse.isEmpty()) {
        InteractiveMode().run()
    } else {
        parser.parse(argsToParse)
    }

    if (cliArgs.showHelp || (cliArgs.algorithm == null && cliArgs.size == null)) {
        if (!argsToParse.isEmpty()) { // Only show usage if arguments were actually provided but invalid/help requested
             parser.printUsage()
        }
        return
    }

    println("DNSort CLI Tool")
    println("Algorithm: ${cliArgs.algorithm ?: "Not specified"}")
    println("Size: ${cliArgs.size ?: "Not specified"}")
    println("Verbose: ${cliArgs.showVerbose}")
}
