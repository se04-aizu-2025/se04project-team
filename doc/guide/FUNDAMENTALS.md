---
title: 基礎ルール
version: 1.1.1
last_updated: 2026-01-17
maintainer: Team
---

# 基礎ルール

このドキュメントは、すべての開発タスクで適用される基礎ルールをまとめたものです。

---

## 命名規則

> [!IMPORTANT]
> **詳細な命名規則は [NAMING_CONVENTIONS.md](./reference/NAMING_CONVENTIONS.md) を参照してください。**

### クイックリファレンス

| 種類 | 規則 | 例 |
|------|------|-----|
| **Screen** | `{Feature}Screen.kt` | `SortScreen.kt` |
| **ViewModel** | `{Feature}ViewModel.kt` | `SortViewModel.kt` |
| **UseCase** | `{Verb}{Noun}UseCase.kt` | `ExecuteSortUseCase.kt` |
| **Composable関数** | PascalCase | `SortScreen()` |
| **通常関数** | camelCase | `executeSortAlgorithm()` |
| **Boolean変数** | is/has/can プレフィックス | `isLoading`, `hasError` |

---

## データモデル

### data class

```kotlin
// ✅ すべて val + デフォルト値 + 不変コレクション
data class SortState(
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 20,
    val isLoading: Boolean = false,
    val items: List<Int> = emptyList()  // List, not MutableList
)
```

### sealed class

```kotlin
// ✅ Intent / Result / Route に使用
sealed class SortIntent : Intent {
    data object StartSort : SortIntent()           // データなし
    data class SelectAlgorithm(val type: SortType) : SortIntent()  // データあり
}
```

### enum class

```kotlin
// ✅ 付随データを持たせる
enum class SortType(
    val displayName: String,
    val timeComplexity: String
) {
    BUBBLE("Bubble Sort", "O(n²)"),
    QUICK("Quick Sort", "O(n log n)")
}
```

---

## KDoc

### 必須対象

- public クラス
- public 関数
- public プロパティ

### 基本形式

```kotlin
/**
 * ソートアルゴリズムを実行する。
 *
 * @param type アルゴリズムの種類
 * @param input ソート対象のリスト
 * @return ソート結果
 */
fun execute(type: SortType, input: List<Int>): SortResult
```

---

## 禁止事項

### コード

| 禁止 | 理由 | 代替 |
|------|------|------|
| `var` in data class | 不変性違反 | `val` を使う |
| `MutableList` in State | 不変性違反 | `List` を使う |
| ハードコード色/サイズ | 保守性低下 | Design Token を使う |
| Screen に NavController | テスト困難 | コールバックで抽象化 |
| ViewModel メソッド直接呼び出し | MVI 違反 | `send(Intent)` を使う |
| GlobalScope | ライフサイクル無視 | `viewModelScope` を使う |
| 例外を握りつぶす | デバッグ困難 | 適切にハンドル |
| Raw Material3 Component | 一貫性欠如 | Design System (`SortText` 等) を使う |
| 完全修飾名の使用 (コード本体) | 可読性低下 | import または typealias |
| 深い if/when ネスト (3段以上) | 可読性低下 | ガード節・分割関数 |
| 1関数に大量の処理 | 保守性低下 | 小さな関数に分割 |

### 命名

| 禁止 | 理由 | 代替 |
|------|------|------|
| `Manager`, `Handler`, `Helper` | 曖昧 | 具体的な名前 |
| 1文字変数 (ループ以外) | 意味不明 | 説明的な名前 |
| 省略形 | 読みにくい | `algorithm` not `alg` |

---

## StateFlow パターン

```kotlin
// ✅ 必須パターン
private val _state = MutableStateFlow(SortState())
val state: StateFlow<SortState> = _state.asStateFlow()

// State 更新
fun updateState(reducer: SortState.() -> SortState) {
    _state.value = _state.value.reducer()
}

// 使用
updateState { copy(isLoading = true) }
```

---

## Modifier パターン

```kotlin
// ✅ 外部 Modifier を最初に適用
@Composable
fun SortBar(
    value: Int,
    modifier: Modifier = Modifier  // 最後のパラメータ
) {
    Box(
        modifier = modifier         // 外部が先
            .height(100.dp)         // 内部が後
    )
}
```

---

## テスト命名

```kotlin
// ✅ GIVEN/WHEN/THEN パターン
@Test
fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`() {
    // Given
    val input = listOf(5, 3, 1)
    
    // When
    val result = algorithm.sort(input)
    
    // Then
    assertEquals(listOf(1, 3, 5), result.finalArray)
}
```

---

## 次のステップ

**タスクを実行する際は、該当するガイドを参照してください:**

- [画面を追加する](./tasks/ADD_SCREEN.md)
- [アルゴリズムを追加する](./tasks/ADD_ALGORITHM.md)
- [UIコンポーネントを追加する](./tasks/ADD_UI_COMPONENT.md)
- [テストを追加する](./tasks/ADD_TEST.md)
