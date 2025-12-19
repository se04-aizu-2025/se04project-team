# AGENTS.md - AI アシスタント向けプロジェクトコンテキスト

このファイルはAIコーディングアシスタント（Cursor、GitHub Copilot、Claude Code など）が
プロジェクトを理解するための情報を提供します。

## プロジェクト概要

Kotlin Multiplatform を使用した Web/Desktop クロスプラットフォームアプリケーションです。

## 技術スタック

| カテゴリ | 技術 |
|----------|------|
| 言語 | Kotlin |
| UI | Compose Multiplatform |
| ターゲット | Desktop (JVM), Web (Wasm/JS) |
| ビルド | Gradle (Kotlin DSL) |
| コード品質 | ktlint, detekt |

## アーキテクチャ

**Layered Architecture (Clean Architecture-like)** を採用しています。

```
dotnet/
├── composeApp/     → Compose Multiplatform アプリ
├── presentation/   → Presentation Layer (MVI パターン)
├── domain/         → Domain Layer (DDD)
└── data/           → Data Layer
```

### レイヤー間の依存関係

```
Presentation → Domain ← Data
```

- **Presentation** は **Domain** に依存
- **Data** は **Domain** に依存
- **Domain** は他のレイヤーに依存しない

## 必読ドキュメント

以下のドキュメントに従ってください：

| ドキュメント | パス | 内容 |
|-------------|------|------|
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
feature/機能名        # 新機能
bugfix/Issue番号-説明 # バグ修正
hotfix/説明           # 緊急修正
refactor/説明         # リファクタリング
```

## コミット・PR

- PR作成時はテンプレート（`.github/pull_request_template.md`）に従う
- CI（ktlint, detekt, build, test）がパスするまでマージ不可

## 実行コマンド

```bash
# Desktop
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Web (JS)
./gradlew :composeApp:jsBrowserDevelopmentRun

# Git Hooks セットアップ
./gradlew setupGitHooks
```
