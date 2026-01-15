---
title: ベストプラクティスガイド
version: 1.1.0
last_updated: 2026-01-13
maintainer: Team
---

# 📚 ベストプラクティスガイド

開発者向けのベストプラクティスガイドです。

---

## このディレクトリの目的

- **プロジェクト特有の規約・パターン**を集約
- **タスクベースのガイド**で日常開発を効率化
- **詳細リファレンス**で深い理解をサポート

---

## ディレクトリ構成

| ディレクトリ/ファイル | 内容 |
|----------------------|------|
| `ONBOARDING.md` | 新規参加者向けアーキテクチャ概要 |
| `FUNDAMENTALS.md` | 基礎ルール（サマリー） |
| [tasks/](./tasks/README.md) | タスク別ステップガイド |
| [reference/](./reference/README.md) | 詳細リファレンス |

---

## 読み方フロー

```
新規参加者:
  1. ONBOARDING.md (1回)     → アーキテクチャ理解
  2. FUNDAMENTALS.md (1回)  → 基本ルール習得

日常開発:
  3. tasks/*.md             → 必要なタスクのみ参照

深掘り:
  4. reference/*.md         → 詳細が必要な時のみ
```

---

## 📖 最初に読むドキュメント

| ドキュメント | 内容 | 読むタイミング |
|--------------|------|----------------|
| [ONBOARDING.md](./ONBOARDING.md) | アーキテクチャ概要、技術スタック、プロジェクト構造 | 参加時に1回 |
| [FUNDAMENTALS.md](./FUNDAMENTALS.md) | 命名規則、データモデル、禁止事項 | 参加時に1回 |

---

## 🛠️ タスク別ガイド

日常の開発タスクを実行する時に参照してください。

### 基本タスク

| タスク | ガイド |
|--------|--------|
| **新しい画面を追加** | [tasks/ADD_SCREEN.md](./tasks/ADD_SCREEN.md) |
| **新しいアルゴリズムを追加** | [tasks/ADD_ALGORITHM.md](./tasks/ADD_ALGORITHM.md) |
| **UIコンポーネントを追加** | [tasks/ADD_UI_COMPONENT.md](./tasks/ADD_UI_COMPONENT.md) |
| **テストを追加** | [tasks/ADD_TEST.md](./tasks/ADD_TEST.md) |

### ドメイン/データタスク

| タスク | ガイド |
|--------|--------|
| **UseCase を追加** | [tasks/ADD_USECASE.md](./tasks/ADD_USECASE.md) |
| **Repository を追加** | [tasks/ADD_REPOSITORY.md](./tasks/ADD_REPOSITORY.md) |
| **データジェネレーターを追加** | [tasks/ADD_DATA_GENERATOR.md](./tasks/ADD_DATA_GENERATOR.md) |
| **データ永続化を追加** | [tasks/ADD_PERSISTENCE.md](./tasks/ADD_PERSISTENCE.md) |

### アーキテクチャタスク

| タスク | ガイド |
|--------|--------|
| **Feature Module を追加** | [tasks/ADD_FEATURE_MODULE.md](./tasks/ADD_FEATURE_MODULE.md) |
| **Koin Module を追加** | [tasks/ADD_KOIN_MODULE.md](./tasks/ADD_KOIN_MODULE.md) |

### 設計/拡張タスク

| タスク | ガイド |
|--------|--------|
| **Design Token を追加** | [tasks/ADD_DESIGN_TOKEN.md](./tasks/ADD_DESIGN_TOKEN.md) |
| **多言語対応を追加** | [tasks/ADD_LOCALIZATION.md](./tasks/ADD_LOCALIZATION.md) |
| **CLI コマンドを追加** | [tasks/ADD_CLI_COMMAND.md](./tasks/ADD_CLI_COMMAND.md) |

### トラブルシューティング

| タスク | ガイド |
|--------|--------|
| **デバッグ・問題解決** | [tasks/DEBUG_GUIDE.md](./tasks/DEBUG_GUIDE.md) |


## 📋 リファレンス

詳細な仕様が必要な時に参照してください。

### アーキテクチャ

| ガイド | 内容 |
|--------|------|
| [DESIGN_PATTERNS.md](./reference/DESIGN_PATTERNS.md) | Strategy / Factory / Template Method |
| [USECASE.md](./reference/USECASE.md) | UseCase 設計・命名 |
| [DEPENDENCY_INJECTION.md](./reference/DEPENDENCY_INJECTION.md) | Koin モジュール・スコープ |

### UI / Presentation

| ガイド | 内容 |
|--------|------|
| [VIEWMODEL_SCREEN.md](./reference/VIEWMODEL_SCREEN.md) | ViewModel + Screen 構造 |
| [STATE_MANAGEMENT.md](./reference/STATE_MANAGEMENT.md) | MVI パターン詳細 |
| [COMPOSE_COMPONENTS.md](./reference/COMPOSE_COMPONENTS.md) | Atomic Design、Modifier |
| [NAVIGATION.md](./reference/NAVIGATION.md) | NavGraphBuilder、Routes |

### 処理

| ガイド | 内容 |
|--------|------|
| [ASYNC_FLOW.md](./reference/ASYNC_FLOW.md) | Coroutines / StateFlow |
| [ERROR_HANDLING.md](./reference/ERROR_HANDLING.md) | Result型、検証 |

### コード規約

| ガイド | 内容 |
|--------|------|
| [NAMING_CONVENTIONS.md](./reference/NAMING_CONVENTIONS.md) | 命名規則詳細 |
| [DATA_MODELS.md](./reference/DATA_MODELS.md) | data class / sealed class / enum |
| [KDOC.md](./reference/KDOC.md) | ドキュメントコメント |
| [TESTING.md](./reference/TESTING.md) | テスト詳細規則 |
| [ALGORITHM.md](./reference/ALGORITHM.md) | アルゴリズム実装詳細 |

---

## 🚀 クイックリファレンス

### よく使うルール

| 種類 | 規則 |
|------|------|
| Composable | PascalCase: `SortScreen()` |
| 通常関数 | camelCase: `executeSortAlgorithm()` |
| ViewModel | `{Feature}ViewModel` |
| Intent (データなし) | `data object StartSort` |
| Intent (データあり) | `data class SelectAlgorithm(val type)` |
| Boolean | `isLoading`, `hasError` |

### MVI フロー

```
User Input → Intent → ViewModel → State → UI
```

### 禁止事項

- ❌ State で `var` を使う
- ❌ `MutableList` を State に含める
- ❌ Screen に NavController を渡す
- ❌ ViewModel メソッドを直接呼び出す
- ❌ ハードコード色/サイズ

---

## 📎 外部リファレンス

| ドキュメント | URL |
|--------------|-----|
| Kotlin Coding Conventions | https://kotlinlang.org/docs/coding-conventions.html |
| Android App Architecture | https://developer.android.com/topic/architecture |
| Compose API Guidelines | https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md |
| Koin | https://insert-koin.io/ |
