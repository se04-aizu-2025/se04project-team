# Preview ガイド

このガイドでは、Compose Multiplatform におけるプレビュー機能の実装規則を定義します。

---

## 概要

### 目的

- **開発効率**: 実機ビルドなしで UI を確認
- **ドキュメント化**: コンポーネントの使用例や状態を可視化
- **品質保証**: エッジケース（長文、エラー状態）の視覚的確認

---

## 実装規則

### 基本構造

全ての `commonMain` ソースセット内で、`androidx` パッケージの標準アノテーションを使用します。

```kotlin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Preview
@Composable
private fun SortCardPreview() {
    SortTheme {
        SortCard(text = "Sample")
    }
}
```

### 必須設定

| 項目 | 設定内容 |
|------|----------|
| **可視性** | `private` (API汚染防止) |
| **テーマ** | `SortTheme` でラップする |
| **配置** | 実装ファイル (`.kt`) の末尾、または専用ファイル |

---

## ベストプラクティス

### 1. ステートレス設計 (Stateless)

プレビュー可能なコンポーネントは**ステートレス**でなければなりません。
`ViewModel` や `UseCase` に依存せず、純粋なデータ (`State`) とコールバック (`onAction`) のみを受け取ります。

```kotlin
// ✅ OK: プレビュー可能
@Composable
fun SortContent(
    state: SortUiState,
    onAction: (SortAction) -> Unit
) { ... }

// ❌ NG: プレビュー不可 (ViewModel依存)
@Composable
fun SortScreen(viewModel: SortViewModel) { ... }
```

### 2. ダミーデータの提供

複雑なデータ構造は、プレビュー専用のダミーデータを作成して渡します。

```kotlin
@Preview
@Composable
private fun SortListPreview() {
    SortTheme {
        SortList(
            items = List(5) { id -> "Item $id" } // ダミーデータ
        )
    }
}
```

### 3. @PreviewParameter (オプション)

複数の状態を一度に確認したい場合は、`@PreviewParameter` の使用を検討してください。
ただし、単に複数の `@Preview` 関数を並べる方が可読性が高い場合もあります。

---

## トラブルシューティング

### Unresolved reference: tooling

ビルドスクリプトの依存関係を確認してください。

```kotlin
// build.gradle.kts
sourceSets {
    commonMain.dependencies {
        implementation(libs.ui.tooling.preview) // 必須
    }
}
```

### ClassNotFoundException: ComposeViewAdapter

Android デバッグ用依存関係が不足しています。

```kotlin
// build.gradle.kts
dependencies {
    debugImplementation(libs.ui.tooling) // 必須 (sourceSetsの外)
}
```

---

## チェックリスト

新しいコンポーネントを作成する場合:

- [ ] ステートレスコンポーネントとして分離されているか
- [ ] `private` な `@Preview` 関数が存在するか
- [ ] `SortTheme` が適用されているか
- [ ] ダミーデータで表示が崩れていないか

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Compose Multiplatform** | [Previews in common code](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-ide-preview.html) |
| **Android Developers** | [Compose Previews](https://developer.android.com/develop/ui/compose/tooling/previews) |
