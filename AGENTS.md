# AGENTS.md - AI アシスタント向けプロジェクトコンテキスト

このファイルはAIコーディングアシスタント（Cursor、GitHub Copilot、Claude Code など）が
プロジェクトを理解するための情報を提供します。

## プロジェクト概要

**DNSort** - ソートアルゴリズム教育ツール

Kotlin Multiplatform を使用した Web/Desktop クロスプラットフォームアプリケーションです。
ソートアルゴリズムの動作を視覚的に学習できる教育ツールとして開発されています。

## 技術スタック

| カテゴリ | 技術 |
|----------|------|
| 言語 | Kotlin |
| UI | Compose Multiplatform |
| ターゲット | Desktop (JVM), Web (Wasm/JS) |
| DI | Koin |
| ビルド | Gradle (Kotlin DSL) |
| コード品質 | ktlint, detekt |
| CI/CD | GitHub Actions |

## アーキテクチャ

**Layered Architecture (Clean Architecture-like)** を採用しています。

```
dotnet/
├── composeApp/         → Compose Multiplatform アプリ (GUI/CUI エントリポイント)
├── presentation/       → Presentation Layer (MVI パターン)
│   ├── navigation/     → Navigation設定
│   ├── designsystem/   → Design System (Atomic Design)
│   ├── common/         → 共通ユーティリティ
│   └── feature/        → 機能別モジュール
│       ├── home/
│       ├── sort/
│       ├── learn/
│       ├── compare/
│       └── settings/
├── domain/             → Domain Layer (DDD)
│   ├── algorithm/      → ソートアルゴリズム
│   ├── model/          → モデル
│   ├── usecase/        → UseCase
│   └── repository/     → Repository インターフェース
├── data/               → Data Layer
│   ├── generator/      → 配列生成
│   └── repository/     → Repository 実装
└── cli/                → CLI アプリ
```

### レイヤー間の依存関係

```
Presentation → Domain ← Data
```

- **Presentation** は **Domain** に依存
- **Data** は **Domain** に依存
- **Domain** は他のレイヤーに依存しない

### 採用デザインパターン

| パターン | 適用箇所 | 目的 |
|----------|----------|------|
| **Strategy** | `SortAlgorithm` | アルゴリズムの動的切り替え |
| **Factory** | `SortAlgorithmFactory` | インスタンス生成 |
| **Template Method** | `BaseSortAlgorithm` | 共通処理の抽象化 |
| **MVI** | ViewModel / Intent / State | 単方向データフロー |

## 必読ドキュメント

### 最初に読むべき

| ドキュメント | パス | 内容 |
|--------------|------|------|
| オンボーディング | `doc/guide/ONBOARDING.md` | アーキテクチャ概要 |
| 基礎ルール | `doc/guide/FUNDAMENTALS.md` | 命名・データモデル・禁止事項 |
| 開発計画 | `doc/DEVELOPMENT_PLAN.md` | PR計画、作業順序 |
| 実行方法 | `doc/GETTING_STARTED.md` | ビルド・実行コマンド |

### タスク実行時に参照

| タスク | ガイド |
|--------|--------|
| 画面追加 | `doc/guide/tasks/ADD_SCREEN.md` |
| アルゴリズム追加 | `doc/guide/tasks/ADD_ALGORITHM.md` |
| UIコンポーネント追加 | `doc/guide/tasks/ADD_UI_COMPONENT.md` |
| テスト追加 | `doc/guide/tasks/ADD_TEST.md` |
| UseCase追加 | `doc/guide/tasks/ADD_USECASE.md` |
| Repository追加 | `doc/guide/tasks/ADD_REPOSITORY.md` |
| Feature Module追加 | `doc/guide/tasks/ADD_FEATURE_MODULE.md` |
| デバッグ | `doc/guide/tasks/DEBUG_GUIDE.md` |

### その他

| ドキュメント | パス | 内容 |
|--------------|------|------|
| 要件定義 | `doc/REQUIREMENTS_DEFINITION.md` | 機能・非機能要件 |
| アーキテクチャ詳細 | `doc/ARCHITECTURE.md` | レイヤー構成詳細 |
| ブランチ戦略 | `doc/BRANCH_STRATEGY.md` | Git ブランチ運用 |
| プルリクエスト | `doc/PULL_REQUEST.md` | PR の書き方 |

## コーディング規約 (要点)

詳細は `doc/guide/FUNDAMENTALS.md` を参照してください。

### 命名規則

| 種類 | 規則 | 例 |
|------|------|-----|
| Composable | PascalCase | `SortScreen()` |
| 通常関数 | camelCase | `executeSortAlgorithm()` |
| ViewModel | `{Feature}ViewModel` | `SortViewModel` |
| UseCase | `{Verb}{Noun}UseCase` | `ExecuteSortUseCase` |
| Intent (データなし) | data object | `data object StartSort` |
| Intent (データあり) | data class | `data class SelectAlgorithm(val type)` |

### MVI パターン

```
User Input → Intent → ViewModel → State → UI
```

### 禁止事項

- ❌ State で `var` を使う
- ❌ Screen に NavController を渡す
- ❌ ViewModel メソッドを直接呼び出す (`send(Intent)` を使う)
- ❌ ハードコード色/サイズ (Design Token を使う)

## ブランチ命名規則

```
feature/{PR番号}      # 新機能（例: feature/01, feature/06）
bugfix/Issue番号      # バグ修正
hotfix/説明           # 緊急修正
refactor/説明         # リファクタリング
release/vX.X.X        # リリース
```

## 実行コマンド

```bash
# Desktop (GUI)
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Web (JS)
./gradlew :composeApp:jsBrowserDevelopmentRun

# CUI
./gradlew runCli --args="--algorithm bubble --size 20"

# Git Hooks セットアップ
./gradlew setupGitHooks

# Lint チェック
./gradlew ktlintCheck detekt
```

## 現在の開発状況

開発は `doc/DEVELOPMENT_PLAN.md` に従って進行中です。
**PR-01~100** が定義されています。

### Phase 一覧

| Phase | 内容 | PR範囲 | 状態 |
|-------|------|--------|------|
| 1 | 基盤整備 | PR-01~05 | ✅ 完了 |
| 2 | アルゴリズム実装 | PR-06~11 | ✅ 完了 |
| 3 | Data層実装 | PR-12~15 | ✅ 完了 |
| 4 | Design System構築 | PR-16~23 | ✅ 完了 |
| 5 | Presentation層アーキテクチャ | PR-24~27 | ✅ 完了 |
| 6 | Sort機能実装 | PR-28~33 | ✅ 完了 |
| 7 | アプリケーション構造構築 | PR-34~36 | ✅ 完了 |
| 8 | 可視化機能強化 | PR-37~40 | 🔄 進行中 |
| 9 | テスト・品質保証 | PR-41~45 | 🔄 一部完了 |
| 10 | CUI実装 | PR-46~49 | ✅ 完了 |
| 11 | リリース準備 | PR-50 | 🔄 進行中 |
| 12-20 | v2.0 拡張機能 | PR-51~100 | 📝 計画済 |
