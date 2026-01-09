package dotnet.sort

fun main(args: Array<String>) {
    val parser = CliParser()
    val cliArgs = parser.parse(args)

    if (cliArgs.showHelp) {
        parser.printUsage()
        return
    }

    println("DNSort CLI Tool")
    println("Algorithm: ${cliArgs.algorithm ?: "Not specified"}")
    println("Size: ${cliArgs.size ?: "Not specified"}")
    println("Verbose: ${cliArgs.showVerbose}")
}
