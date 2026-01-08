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
| ビルド | Gradle (Kotlin DSL) |
| コード品質 | ktlint, detekt |
| CI/CD | GitHub Actions |

## アーキテクチャ

**Layered Architecture (Clean Architecture-like)** を採用しています。

```
dotnet/
├── composeApp/         → Compose Multiplatform アプリ (GUI/CUI エントリポイント)
├── presentation/       → Presentation Layer (MVI パターン)
│   └── designsystem/   → Design System (Atomic Design)
├── domain/             → Domain Layer (DDD)
└── data/               → Data Layer
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

以下のドキュメントに従ってください：

| ドキュメント | パス | 内容 |
|-------------|------|------|
| 要件定義 | `doc/REQUIREMENTS_DEFINITION.md` | 機能・非機能要件 |
| 開発計画 | `doc/DEVELOPMENT_PLAN.md` | PR計画、作業順序 |
| アーキテクチャ | `doc/ARCHITECTURE.md` | レイヤー構成、MVI、DDDパターン |
| ブランチ戦略 | `doc/BRANCH_STRATEGY.md` | Git ブランチ運用、命名規則 |
| CI/CD | `doc/CI_CD.md` | CI/CD パイプライン |
| プルリクエスト | `doc/PULL_REQUEST.md` | PR の書き方 |
| 実行方法 | `doc/GETTING_STARTED.md` | ビルド・実行コマンド |

## コーディング規約

### 一般原則

- **SOLID原則**に従う（特にSRP、DIP、ISP）
- **不変性**を優先: `var` より `val` を使用
- **Nullable**は最小限に: `Result` 型でエラーハンドリング
- コードスタイルは **ktlint** と **detekt** で強制

### Presentation Layer (MVI)

- `Intent` → `ViewModel` → `State` → `View` のフロー
- ViewModelは `handleIntent()` でIntentを処理
- Stateは不変データクラス

### Domain Layer (DDD)

- **Use Case**: 単一責任、`operator fun invoke()` を使用
- **Entity**: ビジネスロジックをカプセル化
- **Value Object**: `@JvmInline value class` で実装
- **Repository**: インターフェースのみ（実装はData層）

### Data Layer

- Repository実装は Domain インターフェースを実装
- DTO と Entity の変換は Mapper クラスで行う

## ブランチ命名規則

```
feature/{PR番号}      # 新機能（例: feature/01, feature/06）
bugfix/Issue番号      # バグ修正
hotfix/説明           # 緊急修正
refactor/説明         # リファクタリング
release/vX.X.X        # リリース
```

> **Note**: 開発計画（`doc/DEVELOPMENT_PLAN.md`）に記載された PR 番号に対応するブランチを作成してください。

## コミット・PR

- **PRテンプレート**: `.github/pull_request_template.md` を使用すること
- **PRガイド**: `doc/PULL_REQUEST.md` を参照
- CI（ktlint, detekt, build, test）がパスするまでマージ不可
- `develop` ブランチにマージ後、`main` へのリリースPRを作成

## 実行コマンド

```bash
# Desktop (GUI)
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Web (JS)
./gradlew :composeApp:jsBrowserDevelopmentRun

# CUI
./gradlew runCli --args="--algorithm bubble --input 5,3,8,1,2"

# Git Hooks セットアップ
./gradlew setupGitHooks

# Lint チェック
./gradlew ktlintCheck detekt
```

## 現在の開発状況

開発は `doc/DEVELOPMENT_PLAN.md` に従って進行中です。
現在 **50個のPR** に分割された段階的な開発計画があります。

### Phase 一覧

1. 基盤整備 (PR-01~05)
2. アルゴリズム実装 (PR-06~11)
3. Data層実装 (PR-12~15)
4. Design System構築 (PR-16~23)
5. UI実装 (PR-24~31)
6. 可視化機能 (PR-32~37)
7. テスト・品質保証 (PR-38~43)
8. CUI実装 (PR-44~47)
9. リリース準備 (PR-48~50)
