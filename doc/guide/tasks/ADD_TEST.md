# テストを追加する

このガイドでは、テストを追加するために必要なすべての手順を説明します。

---

## 概要

テストは以下の場所に配置します:

```
module/
└── src/
    └── commonTest/
        └── kotlin/dotnet/sort/{layer}/
            └── {ClassName}Test.kt
```

---

## テスト種類

| 種類 | 対象 | ディレクトリ |
|------|------|--------------|
| **Algorithm テスト** | SortAlgorithm 実装 | `domain/commonTest/` |
| **UseCase テスト** | UseCase | `domain/commonTest/` |
| **Generator テスト** | ArrayGenerator | `data/commonTest/` |
| **ViewModel テスト** | ViewModel | `presentation/feature/.../commonTest/` |

---

## Step 1: テストクラスを作成

```kotlin
// {ClassName}Test.kt

class BubbleSortAlgorithmTest {
    // テスト対象
    private val algorithm = BubbleSortAlgorithm()
    
    @Test
    fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`() {
        // Given
        val input = listOf(5, 3, 1, 4, 2)
        
        // When
        val result = algorithm.sort(input)
        
        // Then
        assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
    }
}
```

### 命名規則

| 要素 | 規則 |
|------|------|
| **クラス名** | `{テスト対象}Test` |
| **メソッド名** | `` `GIVEN ... WHEN ... THEN ...` `` |

---

## Step 2: Given-When-Then

```kotlin
@Test
fun `GIVEN {前提条件} WHEN {操作} THEN {期待結果}`() {
    // Given: 前提条件・テストデータ準備
    val input = listOf(5, 3, 1)
    
    // When: テスト対象の操作実行
    val result = algorithm.sort(input)
    
    // Then: 結果検証
    assertEquals(listOf(1, 3, 5), result.finalArray)
}
```

### ルール

- `// Given`, `// When`, `// Then` コメント必須
- 1テスト 1検証
- テスト間で状態共有しない

---

## エッジケーステスト

### 必須ケース

```kotlin
// 空入力
@Test
fun `GIVEN empty list WHEN sort THEN returns empty list`()

// 単一要素
@Test
fun `GIVEN single element WHEN sort THEN returns same element`()

// 重複値
@Test
fun `GIVEN duplicates WHEN sort THEN preserves all elements`()

// ソート済み
@Test
fun `GIVEN already sorted WHEN sort THEN returns same order`()

// 逆順
@Test
fun `GIVEN reverse sorted WHEN sort THEN returns sorted`()

// 全同一値
@Test
fun `GIVEN all same values WHEN sort THEN returns same list`()
```

---

## ViewModel テスト

```kotlin
class SortViewModelTest {
    private lateinit var viewModel: SortViewModel
    private val mockUseCase = mockk<ExecuteSortUseCase>()
    private val mockGenerator = mockk<ArrayGenerator>()

    @BeforeTest
    fun setup() {
        every { mockGenerator.generate(any(), any()) } returns listOf(5, 3, 1)
        viewModel = SortViewModel(mockUseCase, mockGenerator)
    }

    @Test
    fun `GIVEN initial state WHEN SelectAlgorithm THEN updates algorithm`() = runTest {
        // Given
        val initialAlgorithm = viewModel.state.value.algorithm
        
        // When
        viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
        
        // Then
        assertEquals(SortType.QUICK, viewModel.state.value.algorithm)
    }
}
```

---

## Turbine を使った Flow テスト

```kotlin
@Test
fun `GIVEN sort intent WHEN executed THEN emits loading and result`() = runTest {
    // Given
    coEvery { mockUseCase.execute(any(), any()) } returns mockSortResult
    
    viewModel.state.test {
        // 初期状態
        val initial = awaitItem()
        assertFalse(initial.isLoading)
        
        // When
        viewModel.send(SortIntent.StartSort)
        
        // Then: Loading
        val loading = awaitItem()
        assertTrue(loading.isLoading)
        
        // Then: Result
        val result = awaitItem()
        assertFalse(result.isLoading)
        assertNotNull(result.sortResult)
    }
}
```

### Turbine API

| メソッド | 用途 |
|----------|------|
| `awaitItem()` | 次の emit を待つ |
| `skipItems(n)` | n 個スキップ |
| `expectNoEvents()` | イベントなしを確認 |

---

## アサーション

### 推奨

```kotlin
// 具体的なアサーション
assertEquals(expected, actual)
assertTrue(condition)
assertFalse(condition)
assertNotNull(value)
assertNull(value)

// メッセージ付き
assertEquals(expected, actual, "Should return sorted list")
```

### 禁止

```kotlin
// ❌ 曖昧
assertTrue(result != null)  // → assertNotNull

// ❌ 複合条件
assertTrue(a && b && c)  // → 分割
```

---

## テストデータファクトリ

```kotlin
object TestDataFactory {
    fun createUnsortedList(size: Int = 10): List<Int> =
        (1..size).shuffled()
    
    fun createSortedList(size: Int = 10): List<Int> =
        (1..size).toList()
    
    fun createSortResult(
        finalArray: List<Int> = listOf(1, 2, 3),
        stepsCount: Int = 5
    ): SortResult = SortResult(
        finalArray = finalArray,
        steps = List(stepsCount) { createSnapshot() },
        complexityMetrics = createMetrics()
    )
}
```

---

## チェックリスト

- [ ] テストクラス作成 (`{ClassName}Test`)
- [ ] メソッド命名 (`GIVEN ... WHEN ... THEN ...`)
- [ ] Given/When/Then コメント追加
- [ ] エッジケースをカバー
- [ ] 1テスト 1検証
- [ ] テストデータファクトリを使用 (必要に応じて)
- [ ] テスト実行・成功確認

---

## 参考

詳細が必要な場合は、[reference/TESTING.md](../reference/TESTING.md) を参照してください。
