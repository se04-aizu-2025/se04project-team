# テストガイド

このガイドでは、テストの記述規則と必須パターンを定義します。

---

## テスト構成

```
module/
└── src/
    ├── commonMain/         # プロダクションコード
    └── commonTest/         # 共通テスト (推奨)
        └── kotlin/
            └── dotnet/sort/{layer}/
                └── {ClassName}Test.kt
```

---

## 命名規則

### テストクラス

```kotlin
// クラス名: {テスト対象クラス名}Test
class BubbleSortAlgorithmTest
class SortViewModelTest
class ArrayGeneratorImplTest
```

### テストメソッド

```kotlin
// バッククォート + GIVEN/WHEN/THEN
@Test
fun `GIVEN {前提条件} WHEN {操作} THEN {期待結果}`()

// 具体例
@Test
fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`()

@Test
fun `GIVEN empty input WHEN generate is called THEN returns empty list`()

@Test
fun `GIVEN size 10 WHEN generate RANDOM THEN returns list of size 10`()
```

### 命名テンプレート

| テスト種類 | GIVEN | WHEN | THEN |
|------------|-------|------|------|
| **正常系** | 有効な入力 | 操作実行 | 期待結果 |
| **エッジケース** | 空/単一/境界値 | 操作実行 | 適切な処理 |
| **異常系** | 無効な入力 | 操作実行 | エラー/例外 |

---

## Given-When-Then パターン

### 構造

```kotlin
@Test
fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`() {
    // Given: 前提条件・テストデータの準備
    val input = listOf(5, 3, 1, 4, 2)
    val algorithm = BubbleSortAlgorithm()
    
    // When: テスト対象の操作を実行
    val result = algorithm.sort(input)
    
    // Then: 結果を検証
    assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
}
```

### 必須ルール

| ルール | 詳細 |
|--------|------|
| **コメント必須** | `// Given`, `// When`, `// Then` |
| **1テスト1検証** | 1つのテストで1つのことを検証 |
| **独立性** | テスト間で状態を共有しない |

---

## エッジケーステスト

### 必須テストケース

```kotlin
// ✅ すべてのアルゴリズムで必須

// 空配列
@Test
fun `GIVEN empty list WHEN sort is called THEN returns empty list`()

// 単一要素
@Test
fun `GIVEN single element WHEN sort is called THEN returns same element`()

// 重複値
@Test
fun `GIVEN duplicates WHEN sort is called THEN preserves all elements`()

// 既にソート済み
@Test
fun `GIVEN already sorted WHEN sort is called THEN returns same order`()

// 逆順
@Test
fun `GIVEN reverse sorted WHEN sort is called THEN returns sorted`()

// 同一値のみ
@Test
fun `GIVEN all same values WHEN sort is called THEN returns same list`()
```

---

## ViewModel テスト

### 構造

```kotlin
class SortViewModelTest {
    // テスト対象
    private lateinit var viewModel: SortViewModel
    
    // モック
    private val mockExecuteSortUseCase = mockk<ExecuteSortUseCase>()
    private val mockArrayGenerator = mockk<ArrayGenerator>()

    @BeforeTest
    fun setup() {
        // モックの設定
        every { mockArrayGenerator.generate(any(), any()) } returns listOf(5, 3, 1)
        
        // ViewModel 初期化
        viewModel = SortViewModel(mockExecuteSortUseCase, mockArrayGenerator)
    }

    @Test
    fun `GIVEN initial state WHEN SelectAlgorithm intent THEN updates algorithm`() = runTest {
        // Given
        val initialAlgorithm = viewModel.state.value.algorithm
        
        // When
        viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
        
        // Then
        assertEquals(SortType.QUICK, viewModel.state.value.algorithm)
    }
}
```

### Turbine を使った Flow テスト

```kotlin
@Test
fun `GIVEN sort intent WHEN executed THEN emits loading and result states`() = runTest {
    // Given
    coEvery { mockExecuteSortUseCase.execute(any(), any()) } returns mockSortResult
    
    viewModel.state.test {
        // 初期状態
        val initial = awaitItem()
        assertFalse(initial.isLoading)
        
        // When
        viewModel.send(SortIntent.StartSort)
        
        // Then: Loading 状態
        val loading = awaitItem()
        assertTrue(loading.isLoading)
        
        // Then: 結果状態
        val result = awaitItem()
        assertFalse(result.isLoading)
        assertNotNull(result.sortResult)
    }
}
```

**参考**: [Turbine - Testing Flow](https://github.com/cashapp/turbine)

---

## テストカバレッジ

### 必須カバレッジ

| レイヤー | 対象 | カバレッジ目標 |
|----------|------|----------------|
| **Domain** | Algorithm | 100% (全エッジケース) |
| **Domain** | UseCase | 100% |
| **Data** | Generator | 100% |
| **Presentation** | ViewModel | 主要 Intent 全て |

---

## アサーション

### 推奨

```kotlin
// ✅ 具体的なアサーション
assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
assertEquals(10, result.steps.size)
assertTrue(result.complexityMetrics.comparisonCount > 0)

// ✅ カスタムメッセージ
assertEquals(
    expected = listOf(1, 2, 3),
    actual = result.finalArray,
    message = "Sorted result should be [1, 2, 3]"
)
```

### 禁止

```kotlin
// ❌ 曖昧なアサーション
assertTrue(result != null)  // assertNotNull を使う

// ❌ 複数の検証を1つのアサーションに
assertTrue(result.isSuccess && result.data.size == 5)  // 分ける
```

---

## テストデータ

### ファクトリパターン

```kotlin
// テストデータファクトリ
object TestDataFactory {
    fun createUnsortedList(size: Int = 10): List<Int> =
        (1..size).shuffled()
    
    fun createSortedList(size: Int = 10): List<Int> =
        (1..size).toList()
    
    fun createReverseSortedList(size: Int = 10): List<Int> =
        (size downTo 1).toList()
    
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

新しいテストを追加する場合:

- [ ] テストクラス名: `{ClassName}Test`
- [ ] メソッド名: `GIVEN ... WHEN ... THEN ...`
- [ ] Given/When/Then コメントを追加
- [ ] エッジケースをカバー
- [ ] 1テスト1検証
- [ ] テストデータファクトリを使用

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **kotlin.test** | [Kotlin Test](https://kotlinlang.org/api/latest/kotlin.test/) |
| **Turbine** | [Turbine GitHub](https://github.com/cashapp/turbine) |
| **Testing ViewModel** | [Test a ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-testing) |
| **Testing Best Practices** | [Android Testing](https://developer.android.com/training/testing) |
| **Compose Testing** | [Testing Compose](https://developer.android.com/jetpack/compose/testing) |
