package dotnet.sort

import dotnet.sort.model.SortType
import java.io.BufferedReader
import java.io.InputStreamReader

class InteractiveMode {
    fun run(): CliArgs {
        val reader = BufferedReader(InputStreamReader(System.`in`))

        println("=== DNSort Interactive Mode ===")
        
        // 1. Select Algorithm
        val algorithm = selectAlgorithm(reader)
        if (algorithm == null) {
            println("Operation cancelled.")
            return CliArgs(showHelp = true)
        }

        // 2. Select Size
        val size = selectSize(reader)
        if (size == null) {
            println("Operation cancelled.")
            return CliArgs(showHelp = true)
        }

        // 3. Confirm
        println("\nConfiguration:")
        println("  Algorithm: ${algorithm.displayName}")
        println("  Size: $size")
        print("\nStart sort? (y/n): ")
        val confirm = reader.readLine()?.trim()?.lowercase()
        return if (confirm == "y" || confirm == "yes") {
            CliArgs(algorithm = algorithm.name, size = size)
        } else {
            println("Cancelled.")
            CliArgs() // Empty args, effectively doing nothing or showing help
        }
    }

    private fun selectAlgorithm(reader: BufferedReader): SortType? {
        println("\nSelect Algorithm:")
        val algorithms = SortType.entries
        algorithms.forEachIndexed { index, type ->
            println("  ${index + 1}. ${type.displayName}")
        }

        while (true) {
            print("Enter number (1-${algorithms.size}): ")
            val input = reader.readLine()?.trim() ?: return null
            if (input.isEmpty()) continue

            val index = input.toIntOrNull()
            if (index != null && index in 1..algorithms.size) {
                return algorithms[index - 1]
            }
            println("Invalid selection. Please try again.")
        }
    }

    private fun selectSize(reader: BufferedReader): Int? {
        println("\nEnter Array Size:")
        while (true) {
            print("Size (e.g. 50): ")
            val input = reader.readLine()?.trim() ?: return null
            if (input.isEmpty()) continue

            val size = input.toIntOrNull()
            if (size != null && size > 0) {
                return size
            }
            println("Invalid size. Please enter a positive integer.")
        }
    }
}
