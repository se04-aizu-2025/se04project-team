# KDoc / ドキュメントガイド

このガイドでは、コードドキュメント (KDoc) の記述規則を定義します。

---

## 必須ドキュメント対象

| 対象 | 必須度 | 理由 |
|------|--------|------|
| **public クラス** | 必須 | API の説明 |
| **public 関数** | 必須 | 使用方法の説明 |
| **public プロパティ** | 必須 | 意味の説明 |
| **internal クラス/関数** | 推奨 | チーム内理解 |
| **private** | 任意 | 複雑な場合のみ |

---

## 基本構造

### クラス

```kotlin
/**
 * バブルソートアルゴリズムの実装。
 *
 * 隣接する要素を比較・交換しながらソートを行う。
 * 時間計算量: O(n²)、空間計算量: O(1)
 *
 * @see SortAlgorithm
 * @see BaseSortAlgorithm
 */
class BubbleSortAlgorithm : BaseSortAlgorithm() {
    // ...
}
```

### 関数

```kotlin
/**
 * 指定されたソートアルゴリズムでリストをソートする。
 *
 * @param type 使用するソートアルゴリズムの種類
 * @param input ソート対象の整数リスト
 * @return ソート結果 (最終配列、ステップ、メトリクス)
 * @throws IllegalArgumentException 入力が空の場合
 */
fun execute(type: SortType, input: List<Int>): SortResult
```

### プロパティ

```kotlin
/**
 * 現在選択されているソートアルゴリズム。
 */
val algorithm: SortType

/**
 * ソート実行中かどうかを示すフラグ。
 * true の場合、配列の変更は禁止される。
 */
val isPlaying: Boolean
```

---

## KDoc タグ

### 必須タグ

| タグ | 用途 | 必須条件 |
|------|------|----------|
| `@param` | パラメータ説明 | パラメータがある場合 |
| `@return` | 戻り値説明 | Unit 以外の戻り値 |
| `@throws` | スロー例外 | 例外をスローする場合 |

### 任意タグ

| タグ | 用途 |
|------|------|
| `@see` | 関連クラス/関数への参照 |
| `@since` | 追加バージョン |
| `@sample` | 使用例 |
| `@property` | プロパティ説明 (data class) |

---

## Composable 関数

### Screen

```kotlin
/**
 * ソート可視化画面。
 *
 * アルゴリズムの選択、実行、ステップ再生を提供する。
 *
 * @param viewModel 画面の状態を管理する ViewModel
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier レイアウト調整用の Modifier
 */
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
)
```

### Component

```kotlin
/**
 * ソートバー (配列の各要素を表すバー)。
 *
 * @param value バーが表す値
 * @param maxValue 配列内の最大値 (高さ計算用)
 * @param state バーの状態 (ハイライト等)
 * @param modifier レイアウト調整用の Modifier
 */
@Composable
fun SortBar(
    value: Int,
    maxValue: Int,
    state: BarState = BarState.Default,
    modifier: Modifier = Modifier
)
```

---

## Intent / State

### Intent

```kotlin
/**
 * ソート画面のユーザーインテント。
 */
sealed class SortIntent : Intent {
    /**
     * ソートを開始する。
     */
    data object StartSort : SortIntent()
    
    /**
     * 使用するアルゴリズムを選択する。
     *
     * @property type 選択するアルゴリズムの種類
     */
    data class SelectAlgorithm(val type: SortType) : SortIntent()
}
```

### State

```kotlin
/**
 * ソート画面の UI 状態。
 *
 * @property algorithm 現在選択されているアルゴリズム
 * @property arraySize 生成する配列のサイズ
 * @property isLoading ソート実行中フラグ
 * @property sortResult ソート結果 (実行後に設定)
 */
data class SortState(
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 20,
    val isLoading: Boolean = false,
    val sortResult: SortResult? = null
) : UiState
```

---

## パッケージドキュメント

### package-info.kt

```kotlin
// package-info.kt

/**
 * ソート機能の Presentation 層。
 *
 * ## 構成
 * - [SortScreen]: メイン画面
 * - [SortViewModel]: 状態管理
 * - [SortIntent]: ユーザーインテント
 *
 * ## 使用例
 * ```kotlin
 * SortScreen(
 *     onBackClick = { navController.popBackStack() }
 * )
 * ```
 */
package dotnet.sort.presentation.feature.sort
```

---

## 禁止事項

### 避けるべきドキュメント

```kotlin
// ❌ 禁止 - コードの繰り返し
/**
 * Sort the list.  // 関数名と同じ
 */
fun sortList()

// ❌ 禁止 - 情報がない
/**
 * Returns the result.  // 何の result?
 */
fun getResult()

// ❌ 禁止 - 型情報の繰り返し
/**
 * @param size The Int size.  // Int は型から分かる
 */
fun generate(size: Int)
```

### 良いドキュメント

```kotlin
// ✅ 具体的で有用
/**
 * 隣接要素を比較・交換してリストをソートする。
 */
fun sortList()

/**
 * ソート実行結果 (最終配列、ステップ履歴、メトリクス) を返す。
 */
fun getResult()

/**
 * @param size 生成する配列の要素数 (1-1000)
 */
fun generate(size: Int)
```

---

## チェックリスト

新しいクラス/関数を追加する場合:

- [ ] public 要素に KDoc を追加
- [ ] `@param` を全パラメータに追加
- [ ] `@return` を非 Unit 関数に追加
- [ ] `@throws` を例外スロー関数に追加
- [ ] コードの繰り返しを避ける
- [ ] 具体的で有用な説明を書く

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **KDoc** | [Kotlin Documentation](https://kotlinlang.org/docs/kotlin-doc.html) |
| **Dokka** | [Dokka - Documentation Engine](https://github.com/Kotlin/dokka) |
