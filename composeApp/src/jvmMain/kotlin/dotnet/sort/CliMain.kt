package dotnet.sort

import dotnet.sort.CliParser
import dotnet.sort.InteractiveMode
import dotnet.sort.CliVisualizer
import dotnet.sort.domain.generator.ArrayGeneratorImpl
import dotnet.sort.domain.generator.ArrayGeneratorType
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.usecase.ExecuteSortUseCase
import dotnet.sort.domain.usecase.GenerateArrayUseCase

fun main(args: Array<String>) {
    val parser = CliParser()
    val argsToParse = args
    val cliArgs = if (argsToParse.isEmpty()) {
        InteractiveMode().run()
    } else {
        parser.parse(argsToParse)
    }

    if (cliArgs.showHelp || (cliArgs.algorithm == null && cliArgs.size == null)) {
        if (!argsToParse.isEmpty()) { 
             parser.printUsage()
        }
        return
    }

    val algorithmName = cliArgs.algorithm
    val size = cliArgs.size ?: 50
    val showVerbose = cliArgs.showVerbose

    val sortType = SortType.entries.find { it.name.equals(algorithmName, ignoreCase = true) }
        ?: run {
            println("Error: Unknown algorithm '$algorithmName'")
            return
        }

    println("DNSort CLI Tool")
    println("Configuration: Algorithm=${sortType.displayName}, Size=$size, Verbose=$showVerbose")
    println("Generating array...")

    // Setup dependencies manually
    val generator = ArrayGeneratorImpl()
    val generateUseCase = GenerateArrayUseCase(generator)
    val executeUseCase = ExecuteSortUseCase()

    val initialArray = generateUseCase.invoke(size, ArrayGeneratorType.RANDOM)
    
    println("Starting sort...")
    val result = executeUseCase.execute(sortType, initialArray)

    println("Sort completed!")
    println("Metrics:")
    println("  Comparisons: ${result.complexityMetrics.comparisonCount}")
    println("  Swaps: ${result.complexityMetrics.swapCount}")
    println("  Time: ${result.complexityMetrics.executionTimeNs / 1_000_000.0} ms")

    if (showVerbose) {
        println("\nVisualization:")
        result.steps.forEachIndexed { index, snapshot ->
            println("Step $index: ${snapshot.description}")
            CliVisualizer.visualize(snapshot.arrayState)
            Thread.sleep(50) 
        }
    }
}
