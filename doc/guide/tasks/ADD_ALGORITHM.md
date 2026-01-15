# アルゴリズムを追加する

このガイドでは、新しいソートアルゴリズムを追加するために必要なすべての手順を説明します。

---

## 概要

新しいアルゴリズムを追加するには、以下を変更します:

1. `SortType` に enum 追加
2. `{Name}SortAlgorithm.kt` を作成
3. `SortAlgorithmFactory` に追加
4. テストを作成

---

## Step 1: SortType に追加

```kotlin
// domain/model/SortType.kt

enum class SortType(
    val displayName: String,
    val timeComplexity: String,
    val spaceComplexity: String
) {
    // 既存...
    BUBBLE("Bubble Sort", "O(n²)", "O(1)"),
    SELECTION("Selection Sort", "O(n²)", "O(1)"),
    
    // 新規追加
    COUNTING("Counting Sort", "O(n + k)", "O(k)"),
    RADIX("Radix Sort", "O(nk)", "O(n + k)")
}
```

---

## Step 2: Algorithm クラスを作成

```kotlin
// domain/algorithm/{Name}SortAlgorithm.kt

/**
 * カウンティングソートの実装。
 *
 * 非負整数のみをソート可能。値の範囲 k が小さい場合に効率的。
 * 時間計算量: O(n + k)、空間計算量: O(k)
 */
class CountingSortAlgorithm : BaseSortAlgorithm() {
    
    override val type = SortType.COUNTING
    
    override fun doSort(array: MutableList<Int>) {
        if (array.isEmpty()) return
        
        val max = array.maxOrNull() ?: return
        val count = IntArray(max + 1)
        
        // カウントフェーズ
        for (num in array) {
            count[num]++
            addSnapshot(
                array = array.toList(),
                highlightIndices = listOf(array.indexOf(num)),
                description = "Counting $num"
            )
        }
        
        // 再構築フェーズ
        var index = 0
        for (i in count.indices) {
            repeat(count[i]) {
                array[index++] = i
                addSnapshot(
                    array = array.toList(),
                    highlightIndices = listOf(index - 1),
                    description = "Placing $i at index ${index - 1}"
                )
            }
        }
    }
}
```

### ルール
- `BaseSortAlgorithm` を継承
- `type` を override
- `doSort()` を実装
- `addSnapshot()` で可視化ステップ追加

---

## Step 3: Factory に追加

```kotlin
// domain/algorithm/SortAlgorithmFactory.kt

object SortAlgorithmFactory {
    fun create(type: SortType): SortAlgorithm = when (type) {
        SortType.BUBBLE -> BubbleSortAlgorithm()
        SortType.SELECTION -> SelectionSortAlgorithm()
        SortType.INSERTION -> InsertionSortAlgorithm()
        SortType.SHELL -> ShellSortAlgorithm()
        SortType.MERGE -> MergeSortAlgorithm()
        SortType.QUICK -> QuickSortAlgorithm()
        SortType.HEAP -> HeapSortAlgorithm()
        
        // 新規追加
        SortType.COUNTING -> CountingSortAlgorithm()
        SortType.RADIX -> RadixSortAlgorithm()
    }
}
```

### ルール
- すべての `SortType` を網羅 (sealed/enum で強制)
- else 不要

---

## Step 4: テストを作成

```kotlin
// commonTest/algorithm/{Name}SortAlgorithmTest.kt

class CountingSortAlgorithmTest : BaseSortAlgorithmTest() {
    
    // 基底クラスからアルゴリズム取得
    override fun createAlgorithm(): SortAlgorithm = CountingSortAlgorithm()
    
    // アルゴリズム固有のテスト
    @Test
    fun `GIVEN negative numbers WHEN sort THEN handles correctly`() {
        // Counting sort は非負整数のみ対応
        // エラーまたはフォールバック動作を検証
    }
    
    @Test
    fun `GIVEN large range WHEN sort THEN completes within time`() {
        // k が大きい場合のパフォーマンステスト
    }
}
```

### BaseSortAlgorithmTest の継承

```kotlin
// 基底テストクラス (すべてのアルゴリズムで共通)
abstract class BaseSortAlgorithmTest {
    abstract fun createAlgorithm(): SortAlgorithm
    
    private val algorithm by lazy { createAlgorithm() }
    
    @Test
    fun `GIVEN unsorted list WHEN sort THEN returns sorted list`() {
        val input = listOf(5, 3, 1, 4, 2)
        val result = algorithm.sort(input)
        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
    }
    
    @Test
    fun `GIVEN empty list WHEN sort THEN returns empty list`() {
        val result = algorithm.sort(emptyList())
        assertTrue(result.finalArray.isEmpty())
    }
    
    @Test
    fun `GIVEN single element WHEN sort THEN returns same element`() {
        val result = algorithm.sort(listOf(42))
        assertEquals(listOf(42), result.finalArray)
    }
    
    @Test
    fun `GIVEN duplicates WHEN sort THEN preserves all elements`() {
        val input = listOf(3, 1, 3, 2, 1)
        val result = algorithm.sort(input)
        assertEquals(listOf(1, 1, 2, 3, 3), result.finalArray)
    }
    
    @Test
    fun `GIVEN already sorted WHEN sort THEN returns same order`() {
        val input = listOf(1, 2, 3, 4, 5)
        val result = algorithm.sort(input)
        assertEquals(input, result.finalArray)
    }
    
    @Test
    fun `GIVEN reverse sorted WHEN sort THEN returns sorted`() {
        val input = listOf(5, 4, 3, 2, 1)
        val result = algorithm.sort(input)
        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
    }
}
```

---

## BaseSortAlgorithm API

### 使用可能なメソッド

| メソッド | 用途 | 自動カウント |
|----------|------|--------------|
| `compare(a, b)` | 2値を比較 | ✅ comparisonCount |
| `swap(array, i, j)` | 要素を交換 | ✅ swapCount |
| `addSnapshot(...)` | 可視化ステップ追加 | - |

### スナップショット追加

```kotlin
addSnapshot(
    array = array.toList(),        // ✅ コピー必須
    highlightIndices = listOf(i, j),  // ハイライト対象
    description = "Comparing $a and $b"  // 説明
)
```

### 説明テンプレート

| 操作 | 説明例 |
|------|--------|
| 比較 | "Comparing X and Y" |
| 交換 | "Swapping X and Y" |
| 挿入 | "Inserting X at position Y" |
| 選択 | "Found minimum: X" |
| 分割 | "Dividing array at pivot X" |
| マージ | "Merging subarrays" |

---

## チェックリスト

- [ ] `SortType` に enum 追加 (displayName, complexity)
- [ ] `{Name}SortAlgorithm.kt` 作成 (BaseSortAlgorithm 継承)
- [ ] `doSort()` 実装
- [ ] `addSnapshot()` で可視化ステップ追加
- [ ] `SortAlgorithmFactory` に case 追加
- [ ] `{Name}SortAlgorithmTest` 作成 (BaseSortAlgorithmTest 継承)
- [ ] アルゴリズム固有のエッジケーステスト追加
- [ ] KDoc 追加 (計算量を記載)
- [ ] テスト実行・成功確認

---

## 参考

詳細が必要な場合は、[reference/DESIGN_PATTERNS.md](../reference/DESIGN_PATTERNS.md) を参照してください。
