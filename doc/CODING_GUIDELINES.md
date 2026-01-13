---
title: コーディングガイドライン
version: 1.1.0
last_updated: 2026-01-13
maintainer: Team
---

# コーディングガイドライン

このドキュメントでは、本プロジェクトのコーディング規約とベストプラクティスを定義します。

> [!TIP]  
> **より詳細なタスク別ガイドは `doc/guide/` を参照してください。**  
> - [基礎ルール](./guide/FUNDAMENTALS.md) - 命名・データモデル・禁止事項
> - [タスク別ガイド](./guide/README.md) - 画面追加、テスト追加 等

---

## 目次

1. [一般原則](#一般原則)
2. [命名規則](#命名規則)
3. [Compose 規約](#compose-規約)
4. [MVI 規約](#mvi-規約)
5. [KDoc 規約](#kdoc-規約)
6. [ファイル構成](#ファイル構成)

---

## 一般原則

### SOLID 原則

| 原則 | 説明 | 適用例 |
|------|------|--------|
| **S** - 単一責任 | クラスは一つの責務のみ持つ | UseCase: 1機能1クラス |
| **O** - 開放閉鎖 | 拡張に開き、修正に閉じる | `BaseSortAlgorithm` の継承 |
| **L** - リスコフ置換 | 派生クラスは基底クラスと置換可能 | `SortAlgorithm` interface |
| **I** - インターフェース分離 | 必要最小限のインターフェース | Repository の分離 |
| **D** - 依存性逆転 | 抽象に依存、具象に依存しない | Domain → Data の依存方向 |

### 不変性 (Immutability)

```kotlin
// ✅ Good - イミュータブル
val sortedList = input.sorted()
data class SortState(val isLoading: Boolean = false)

// ❌ Bad - ミュータブル
var sortedList = mutableListOf<Int>()
```

### Null 安全性

```kotlin
// ✅ Good - Nullを避けるか、明示的に扱う
fun findUser(id: String): User? = repository.findById(id)
val name = user?.name ?: "Unknown"

// ❌ Bad - 暗黙的なNull
fun getUser(): User = users[id]!! // 危険
```

---

## 命名規則

> [!IMPORTANT]
> **命名規則の正規ソースは [NAMING_CONVENTIONS.md](./guide/reference/NAMING_CONVENTIONS.md) です。**

### クイックリファレンス

| 要素 | 規則 | 例 |
|------|------|-----|
| クラス | PascalCase | `SortViewModel` |
| 関数 | camelCase | `executeSorting()` |
| 変数 | camelCase | `currentIndex` |
| 定数 | SCREAMING_SNAKE_CASE | `MAX_ARRAY_SIZE` |

詳細は [命名規則ガイド](./guide/reference/NAMING_CONVENTIONS.md) を参照してください。

### 関数

```kotlin
// ✅ Good - 単一式関数
fun double(x: Int): Int = x * 2

// ✅ Good - 名前付き引数
createUser(
    name = "John",
    email = "john@example.com"
)

// ✅ Good - デフォルト引数
fun sort(
    input: List<Int>,
    ascending: Boolean = true
): List<Int>
```

### when 式

```kotlin
// ✅ Good - exhaustive when
when (sortType) {
    SortType.BUBBLE -> BubbleSortAlgorithm()
    SortType.SELECTION -> SelectionSortAlgorithm()
    // ... 全ケースを網羅
}
```

---

## Compose 規約

### Composable 関数

```kotlin
// ✅ Good
@Composable
fun SortBar(
    value: Int,
    maxValue: Int,
    state: BarState = BarState.Default,
    modifier: Modifier = Modifier  // 最初のオプション引数
) {
    // 実装
}

// ❌ Bad - Modifier が最後
@Composable
fun SortBar(
    value: Int,
    state: BarState,
    modifier: Modifier = Modifier  // 他のオプション引数の後
)
```

### State 管理

```kotlin
// ✅ Good - ViewModel から State を取得
@Composable
fun SortScreen(viewModel: SortViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    // state を使用
}

// ❌ Bad - Composable 内で remember + mutableState
@Composable
fun SortScreen() {
    var count by remember { mutableStateOf(0) } // 複雑な状態はNG
}
```

### Preview

```kotlin
@Preview
@Composable
private fun SortBarPreview() {
    SortTheme {
        SortBar(value = 50, maxValue = 100)
    }
}
```

---

## MVI 規約

### Intent

```kotlin
// ✅ Good - 明確な Intent 定義
sealed class SortIntent : Intent {
    data class SelectAlgorithm(val type: SortType) : SortIntent()
    data object StartSort : SortIntent()
    data object ResetSort : SortIntent()
}
```

### State

```kotlin
// ✅ Good - イミュータブルな State
data class SortState(
    val isLoading: Boolean = false,
    val algorithm: SortType = SortType.BUBBLE,
    val currentNumbers: List<Int> = emptyList()
) : UiState

// ❌ Bad - Mutable な State
data class SortState(
    var isLoading: Boolean = false  // var は禁止
)
```

### ViewModel

```kotlin
class SortViewModel : BaseViewModel<SortState, SortIntent>(SortState()) {
    
    override fun send(intent: SortIntent) {
        when (intent) {
            is SortIntent.SelectAlgorithm -> updateState { 
                copy(algorithm = intent.type) 
            }
            // ...
        }
    }
}
```

---

## KDoc 規約

### 必須対象

- Public クラス
- Public 関数
- Public プロパティ

### フォーマット

```kotlin
/**
 * ソートを実行します。
 *
 * @param type 使用するソートアルゴリズムの種類
 * @param input ソート対象の整数リスト
 * @return [SortResult] ソート結果
 * @throws IllegalArgumentException 入力が空の場合
 */
fun execute(type: SortType, input: List<Int>): SortResult
```

### パッケージドキュメント

各パッケージに `package-info.kt` を作成：

```kotlin
/**
 * ソート機能モジュール。
 *
 * このパッケージは以下を提供します：
 * - SortScreen
 * - SortViewModel
 * - SortIntent / SortState
 */
package dotnet.sort.presentation.feature.sort
```

---

## ファイル構成

### Feature モジュール

```
feature/sort/
├── SortScreen.kt       # Composable Screen
├── SortViewModel.kt    # ViewModel (MVI)
├── SortIntent.kt       # Intent 定義
├── SortFeatureModule.kt # DI モジュール
├── package-info.kt     # パッケージドキュメント
└── components/         # 画面固有コンポーネント
    ├── AlgorithmSelector.kt
    ├── SortControlPanel.kt
    └── SortVisualizer.kt
```

### Design System

```
designsystem/
├── tokens/            # デザイントークン
│   ├── ColorTokens.kt
│   ├── SpacingTokens.kt
│   └── AnimationTokens.kt
├── theme/             # テーマ
│   ├── Theme.kt
│   ├── Color.kt
│   └── Typography.kt
└── components/        # Atomic コンポーネント
    ├── atoms/
    └── molecules/
```
