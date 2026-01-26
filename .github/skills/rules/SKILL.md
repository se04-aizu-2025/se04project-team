---
name: rules
description: Provides information about project rules, coding conventions, and fundamental guidelines. Use this when writing code, reviewing, or understanding project standards.
---

You are the rules skill for the DNSort project. When activated, provide comprehensive information about project rules and coding conventions:

## 1. 命名規則

### クイックリファレンス

| 種類 | 規則 | 例 |
|------|------|-----|
| **Screen** | `{Feature}Screen.kt` | `SortScreen.kt` |
| **ViewModel** | `{Feature}ViewModel.kt` | `SortViewModel.kt` |
| **UseCase** | `{Verb}{Noun}UseCase.kt` | `ExecuteSortUseCase.kt` |
| **Composable関数** | PascalCase | `SortScreen()` |
| **通常関数** | camelCase | `executeSortAlgorithm()` |
| **Boolean変数** | is/has/can プレフィックス | `isLoading`, `hasError` |

## 2. データモデル

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
    data object StartSort : SortIntent()
    data class SelectAlgorithm(val type: SortType) : SortIntent()
}
```

## 3. 禁止事項

### ❌ State で var を使う
```kotlin
// ❌ ダメ
data class SortState(
    var algorithm: SortType = SortType.BUBBLE,  // var は禁止
)

// ✅ 良い
data class SortState(
    val algorithm: SortType = SortType.BUBBLE,
)
```

### ❌ Screen に NavController を渡す
```kotlin
// ❌ ダメ
@Composable
fun SortScreen(navController: NavController) { ... }

// ✅ 良い
@Composable
fun SortScreen(onNavigateToSettings: () -> Unit) { ... }
```

### ❌ ViewModel メソッドを直接呼び出す
```kotlin
// ❌ ダメ
viewModel.executeSort()

// ✅ 良い
viewModel.send(StartSort)
```

### ❌ ハードコード色/サイズ (Design Token を使う)
```kotlin
// ❌ ダメ
Text(color = Color.Red, fontSize = 16.sp)

// ✅ 良い
Text(color = DesignToken.ErrorColor, fontSize = DesignToken.BodyFontSize)
```

## 4. アーキテクチャルルール

### MVI パターン
```
User Input → Intent → ViewModel → State → UI
```

- **Intent**: ユーザーの意図を表現 (data object / data class)
- **State**: UIの状態を表現 (data class, すべて val)
- **ViewModel**: Intentを受け取り、Stateを更新

### レイヤー依存関係
```
Presentation → Domain ← Data
```

- Presentation は Domain に依存
- Data は Domain に依存
- Domain は他のレイヤーに依存しない

Always reference the FUNDAMENTALS.md file in the doc/guide/ directory for the most current information.