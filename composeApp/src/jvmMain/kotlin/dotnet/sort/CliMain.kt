package dotnet.sort

fun main(args: Array<String>) {
    println("DNSort CLI Tool")
    println("Arguments: ${args.joinToString()}")
    
    if (args.isEmpty()) {
        println("No arguments provided. Usage: cli [options]")
        return
    }
}
