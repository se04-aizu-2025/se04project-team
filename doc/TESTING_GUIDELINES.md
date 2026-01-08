# テストガイドライン

このドキュメントでは、本プロジェクトにおけるテストの書き方、命名規則、およびベストプラクティスを定義します。

---

## 目次

1. [テスト構成](#テスト構成)
2. [命名規則](#命名規則)
3. [Given-When-Then パターン](#given-when-then-パターン)
4. [ユニットテスト](#ユニットテスト)
5. [UIテスト（Compose）](#uiテストcompose)
6. [ViewModelテスト](#viewmodelテスト)
7. [Koin統合テスト](#koin統合テスト)
8. [ベストプラクティス](#ベストプラクティス)

---

## テスト構成

### 推奨ライブラリ

| ライブラリ | 用途 | 依存関係 |
|-----------|------|----------|
| `kotlin.test` | ユニットテスト | `libs.kotlin.test` |
| `compose.ui:ui-test` | Compose UIテスト | `libs.compose.ui.test` |
| `Koin Test` | DI統合テスト | `libs.koin.test` |
| `Turbine` | Flow/StateFlowテスト | `libs.turbine` |

### ディレクトリ構造

```
module/
└── src/
    ├── commonMain/         # プロダクションコード
    ├── commonTest/         # 共通テスト（推奨）
    ├── jvmTest/            # JVM固有テスト
    └── ...
```

> [!TIP]
> 可能な限り `commonTest` にテストを配置し、プラットフォーム間で共有しましょう。

---

## 命名規則

### テストクラス名

```
{テスト対象クラス名}Test
```

**例**:
- `BubbleSortAlgorithmTest`
- `SortViewModelTest`

### テストメソッド名

**バッククォート記法**を使用し、`GIVEN`/`WHEN`/`THEN` を明示的に記述します。

```kotlin
fun `GIVEN {前提条件} WHEN {操作} THEN {期待結果}`()
```

**例**:
```kotlin
@Test
fun `GIVEN an unsorted list WHEN sort is called THEN returns sorted list`()

@Test
fun `GIVEN an empty list WHEN sort is called THEN returns empty list`()

@Test
fun `GIVEN a single element WHEN sort is called THEN returns same element`()

@Test
fun `GIVEN duplicate values WHEN sort is called THEN preserves all elements`()
```

> [!IMPORTANT]
> Kotlinのバッククォート記法により、スペースを含む読みやすいメソッド名を定義できます。
> これにより、テストの意図が一目で理解できます。

---

## Given-When-Then パターン

テストは **Given-When-Then** 形式で構造化します。これにより、テストの意図が明確になり、可読性が向上します。

### 構造

```kotlin
@Test
fun `GIVEN an unsorted list WHEN sort is called THEN returns sorted list`() {
    // Given: 前提条件・テストデータの準備
    val input = listOf(5, 3, 1, 4, 2)
    val algorithm = BubbleSortAlgorithm()

    // When: テスト対象の操作を実行
    val result = algorithm.sort(input)

    // Then: 結果を検証
    assertEquals(listOf(1, 2, 3, 4, 5), result.finalArray)
}
```

### 複雑なテストの例

```kotlin
@Test
fun `GIVEN a list with duplicate values WHEN sort is called THEN preserves all elements in sorted order`() {
    // Given: 重複を含む入力データ
    val input = listOf(3, 1, 3, 2, 1)
    val algorithm = BubbleSortAlgorithm()

    // When: ソートを実行
    val result = algorithm.sort(input)

    // Then: すべての要素が保持され、正しくソートされている
    assertEquals(listOf(1, 1, 2, 3, 3), result.finalArray)
    assertEquals(input.size, result.finalArray.size)
}
```

---

## ユニットテスト

### Domainレイヤーのテスト例

```kotlin
// domain/src/commonTest/kotlin/dotnet/sort/algorithm/BubbleSortAlgorithmTest.kt

package dotnet.sort.algorithm

import dotnet.sort.model.SortResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BubbleSortAlgorithmTest {

    private val sut = BubbleSortAlgorithm()  // System Under Test

    @Test
    fun `GIVEN an unsorted list WHEN sort is called THEN returns sorted list`() {
        // Given
        val input = listOf(64, 34, 25, 12, 22, 11, 90)

        // When
        val result = sut.sort(input)

        // Then
        assertEquals(listOf(11, 12, 22, 25, 34, 64, 90), result.finalArray)
    }

    @Test
    fun `GIVEN an empty list WHEN sort is called THEN returns empty list`() {
        // Given
        val input = emptyList<Int>()

        // When
        val result = sut.sort(input)

        // Then
        assertTrue(result.finalArray.isEmpty())
    }

    @Test
    fun `GIVEN an unsorted list WHEN sort is called THEN records comparison count`() {
        // Given
        val input = listOf(3, 2, 1)

        // When
        val result = sut.sort(input)

        // Then: バブルソートの比較回数は n*(n-1)/2
        assertTrue(result.complexityMetrics.comparisonCount > 0)
    }

    @Test
    fun `GIVEN a list of two elements WHEN sort is called THEN generates snapshots`() {
        // Given
        val input = listOf(2, 1)

        // When
        val result = sut.sort(input)

        // Then: スナップショットが記録されている
        assertTrue(result.steps.isNotEmpty())
        assertEquals("Start", result.steps.first().description)
        assertEquals("Sorted", result.steps.last().description)
    }
}
```

---

## UIテスト（Compose）

### セットアップ

```kotlin
// build.gradle.kts (commonTest)
dependencies {
    implementation(compose.uiTest)
}
```

### 基本的なUIテスト

```kotlin
package dotnet.sort.ui

import androidx.compose.ui.test.*
import kotlin.test.Test

class SortScreenTest {

    @Test
    fun `GIVEN the sort screen WHEN sort button is clicked THEN shows sorting state`() = runComposeUiTest {
        // Given: 画面を表示
        setContent {
            SortScreen()
        }

        // When: ソートボタンをクリック
        onNodeWithText("Sort").performClick()

        // Then: ソート中の表示に変わる
        onNodeWithText("Sorting...").assertExists()
    }

    @Test
    fun `GIVEN the algorithm selector WHEN expanded THEN displays all options`() = runComposeUiTest {
        // Given
        setContent {
            AlgorithmSelector(
                selected = SortType.BUBBLE,
                onSelect = {}
            )
        }

        // When: セレクターを展開
        onNodeWithContentDescription("Select Algorithm").performClick()

        // Then: すべてのオプションが表示される
        onNodeWithText("Bubble Sort").assertExists()
    }
}
```

### testTagを活用したテスト

```kotlin
// プロダクションコード
@Composable
fun SortVisualization(snapshots: List<SortSnapshot>) {
    LazyColumn(
        modifier = Modifier.testTag("snapshot_list")
    ) {
        items(snapshots) { snapshot ->
            SnapshotItem(
                snapshot = snapshot,
                modifier = Modifier.testTag("snapshot_item")
            )
        }
    }
}

// テストコード
@Test
fun `GIVEN three snapshots WHEN visualization is rendered THEN displays all snapshot items`() = runComposeUiTest {
    // Given
    val snapshots = listOf(
        SortSnapshot(listOf(3, 2, 1), emptyList(), "Start"),
        SortSnapshot(listOf(2, 3, 1), listOf(0, 1), "Swap"),
        SortSnapshot(listOf(1, 2, 3), emptyList(), "Sorted")
    )

    setContent {
        SortVisualization(snapshots = snapshots)
    }

    // Then
    onAllNodesWithTag("snapshot_item").assertCountEquals(3)
}
```

---

## ViewModelテスト

### Turbineを使用したFlowテスト

```kotlin
// build.gradle.kts
dependencies {
    implementation("app.cash.turbine:turbine:1.0.0")
}
```

```kotlin
package dotnet.sort.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SortViewModelTest {

    @Test
    fun `GIVEN a fresh viewmodel WHEN startSort is called THEN emits Loading then Success`() = runTest {
        // Given
        val viewModel = SortViewModel(
            sortUseCase = FakeSortUseCase()
        )

        // When & Then
        viewModel.uiState.test {
            // 初期状態
            assertEquals(SortUiState.Idle, awaitItem())

            // ソート開始
            viewModel.startSort(listOf(3, 1, 2))

            // Loading状態
            assertEquals(SortUiState.Loading, awaitItem())

            // 成功状態
            val successState = awaitItem()
            assertTrue(successState is SortUiState.Success)
        }
    }

    @Test
    fun `GIVEN a failing sortUseCase WHEN startSort is called THEN emits Error state`() = runTest {
        // Given
        val viewModel = SortViewModel(
            sortUseCase = FakeSortUseCase(shouldFail = true)
        )

        // When & Then
        viewModel.uiState.test {
            assertEquals(SortUiState.Idle, awaitItem())
            viewModel.startSort(listOf(3, 1, 2))
            assertEquals(SortUiState.Loading, awaitItem())
            
            val errorState = awaitItem()
            assertTrue(errorState is SortUiState.Error)
        }
    }
}
```

---

## Koin統合テスト

### テスト用モジュールの定義

```kotlin
// テスト用のFake実装
class FakeSortRepository : SortRepository {
    override suspend fun getSortHistory(): List<SortResult> = emptyList()
}

// テスト用Koinモジュール
val testModule = module {
    single<SortRepository> { FakeSortRepository() }
    factory { SortUseCase(get()) }
}
```

### Koinを使用したテスト

```kotlin
package dotnet.sort

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class SortUseCaseKoinTest : KoinTest {

    private val sortUseCase: SortUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GIVEN Koin is started with testModule WHEN sortUseCase is injected THEN it is not null`() {
        // Given: Koinが起動済み

        // When & Then
        assertNotNull(sortUseCase)
    }
}
```

### Compose UI + Koinテスト

```kotlin
@Test
fun `GIVEN Koin context WHEN SortScreen is rendered THEN displays Sort button`() = runComposeUiTest {
    // Given
    startKoin {
        modules(testModule)
    }

    try {
        setContent {
            KoinContext {
                SortScreen()
            }
        }

        // Then
        onNodeWithText("Sort").assertExists()
    } finally {
        stopKoin()
    }
}
```

---

## ベストプラクティス

### 1. AAAパターンの厳守

**Arrange-Act-Assert** (= Given-When-Then) を明確に分離する。

```kotlin
@Test
fun `GIVEN some input WHEN action is performed THEN expected result occurs`() {
    // Arrange (Given)
    val input = ...

    // Act (When)
    val result = ...

    // Assert (Then)
    assertEquals(expected, result)
}
```

### 2. 1テスト1アサーション

1つのテストでは1つの振る舞いのみを検証する。

```kotlin
// ❌ Bad: 複数の振る舞いをテスト
@Test
fun `GIVEN input WHEN sort THEN everything works`() {
    val result = algorithm.sort(input)
    assertEquals(expected, result.finalArray)
    assertTrue(result.steps.isNotEmpty())
    assertTrue(result.complexityMetrics.comparisonCount > 0)
}

// ✅ Good: 個別にテスト
@Test
fun `GIVEN unsorted list WHEN sort THEN returns sorted array`() { ... }

@Test
fun `GIVEN unsorted list WHEN sort THEN records snapshots`() { ... }

@Test
fun `GIVEN unsorted list WHEN sort THEN counts comparisons`() { ... }
```

### 3. テストデータの明示

マジックナンバーを避け、意図を明確にする。

```kotlin
// ❌ Bad
val input = listOf(5, 3, 1, 4, 2)

// ✅ Good
val unsortedNumbers = listOf(5, 3, 1, 4, 2)
val expectedSorted = listOf(1, 2, 3, 4, 5)
```

### 4. Fakeを優先

`Mock`よりも`Fake`を優先し、振る舞いを明示的に制御する。

```kotlin
// ✅ Good: Fake実装
class FakeSortRepository : SortRepository {
    var shouldThrowError = false

    override suspend fun save(result: SortResult) {
        if (shouldThrowError) throw IOException("Test error")
    }
}
```

### 5. テストの独立性

各テストは他のテストに依存せず、独立して実行可能であること。

```kotlin
class SortViewModelTest {
    // 各テストで新しいインスタンスを作成
    private fun createViewModel() = SortViewModel(
        sortUseCase = FakeSortUseCase()
    )

    @Test
    fun `GIVEN fresh viewmodel WHEN test1 THEN works`() {
        val viewModel = createViewModel()
        // ...
    }

    @Test
    fun `GIVEN fresh viewmodel WHEN test2 THEN works`() {
        val viewModel = createViewModel()
        // ...
    }
}
```

### 6. エッジケースを忘れない

- 空のリスト
- 単一要素
- 重複値
- 負の数
- 大きなデータセット
- null（Nullable型の場合）

```kotlin
@Test
fun `GIVEN an empty list WHEN sort THEN returns empty list`() { ... }

@Test
fun `GIVEN a single element WHEN sort THEN returns same element`() { ... }

@Test
fun `GIVEN negative numbers WHEN sort THEN handles correctly`() { ... }

@Test
fun `GIVEN duplicate values WHEN sort THEN preserves all elements`() { ... }
```

### 7. テスト実行コマンド

```bash
# 全テスト実行
./gradlew allTests

# 特定モジュールのテスト
./gradlew :domain:allTests
./gradlew :presentation:allTests

# JVMテストのみ
./gradlew jvmTest
```

---

## チェックリスト

テストを書く際は以下を確認してください：

- [ ] Given-When-Then形式で構造化されている
- [ ] テスト名が `` `GIVEN ... WHEN ... THEN ...` `` 形式
- [ ] 1つのテストで1つの振る舞いのみを検証
- [ ] エッジケースがカバーされている
- [ ] テストが独立して実行可能
- [ ] FakeがMockより優先されている
- [ ] マジックナンバーが避けられている
