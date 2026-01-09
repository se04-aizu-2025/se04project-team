package dotnet.sort

object CliVisualizer {
    fun visualize(snapshot: List<Int>) {
        printAscii(snapshot)
    }

    private fun printAscii(list: List<Int>) {
        val max = list.maxOrNull() ?: 1
        val height = 10
        val normalized = list.map { (it * height) / max }

        println() // spacing
        for (h in height downTo 1) {
            val line = normalized.joinToString("") { if (it >= h) "█" else " " }
            println(line)
        }
    }
}
