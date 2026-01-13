# データモデルガイド

このガイドでは、data class / sealed class / enum の設計規則を定義します。

---

## data class

### 用途

- 不変のデータコンテナ
- Value Object / Entity

### 必須ルール

```kotlin
// ✅ すべて val
data class SortSnapshot(
    val array: List<Int>,
    val highlightingIndices: List<Int>,
    val description: String
)

// ✅ デフォルト値を設定
data class SortState(
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 20,
    val isLoading: Boolean = false
)

// ✅ 不変コレクション
data class SortResult(
    val finalArray: List<Int>,        // List (not MutableList)
    val steps: List<SortSnapshot>
)
```

### 禁止事項

```kotlin
// ❌ var は禁止
data class BadModel(
    var name: String  // ❌
)

// ❌ MutableList は禁止
data class BadModel(
    val items: MutableList<Int>  // ❌
)

// ❌ ロジックを持たない
data class BadModel(
    val x: Int
) {
    fun calculate() { }  // ❌ UseCase に移動
}
```

---

## sealed class

### 用途

- 制限された型階層
- Result 型 / Intent / Screen

### パターン

```kotlin
// Intent
sealed class SortIntent : Intent {
    data object StartSort : SortIntent()
    data class SelectAlgorithm(val type: SortType) : SortIntent()
}

// Result
sealed class SortOperationResult {
    data class Success(val data: SortResult) : SortOperationResult()
    data class Error(val message: String) : SortOperationResult()
    data object Loading : SortOperationResult()
}

// Route
@Serializable
sealed class Screen {
    @Serializable data object Home : Screen()
    @Serializable data object Sort : Screen()
    @Serializable data class Detail(val id: String) : Screen()
}
```

### ルール

| サブクラス | 定義方法 |
|------------|----------|
| データなし | `data object` |
| データあり | `data class` |
| 単一インスタンス | `object` (非 data) |

### when での網羅

```kotlin
// sealed class は網羅的 when を強制
fun handleResult(result: SortOperationResult) {
    when (result) {
        is SortOperationResult.Success -> { }
        is SortOperationResult.Error -> { }
        SortOperationResult.Loading -> { }
        // else 不要 (すべてカバー)
    }
}
```

---

## enum class

### 用途

- 有限の値セット
- 設定値 / 種類

### パターン

```kotlin
// プロパティ付き enum
enum class SortType(
    val displayName: String,
    val timeComplexity: String,
    val spaceComplexity: String
) {
    BUBBLE("Bubble Sort", "O(n²)", "O(1)"),
    SELECTION("Selection Sort", "O(n²)", "O(1)"),
    INSERTION("Insertion Sort", "O(n²)", "O(1)"),
    SHELL("Shell Sort", "O(n log²n)", "O(1)"),
    MERGE("Merge Sort", "O(n log n)", "O(n)"),
    QUICK("Quick Sort", "O(n log n)", "O(log n)"),
    HEAP("Heap Sort", "O(n log n)", "O(1)")
}

// シンプルな enum
enum class ArrayGeneratorType(val displayName: String) {
    RANDOM("Random"),
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    PARTIALLY_SORTED("Partially Sorted"),
    FEW_UNIQUE("Few Unique")
}
```

### ルール

- 付随データ (displayName 等) を持たせる
- `when` 式で網羅的に扱う
- entries プロパティで列挙

```kotlin
// ✅ entries で列挙
SortType.entries.forEach { type ->
    println(type.displayName)
}
```

---

## Value Object vs Entity

### 区別

| 種類 | 特徴 | 例 |
|------|------|-----|
| **Value Object** | 同値性で比較 | `SortSnapshot`, `ComplexityMetrics` |
| **Entity** | ID で比較、ライフサイクルあり | `User`, `QuizResult` |

### Value Object

```kotlin
// equals は自動生成 (data class)
data class ComplexityMetrics(
    val comparisonCount: Long,
    val swapCount: Long,
    val timeComplexity: String,
    val spaceComplexity: String
)
```

### Entity

```kotlin
// ID で同一性を判断
data class QuizResult(
    val id: String,  // 識別子
    val score: Int,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (other !is QuizResult) return false
        return id == other.id  // ID のみで比較
    }
    
    override fun hashCode() = id.hashCode()
}
```

---

## Nullable

### ルール

```kotlin
// ✅ 本当に null になりうる場合のみ
data class SortState(
    val sortResult: SortResult? = null,  // ✅ 未実行時は null
    val errorMessage: String? = null     // ✅ エラーなし時は null
)

// ❌ 空コレクションで代用できる場合
data class BadState(
    val items: List<Int>? = null  // ❌ emptyList() を使う
)

// ✅ 正解
data class GoodState(
    val items: List<Int> = emptyList()
)
```

---

## copy() の使い方

### State 更新

```kotlin
// ✅ copy で新しいインスタンス
val newState = state.copy(
    isLoading = true,
    hasError = false
)

// ViewModel での使用
fun updateState(reducer: S.() -> S) {
    _state.value = _state.value.reducer()
}

// 呼び出し
updateState { copy(isLoading = true) }
```

---

## チェックリスト

新しいモデルを追加する場合:

- [ ] data class / sealed class / enum を選択
- [ ] すべて `val` で定義
- [ ] 不変コレクションを使用
- [ ] デフォルト値を設定
- [ ] Nullable は本当に必要か確認
- [ ] KDoc を追加

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **data class** | [Kotlin Data Classes](https://kotlinlang.org/docs/data-classes.html) |
| **sealed class** | [Kotlin Sealed Classes](https://kotlinlang.org/docs/sealed-classes.html) |
| **enum** | [Kotlin Enum Classes](https://kotlinlang.org/docs/enum-classes.html) |
