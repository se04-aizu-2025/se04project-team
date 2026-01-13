# CLI コマンドを追加する

このガイドでは、CLI に新しいコマンドを追加するために必要なすべての手順を説明します。

---

## 概要

CLI モジュール構成:

```
cli/
└── src/jvmMain/kotlin/dotnet/sort/cli/
    ├── Main.kt              # エントリーポイント
    ├── ArgumentParser.kt    # 引数パーサー
    ├── InteractiveMode.kt   # 対話モード
    └── commands/            # コマンド実装
        ├── SortCommand.kt
        └── HelpCommand.kt
```

---

## Step 1: コマンドクラスを作成

```kotlin
// cli/commands/{Name}Command.kt

/**
 * ベンチマークコマンド。
 * 
 * 複数のアルゴリズムを比較実行し、結果を表示する。
 */
class BenchmarkCommand(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val arrayGenerator: ArrayGenerator
) {
    
    fun execute(options: BenchmarkOptions): Int {
        println("Running benchmark...")
        println("Array size: ${options.size}")
        println("Iterations: ${options.iterations}")
        println()
        
        val algorithms = options.algorithms.ifEmpty { SortType.entries }
        val input = arrayGenerator.generate(options.size, ArrayGeneratorType.RANDOM)
        
        val results = algorithms.map { type ->
            val times = (1..options.iterations).map {
                val start = System.nanoTime()
                executeSortUseCase.execute(type, input)
                System.nanoTime() - start
            }
            BenchmarkResult(type, times.average())
        }.sortedBy { it.averageTime }
        
        printResults(results)
        return 0  // 成功
    }
    
    private fun printResults(results: List<BenchmarkResult>) {
        println("=== Benchmark Results ===")
        println()
        results.forEachIndexed { index, result ->
            val ms = result.averageTime / 1_000_000.0
            println("${index + 1}. ${result.type.displayName}: %.3f ms".format(ms))
        }
    }
}

data class BenchmarkOptions(
    val size: Int = 1000,
    val iterations: Int = 10,
    val algorithms: List<SortType> = emptyList()
)

data class BenchmarkResult(
    val type: SortType,
    val averageTime: Double
)
```

---

## Step 2: 引数パーサーに追加

```kotlin
// cli/ArgumentParser.kt

sealed class CliCommand {
    data class Sort(
        val algorithm: SortType,
        val size: Int,
        val verbose: Boolean
    ) : CliCommand()
    
    data class Benchmark(
        val options: BenchmarkOptions
    ) : CliCommand()  // 追加
    
    data object Interactive : CliCommand()
    data object Help : CliCommand()
}

fun parseArguments(args: Array<String>): CliCommand {
    if (args.isEmpty()) return CliCommand.Help
    
    return when (args[0]) {
        "sort" -> parseSortArgs(args.drop(1))
        "benchmark" -> parseBenchmarkArgs(args.drop(1))  // 追加
        "-i", "--interactive" -> CliCommand.Interactive
        "-h", "--help" -> CliCommand.Help
        else -> CliCommand.Help
    }
}

private fun parseBenchmarkArgs(args: List<String>): CliCommand.Benchmark {
    var size = 1000
    var iterations = 10
    val algorithms = mutableListOf<SortType>()
    
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-s", "--size" -> {
                size = args.getOrNull(i + 1)?.toIntOrNull() ?: 1000
                i++
            }
            "-n", "--iterations" -> {
                iterations = args.getOrNull(i + 1)?.toIntOrNull() ?: 10
                i++
            }
            "-a", "--algorithm" -> {
                args.getOrNull(i + 1)?.let { name ->
                    SortType.entries.find { 
                        it.name.equals(name, ignoreCase = true) 
                    }?.let { algorithms.add(it) }
                }
                i++
            }
        }
        i++
    }
    
    return CliCommand.Benchmark(
        BenchmarkOptions(size, iterations, algorithms)
    )
}
```

---

## Step 3: Main.kt に実行処理を追加

```kotlin
// cli/Main.kt

fun main(args: Array<String>) {
    initKoin()  // DI 初期化
    
    val command = parseArguments(args)
    val exitCode = executeCommand(command)
    
    exitProcess(exitCode)
}

private fun executeCommand(command: CliCommand): Int {
    return when (command) {
        is CliCommand.Sort -> {
            val sortCommand = SortCommand(getKoin().get(), getKoin().get())
            sortCommand.execute(command)
        }
        is CliCommand.Benchmark -> {
            val benchmarkCommand = BenchmarkCommand(getKoin().get(), getKoin().get())
            benchmarkCommand.execute(command.options)
        }
        CliCommand.Interactive -> {
            InteractiveMode(getKoin().get(), getKoin().get()).run()
        }
        CliCommand.Help -> {
            printHelp()
            0
        }
    }
}
```

---

## Step 4: ヘルプに追加

```kotlin
private fun printHelp() {
    println("""
        DNSort CLI
        
        Usage:
          dnsort sort [options]      Run a sorting algorithm
          dnsort benchmark [options] Run benchmark comparison
          dnsort -i, --interactive   Interactive mode
          dnsort -h, --help          Show this help
        
        Sort Options:
          -a, --algorithm <name>  Algorithm (bubble, quick, merge, etc.)
          -s, --size <n>          Array size (default: 20)
          -v, --verbose           Show step-by-step visualization
        
        Benchmark Options:
          -s, --size <n>          Array size (default: 1000)
          -n, --iterations <n>    Iterations (default: 10)
          -a, --algorithm <name>  Include specific algorithm (can repeat)
    """.trimIndent())
}
```

---

## Step 5: テストを追加

```kotlin
class BenchmarkCommandTest {
    private val mockUseCase = mockk<ExecuteSortUseCase>()
    private val mockGenerator = mockk<ArrayGenerator>()
    private val command = BenchmarkCommand(mockUseCase, mockGenerator)
    
    @BeforeTest
    fun setup() {
        every { mockGenerator.generate(any(), any()) } returns listOf(5, 3, 1)
        every { mockUseCase.execute(any(), any()) } returns mockSortResult
    }
    
    @Test
    fun `GIVEN default options WHEN execute THEN runs all algorithms`() {
        val result = command.execute(BenchmarkOptions())
        assertEquals(0, result)
    }
}
```

---

## 出力フォーマット

### 通常出力

```
Running benchmark...
Array size: 1000
Iterations: 10

=== Benchmark Results ===

1. Quick Sort: 0.523 ms
2. Merge Sort: 0.612 ms
3. Heap Sort: 0.834 ms
...
```

### 詳細出力 (--verbose)

```
Running benchmark...
  Iteration 1/10: Quick Sort = 0.521 ms
  Iteration 2/10: Quick Sort = 0.518 ms
  ...
```

---

## チェックリスト

- [ ] コマンドクラスを作成
- [ ] `CliCommand` に追加
- [ ] `parseArguments` に処理追加
- [ ] `executeCommand` に追加
- [ ] ヘルプ文を更新
- [ ] テストを作成

---

## 参考

- [DEVELOPMENT_PLAN.md (PR-46~49)](../../DEVELOPMENT_PLAN.md)
