# デザインパターンガイド

このガイドでは、プロジェクトで使用するデザインパターンの実装規則を定義します。

---

## Strategy パターン

### 用途

アルゴリズムを動的に切り替え可能にする。

### 構造

```
┌─────────────────┐
│   Interface     │ ◄──── クライアントが使用
└────────┬────────┘
         │ implements
    ┌────┴────┬────────┬────────┐
┌───┴───┐ ┌───┴───┐ ┌───┴───┐ ┌───┴───┐
│ Impl1 │ │ Impl2 │ │ Impl3 │ │ ...   │
└───────┘ └───────┘ └───────┘ └───────┘
```

### 実装ルール

```kotlin
// 1. インターフェース定義
interface SortAlgorithm {
    val type: SortType                    // 識別子
    fun sort(input: List<Int>): SortResult  // 実行メソッド
}

// 2. 具体実装
class BubbleSortAlgorithm : SortAlgorithm {
    override val type = SortType.BUBBLE
    override fun sort(input: List<Int>): SortResult { /* ... */ }
}

// 3. Factory で生成
val algorithm = SortAlgorithmFactory.create(SortType.BUBBLE)
val result = algorithm.sort(input)
```

### 本プロジェクトでの適用

| 対象 | インターフェース | 実装 |
|------|------------------|------|
| ソートアルゴリズム | `SortAlgorithm` | `BubbleSortAlgorithm`, `QuickSortAlgorithm`, ... |
| 配列生成 | `ArrayGenerator` | `ArrayGeneratorImpl` |

**参考**: [Refactoring.Guru - Strategy](https://refactoring.guru/design-patterns/strategy)

---

## Factory パターン

### 用途

オブジェクト生成を一元管理する。

### 実装ルール

```kotlin
// ✅ object + when 式
object SortAlgorithmFactory {
    fun create(type: SortType): SortAlgorithm = when (type) {
        SortType.BUBBLE -> BubbleSortAlgorithm()
        SortType.SELECTION -> SelectionSortAlgorithm()
        SortType.INSERTION -> InsertionSortAlgorithm()
        SortType.SHELL -> ShellSortAlgorithm()
        SortType.MERGE -> MergeSortAlgorithm()
        SortType.QUICK -> QuickSortAlgorithm()
        SortType.HEAP -> HeapSortAlgorithm()
        // 網羅的 (sealed/enum なので強制)
    }
}
```

### ルール

| ルール | 詳細 |
|--------|------|
| `object` として定義 | シングルトン |
| `when` は網羅的 | sealed class / enum と併用 |
| 戻り値はインターフェース | `SortAlgorithm` (not 具体型) |

**参考**: [Refactoring.Guru - Factory Method](https://refactoring.guru/design-patterns/factory-method)

---

## Template Method パターン

### 用途

アルゴリズムのスケルトンを定義し、サブクラスで詳細を実装。

### 実装ルール

```kotlin
abstract class BaseSortAlgorithm : SortAlgorithm {
    // 共通の状態
    protected val snapshots = mutableListOf<SortSnapshot>()
    protected var comparisonCount = 0L
    protected var swapCount = 0L

    // テンプレートメソッド (final 相当 - オーバーライドしない)
    override fun sort(input: List<Int>): SortResult {
        // 1. 初期化
        reset()
        val array = input.toMutableList()
        addSnapshot(array, emptyList(), "Start")
        
        // 2. メイン処理 (サブクラスで実装)
        val start = TimeSource.Monotonic.markNow()
        doSort(array)
        val elapsed = start.elapsedNow().inWholeNanoseconds
        
        // 3. 結果生成
        addSnapshot(array, emptyList(), "Finished")
        return SortResult(array, snapshots.toList(), createMetrics(elapsed))
    }

    // サブクラスで実装 (abstract)
    protected abstract fun doSort(array: MutableList<Int>)
    
    // 共通ヘルパー (protected)
    protected fun compare(a: Int, b: Int): Int {
        comparisonCount++
        return a.compareTo(b)
    }
    
    protected fun swap(array: MutableList<Int>, i: Int, j: Int) {
        swapCount++
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }
    
    // 省略可能なフック
    protected open fun onStepComplete() { }
}

// サブクラス
class BubbleSortAlgorithm : BaseSortAlgorithm() {
    override val type = SortType.BUBBLE
    
    override fun doSort(array: MutableList<Int>) {
        // バブルソート固有のロジックのみ
        for (i in array.indices) {
            for (j in 0 until array.size - i - 1) {
                if (compare(array[j], array[j + 1]) > 0) {
                    swap(array, j, j + 1)
                }
            }
        }
    }
}
```

### ルール

| ルール | 詳細 |
|--------|------|
| テンプレートメソッドは `final` (オーバーライドしない) | 処理フローを固定 |
| `abstract` メソッド | サブクラスで必ず実装 |
| `protected` ヘルパー | サブクラスから使用可能 |
| `open` フック | オプションでオーバーライド |

**参考**: [Refactoring.Guru - Template Method](https://refactoring.guru/design-patterns/template-method)

---

## 適用判断

| パターン | 使うべき場面 |
|----------|--------------|
| **Strategy** | 同じインターフェースで複数実装 |
| **Factory** | 型に基づいてインスタンス生成 |
| **Template Method** | 共通フローで詳細が異なる |

---

## 禁止事項

```kotlin
// ❌ 禁止 - 条件分岐で処理を切り替え
fun sort(type: SortType, input: List<Int>): SortResult {
    return when (type) {
        SortType.BUBBLE -> bubbleSort(input)  // ❌ Strategy を使う
        SortType.QUICK -> quickSort(input)
        // ...
    }
}

// ❌ 禁止 - Factory でインスタンスをキャッシュ
object BadFactory {
    private val cache = mutableMapOf<SortType, SortAlgorithm>()  // ❌ 状態を持つ
}
```

---

## 参考リンク

| パターン | リンク |
|----------|--------|
| **Strategy** | [Refactoring.Guru - Strategy](https://refactoring.guru/design-patterns/strategy) |
| **Factory Method** | [Refactoring.Guru - Factory Method](https://refactoring.guru/design-patterns/factory-method) |
| **Template Method** | [Refactoring.Guru - Template Method](https://refactoring.guru/design-patterns/template-method) |
| **Kotlin でのパターン** | [Baeldung - Design Patterns in Kotlin](https://www.baeldung.com/kotlin/design-patterns) |
