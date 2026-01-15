# アルゴリズム実装ガイド

このガイドでは、新しいソートアルゴリズムを追加する際の規則を定義します。

---

## 実装手順

### 1. SortType に追加

```kotlin
// domain/model/SortType.kt
enum class SortType(
    val displayName: String,
    val timeComplexity: String,
    val spaceComplexity: String
) {
    // 既存...
    
    // 新規追加
    COUNTING("Counting Sort", "O(n + k)", "O(k)"),
    RADIX("Radix Sort", "O(nk)", "O(n + k)")
}
```

### 2. Algorithm クラスを作成

```kotlin
// domain/algorithm/CountingSortAlgorithm.kt

/**
 * カウンティングソートの実装。
 *
 * 非負整数のみをソート可能。値の範囲 k が小さい場合に効率的。
 *
 * @property type アルゴリズム種別
 */
class CountingSortAlgorithm : BaseSortAlgorithm() {
    
    override val type = SortType.COUNTING
    
    override fun doSort(array: MutableList<Int>) {
        if (array.isEmpty()) return
        
        val max = array.maxOrNull() ?: return
        val count = IntArray(max + 1)
        
        // カウント
        for (num in array) {
            count[num]++
            addSnapshot(
                array.toList(),
                listOf(array.indexOf(num)),
                "Counting $num"
            )
        }
        
        // 再構築
        var index = 0
        for (i in count.indices) {
            repeat(count[i]) {
                array[index++] = i
                addSnapshot(
                    array.toList(),
                    listOf(index - 1),
                    "Placing $i at index ${index - 1}"
                )
            }
        }
    }
}
```

### 3. Factory に追加

```kotlin
// domain/algorithm/SortAlgorithmFactory.kt
object SortAlgorithmFactory {
    fun create(type: SortType): SortAlgorithm = when (type) {
        // 既存...
        SortType.COUNTING -> CountingSortAlgorithm()
        SortType.RADIX -> RadixSortAlgorithm()
    }
}
```

### 4. テストを作成

```kotlin
// commonTest/algorithm/CountingSortAlgorithmTest.kt
class CountingSortAlgorithmTest : BaseSortAlgorithmTest() {
    
    override fun createAlgorithm(): SortAlgorithm = CountingSortAlgorithm()
    
    @Test
    fun `GIVEN counting sort specific case WHEN sort THEN works correctly`() {
        // Counting sort 固有のテスト
    }
}
```

---

## ファイル構成

```
domain/
├── algorithm/
│   ├── SortAlgorithm.kt           # インターフェース
│   ├── BaseSortAlgorithm.kt       # 基底クラス (Template Method)
│   ├── SortAlgorithmFactory.kt    # Factory
│   ├── BubbleSortAlgorithm.kt
│   ├── SelectionSortAlgorithm.kt
│   ├── InsertionSortAlgorithm.kt
│   ├── ShellSortAlgorithm.kt
│   ├── MergeSortAlgorithm.kt
│   ├── QuickSortAlgorithm.kt
│   ├── HeapSortAlgorithm.kt
│   └── CountingSortAlgorithm.kt   # 新規
└── model/
    └── SortType.kt                # enum
```

---

## 命名規則

| 要素 | 規則 | 例 |
|------|------|-----|
| **クラス名** | `{Name}SortAlgorithm` | `CountingSortAlgorithm` |
| **ファイル名** | `{Name}SortAlgorithm.kt` | `CountingSortAlgorithm.kt` |
| **SortType** | `SCREAMING_SNAKE_CASE` | `COUNTING`, `RADIX` |

---

## BaseSortAlgorithm の API

### 使用可能なメソッド

| メソッド | 用途 |
|----------|------|
| `compare(a, b)` | 比較 (カウント自動) |
| `swap(array, i, j)` | 交換 (カウント自動) |
| `addSnapshot(array, indices, description)` | スナップショット追加 |

### 使用可能なプロパティ

| プロパティ | 型 | 用途 |
|------------|-----|------|
| `comparisonCount` | `Long` | 比較回数 |
| `swapCount` | `Long` | 交換回数 |
| `snapshots` | `MutableList<SortSnapshot>` | スナップショット |

---

## スナップショット追加

### ルール

```kotlin
// ✅ 意味のあるタイミングで追加
addSnapshot(
    array = array.toList(),       // 現在の配列 (コピー必須)
    highlightIndices = listOf(i, j),  // ハイライト対象
    description = "Comparing $a and $b"  // 説明
)

// ❌ 禁止 - コピーし忘れ
addSnapshot(array, ...)  // 参照が共有される
```

### 追加タイミング

| タイミング | 説明 |
|------------|------|
| 比較時 | "Comparing X and Y" |
| 交換時 | "Swapping X and Y" |
| 挿入時 | "Inserting X at position Y" |
| 分割時 | "Dividing array" |
| マージ時 | "Merging subarrays" |

---

## テスト

### 必須テストケース

```kotlin
abstract class BaseSortAlgorithmTest {
    abstract fun createAlgorithm(): SortAlgorithm
    
    @Test
    fun `GIVEN unsorted list WHEN sort THEN returns sorted list`()
    
    @Test
    fun `GIVEN empty list WHEN sort THEN returns empty list`()
    
    @Test
    fun `GIVEN single element WHEN sort THEN returns same element`()
    
    @Test
    fun `GIVEN duplicates WHEN sort THEN preserves all elements`()
    
    @Test
    fun `GIVEN already sorted WHEN sort THEN returns same order`()
    
    @Test
    fun `GIVEN reverse sorted WHEN sort THEN returns sorted list`()
}
```

### アルゴリズム固有テスト

```kotlin
class QuickSortAlgorithmTest : BaseSortAlgorithmTest() {
    // 基底テストを継承
    
    @Test
    fun `GIVEN worst case pivot WHEN sort THEN still completes`() {
        // Quick sort 固有: worst case テスト
    }
}
```

---

## チェックリスト

新しいアルゴリズムを追加する場合:

- [ ] `SortType` に enum 値を追加
- [ ] `{Name}SortAlgorithm.kt` を作成
- [ ] `BaseSortAlgorithm` を継承
- [ ] `doSort()` を実装
- [ ] スナップショットを適切に追加
- [ ] `SortAlgorithmFactory` に case を追加
- [ ] `BaseSortAlgorithmTest` を継承したテストを作成
- [ ] 固有のエッジケーステストを追加
- [ ] KDoc を追加 (計算量を記載)

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Sorting Algorithms** | [Wikipedia - Sorting Algorithm](https://en.wikipedia.org/wiki/Sorting_algorithm) |
| **Visualgo** | [Visualgo - Sorting](https://visualgo.net/en/sorting) |
| **Big-O Cheat Sheet** | [Big-O Cheat Sheet](https://www.bigocheatsheet.com/) |
