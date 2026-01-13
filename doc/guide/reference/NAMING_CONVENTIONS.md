---
title: 命名規則ガイド
version: 1.0.0
last_updated: 2026-01-13
maintainer: Team
status: authoritative
---

# 命名規則ガイド

> [!NOTE]
> **このファイルはプロジェクトの命名規則の正規ソース (Single Source of Truth) です。**

このガイドでは、プロジェクト全体で統一する命名規則を定義します。

---

## ファイル/クラス命名

### Kotlin ファイル

| 種類 | 命名規則 | 例 |
|------|----------|-----|
| **Screen** | `{Feature}Screen.kt` | `SortScreen.kt`, `HomeScreen.kt` |
| **ViewModel** | `{Feature}ViewModel.kt` | `SortViewModel.kt` |
| **Intent** | `{Feature}Intent.kt` | `SortIntent.kt` |
| **State** | `{Feature}State.kt` (または ViewModel 内) | `SortState` |
| **Navigation** | `{Feature}Navigation.kt` | `SortNavigation.kt` |
| **UseCase** | `{Verb}{Noun}UseCase.kt` | `ExecuteSortUseCase.kt` |
| **Algorithm** | `{Name}SortAlgorithm.kt` | `BubbleSortAlgorithm.kt` |
| **Repository** | `{Name}Repository.kt` (interface) | `SettingsRepository.kt` |
| **Repository Impl** | `{Name}RepositoryImpl.kt` | `SettingsRepositoryImpl.kt` |
| **Module (Koin)** | `{Feature}Module.kt` または `{Layer}Module.kt` | `SortFeatureModule.kt`, `DomainModule.kt` |

> [!IMPORTANT]
> ファイル名とクラス名は**完全に一致**させること。

---

## 関数命名

### Composable 関数

```kotlin
// ✅ PascalCase - 必須
@Composable
fun SortScreen()

@Composable
fun AlgorithmSelector()

// ❌ camelCase - 禁止
@Composable
fun sortScreen()
```

**参考**: [Compose API Guidelines - Naming](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md#naming)

### 通常の関数

```kotlin
// ✅ camelCase
fun executeSortAlgorithm()
fun generateRandomArray()

// ❌ PascalCase - Composable 以外では禁止
fun ExecuteSortAlgorithm()
```

### NavGraphBuilder 拡張関数

```kotlin
// ✅ Google 公式命名規則
fun NavGraphBuilder.sortDestination()      // {feature}Destination
fun NavGraphBuilder.homeDestination()

// ✅ NavController 拡張
fun NavController.navigateToSort()         // navigateTo{Feature}
fun NavController.navigateToSettings()
```

**参考**: [Encapsulate Your Navigation Code](https://developer.android.com/guide/navigation/design/encapsulate)

---

## 変数/プロパティ命名

### 基本ルール

| 種類 | 命名規則 | 例 |
|------|----------|-----|
| **変数/プロパティ** | camelCase | `currentIndex`, `sortResult` |
| **定数 (const)** | SCREAMING_SNAKE_CASE | `MAX_ARRAY_SIZE`, `DEFAULT_SPEED` |
| **companion object 定数** | SCREAMING_SNAKE_CASE | `Companion.DEFAULT_VALUE` |
| **private backing field** | `_` プレフィックス | `_state`, `_uiState` |

### StateFlow パターン

```kotlin
// ✅ 必須パターン
private val _state = MutableStateFlow(SortState())
val state: StateFlow<SortState> = _state.asStateFlow()

// ❌ 禁止 - 外部から変更可能になる
val state = MutableStateFlow(SortState())
```

### Boolean 変数

```kotlin
// ✅ is/has/can/should プレフィックス
val isLoading: Boolean
val hasError: Boolean
val canUndo: Boolean
val shouldShowDialog: Boolean

// ❌ 禁止 - 状態が不明確
val loading: Boolean
val error: Boolean
```

---

## Intent 命名

### sealed class 定義

```kotlin
sealed class SortIntent : Intent {
    // ✅ データなし - data object
    data object StartSort : SortIntent()
    data object StopSort : SortIntent()
    data object Reset : SortIntent()
    
    // ✅ データあり - data class、動詞で開始
    data class SelectAlgorithm(val type: SortType) : SortIntent()
    data class SetArraySize(val size: Int) : SortIntent()
    data class UpdateSpeed(val speed: Float) : SortIntent()
}
```

### 命名パターン

| アクション | 命名例 |
|------------|--------|
| **選択** | `Select{Target}` |
| **設定** | `Set{Property}`, `Update{Property}` |
| **開始/停止** | `Start{Action}`, `Stop{Action}`, `Pause{Action}`, `Resume{Action}` |
| **ナビゲーション** | `Navigate{Direction}`, `GoBack` |
| **CRUD** | `Create{Item}`, `Delete{Item}`, `Load{Items}` |

---

## パッケージ命名

### 構造

```
dotnet.sort.{layer}.{feature}.{subpackage}

例:
dotnet.sort.domain.algorithm
dotnet.sort.domain.model
dotnet.sort.domain.usecase
dotnet.sort.data.generator
dotnet.sort.presentation.feature.sort
dotnet.sort.presentation.feature.sort.components
dotnet.sort.presentation.designsystem.tokens
```

### ルール

- すべて**小文字**
- 単語の区切りなし (underscoreも使わない)
- 複合語は連結: `designsystem` (not `design_system`)

---

## テストクラス命名

### クラス名

```kotlin
// {テスト対象クラス名}Test
class BubbleSortAlgorithmTest
class SortViewModelTest
class ArrayGeneratorImplTest
```

### メソッド名

```kotlin
// バッククォート + GIVEN/WHEN/THEN
@Test
fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`()

@Test
fun `GIVEN empty input WHEN generate is called THEN returns empty list`()
```

---

## 禁止事項

### 避けるべき命名

| 禁止 | 理由 | 代替 |
|------|------|------|
| `Manager`, `Handler`, `Helper` | 曖昧で責務が不明確 | 具体的な名前 (`Generator`, `Repository`) |
| `Data`, `Info` サフィックス | 情報が増えない | 具体的な名前 (`Metrics`, `Details`) |
| 1文字変数 (ループ以外) | 意味不明 | 説明的な名前 |
| 省略形 | 読みにくい | `algorithm` not `alg` |
| 型名の繰り返し | 冗長 | `sortResult` not `sortResultData` |

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Kotlin Coding Conventions** | [Kotlin Official - Naming Rules](https://kotlinlang.org/docs/coding-conventions.html#naming-rules) |
| **Compose API Guidelines** | [Compose Naming](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md#naming) |
| **Navigation Encapsulation** | [Android - Encapsulate Navigation](https://developer.android.com/guide/navigation/design/encapsulate) |
