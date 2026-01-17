---
title: ドキュメント一覧
version: 1.1.1
last_updated: 2026-01-17
maintainer: Team
---

# 📚 ドキュメント一覧

このディレクトリには、プロジェクトに関する各種ドキュメントが格納されています。

## このドキュメントの目的

DNSortプロジェクトの**すべてのドキュメントへのエントリーポイント**です。

- 必要なドキュメントを素早く見つける
- 対象読者に応じた適切な情報にアクセスする
- プロジェクトの全体像を把握する

## 対象読者別ガイド

| あなたは誰？ | 最初に読むべきドキュメント |
|-------------|---------------------------|
| **新規参加者** | [ONBOARDING.md](./guide/ONBOARDING.md) → [FUNDAMENTALS.md](./guide/FUNDAMENTALS.md) |
| **日常開発者** | [guide/tasks/](./guide/tasks/README.md) のタスク別ガイド |
| **エンドユーザー** | [USER_GUIDE.md](./USER_GUIDE.md) |
| **ドキュメント作成者** | [DOCUMENTATION_GUIDELINES.md](./DOCUMENTATION_GUIDELINES.md) |

---

## 🚀 最初に読むべきドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [開発環境セットアップ](./GETTING_STARTED.md) | ビルド・実行方法 |
| [ベストプラクティス概要](./guide/ONBOARDING.md) | アーキテクチャ理解 |
| [基礎ルール](./guide/FUNDAMENTALS.md) | 命名・データモデル・禁止事項 |

---

## 📋 要件・計画

| ドキュメント | 説明 |
|--------------|------|
| [要求定義](./REQUEST_DEFINITION.md) | プロジェクトの元となる要求定義（課題文） |
| [要件定義](./REQUIREMENTS_DEFINITION.md) | システムの機能・非機能要件 |
| [開発計画](./DEVELOPMENT_PLAN.md) | フェーズ・ブランチ・作業順序（100 PRs） |

## 🏗️ 設計・アーキテクチャ

| ドキュメント | 説明 |
|--------------|------|
| [アーキテクチャ](./ARCHITECTURE.md) | システムアーキテクチャと設計方針 |
| [Design System](./DESIGN_SYSTEM.md) | デザイントークン・テーマ・コンポーネント |

---

## 📦 モジュール別ドキュメント

各モジュールには独自の README.md があり、詳細な構造と責務を定義しています。

| モジュール | 責務 | README |
|------------|------|--------|
| **composeApp/** | アプリエントリーポイント | [README.md](../composeApp/README.md) |
| **domain/** | ビジネスロジック・ドメインモデル | [README.md](../domain/README.md) |
| **data/** | データアクセス・プラットフォーム実装 | [README.md](../data/README.md) |
| **presentation/designsystem/** | ドメイン非依存UIコンポーネント | [README.md](../presentation/designsystem/README.md) |
| **presentation/common/** | ドメイン依存・共通コンポーネント | [README.md](../presentation/common/README.md) |
| **presentation/navigation/** | 画面遷移・ルート定義 | [README.md](../presentation/navigation/README.md) |
| **presentation/feature/** | 機能別UI (MVI) | [README.md](../presentation/feature/README.md) |

---

## 📖 開発ガイド

| ドキュメント | 説明 |
|--------------|------|
| [コーディングガイドライン](./CODING_GUIDELINES.md) | Kotlin/Compose一般規約 |
| [テストガイドライン](./TESTING_GUIDELINES.md) | テストの書き方詳細 |
| [ブランチ戦略](./BRANCH_STRATEGY.md) | Git ブランチの運用ルール |
| [プルリクエストガイド](./PULL_REQUEST.md) | PRの作成方法とレビュープロセス |
| [CI/CD](./CI_CD.md) | CI/CD パイプラインの設定と運用 |

---

## ✅ ベストプラクティスガイド (重要)

タスクベースの実践的ガイドです。**日常の開発で最も参照するドキュメントです。**

| カテゴリ | 説明 |
|----------|------|
| [ガイド一覧 (README)](./guide/README.md) | 全ガイドのインデックス |
| [オンボーディング](./guide/ONBOARDING.md) | アーキテクチャ概要 |
| [基礎ルール](./guide/FUNDAMENTALS.md) | 命名・データモデル・禁止事項 |
| [OpenCode Skills](./guide/OPENCODE_SKILLS.md) | OpenCode skills 運用ガイド |

### タスク別ガイド (14件)

```
guide/tasks/
├── ADD_SCREEN.md           # 画面追加
├── ADD_ALGORITHM.md        # アルゴリズム追加
├── ADD_UI_COMPONENT.md     # UIコンポーネント追加
├── ADD_TEST.md             # テスト追加
├── ADD_USECASE.md          # UseCase追加
├── ADD_REPOSITORY.md       # Repository追加
├── ADD_DATA_GENERATOR.md   # データジェネレーター追加
├── ADD_PERSISTENCE.md      # データ永続化追加
├── ADD_FEATURE_MODULE.md   # Feature Module追加
├── ADD_KOIN_MODULE.md      # Koin Module追加
├── ADD_DESIGN_TOKEN.md     # Design Token追加
├── ADD_LOCALIZATION.md     # 多言語対応追加
├── ADD_CLI_COMMAND.md      # CLIコマンド追加
└── DEBUG_GUIDE.md          # デバッグ・問題解決
```

### リファレンス (14件)

詳細な仕様が必要な時に参照: `guide/reference/`

---

## 👤 ユーザー向け

| ドキュメント | 説明 |
|--------------|------|
| [ユーザーガイド](./USER_GUIDE.md) | アプリケーションの使い方 |

---

## 🖥️ 実行コマンド

```bash
# Desktop 実行
./gradlew :composeApp:run

# Web 実行
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# CLI 実行
./gradlew runCli --args="--algorithm bubble --size 20"
```