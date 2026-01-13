# Contributing / コントリビューションガイド

DNSort へのコントリビューションに興味を持っていただきありがとうございます！

## 開発フロー

### 1. ブランチの作成

```bash
git checkout develop
git pull origin develop
git checkout -b feature/{PR番号}
```

ブランチ名は `doc/DEVELOPMENT_PLAN.md` の PR 番号に対応させてください。

### 2. 開発

- コーディング規約に従う (ktlint, detekt が自動チェック)
- 変更に対応するテストを追加
- KDoc でパブリック API をドキュメント化

> [!TIP]  
> **タスク別ガイドを参照:** `doc/guide/tasks/` に各タスク (画面追加、テスト追加 等) の詳細手順があります。

### 3. コミット

```bash
git add .
git commit -m "feat: 機能の説明"
```

コミット時に pre-commit hook が自動実行されます。

### 4. プッシュ

```bash
git push origin feature/{PR番号}
```

### 5. プルリクエスト

GitHub でプルリクエストを作成し、テンプレートに従って記入してください。

---

## コーディング規約

### 一般原則

- **SOLID原則** に従う
- **不変性** を優先: `var` より `val` を使用
- **Nullable** は最小限に

### Kotlin スタイル

```kotlin
// ✅ Good
val userName: String = getName()

// ❌ Bad
var userName: String? = null
```

### Compose スタイル

- Composable 関数は **PascalCase** で命名
- **Modifier** は常に最初のオプション引数として受け取る

```kotlin
@Composable
fun SortBar(
    value: Int,
    modifier: Modifier = Modifier  // 最初のオプション引数
)
```

### KDoc

パブリック API には必ず KDoc を付ける：

```kotlin
/**
 * ソートを実行します。
 *
 * @param type 使用するソートアルゴリズムの種類
 * @param input ソート対象の整数リスト
 * @return ソート結果
 */
fun execute(type: SortType, input: List<Int>): SortResult
```

---

## テスト

### テストの書き方

Given-When-Then パターンを使用：

```kotlin
@Test
fun `GIVEN unsorted list WHEN sort THEN returns sorted list`() {
    // Given
    val input = listOf(5, 3, 1)
    
    // When
    val result = algorithm.sort(input)
    
    // Then
    assertEquals(listOf(1, 3, 5), result.finalArray)
}
```

詳細は `doc/TESTING_GUIDELINES.md` または `doc/guide/tasks/ADD_TEST.md` を参照してください。

### テスト実行

```bash
./gradlew allTests
```

---

## ドキュメント

- 機能追加時は関連ドキュメントも更新
- `doc/` ディレクトリを確認

---

## 質問・サポート

Issue を作成するか、プロジェクトメンテナに連絡してください。
