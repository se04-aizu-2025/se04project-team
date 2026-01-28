---
title: リファレンス
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ベストプラクティスガイド](../README.md)"
---

# リファレンス

このディレクトリには、プロジェクトの詳細な仕様・規約を記載したリファレンスドキュメントが格納されています。

## このディレクトリの目的

- タスクガイドでは扱いきれない**詳細な仕様**を提供
- プロジェクト全体で適用される**規約の正規ソース**
- 深い理解が必要な時の**リファレンス資料**

## 対象読者

- タスクガイドの手順だけでは不十分で、**背景や理由を理解したい**人
- プロジェクト規約の**正確な定義**を確認したい人
- 新しいパターンを追加する際に**既存の設計を理解したい**人

---

## リファレンス一覧

### 📝 命名・コード規約

| ドキュメント | 内容 | 正規ソース |
|-------------|------|-----------|
| [NAMING_CONVENTIONS.md](./NAMING_CONVENTIONS.md) | ファイル・クラス・変数の命名規則 | ✅ 命名規則の正規ソース |
| [DATA_MODELS.md](./DATA_MODELS.md) | data class / sealed class / enum の使い分け | |
| [KDOC.md](./KDOC.md) | ドキュメントコメントの書き方 | |

### 🏗️ アーキテクチャ

| ドキュメント | 内容 |
|-------------|------|
| [DESIGN_PATTERNS.md](./DESIGN_PATTERNS.md) | Strategy / Factory / Template Method パターン |
| [USECASE.md](./USECASE.md) | UseCase の設計・命名・責務 |
| [DEPENDENCY_INJECTION.md](./DEPENDENCY_INJECTION.md) | Koinモジュール・スコープの規約 |

### 🎨 UI / Presentation

| ドキュメント | 内容 |
|-------------|------|
| [VIEWMODEL_SCREEN.md](./VIEWMODEL_SCREEN.md) | ViewModel + Screen の構造 |
| [STATE_MANAGEMENT.md](./STATE_MANAGEMENT.md) | MVIパターンの詳細 |
| [COMPOSE_COMPONENTS.md](./COMPOSE_COMPONENTS.md) | Atomic Design、Modifier規約 |
| [PREVIEWS.md](./PREVIEWS.md) | Preview 実装規約、トラブルシューティング |
| [NAVIGATION.md](./NAVIGATION.md) | NavGraphBuilder、Routes の設計 |

### ⚡ 処理

| ドキュメント | 内容 |
|-------------|------|
| [ASYNC_FLOW.md](./ASYNC_FLOW.md) | Coroutines / StateFlow の使い方 |
| [ERROR_HANDLING.md](./ERROR_HANDLING.md) | Result型、エラー処理規約 |

### ✅ テスト

| ドキュメント | 内容 |
|-------------|------|
| [TESTING.md](./TESTING.md) | テストの詳細規約 |

### 🧮 ドメイン固有

| ドキュメント | 内容 |
|-------------|------|
| [ALGORITHM.md](./ALGORITHM.md) | ソートアルゴリズム実装の詳細 |

---

## 使い方

1. タスクガイド実行中に詳細が必要になったらこちらを参照
2. 新しい規約を追加する場合は、関連するリファレンスを更新
3. **正規ソース**の表記があるドキュメントはその情報の唯一の定義場所

---

## 新しいリファレンスを追加する

[DOCUMENTATION_GUIDELINES.md](../../DOCUMENTATION_GUIDELINES.md) の「リファレンス作成手順」を参照してください。
