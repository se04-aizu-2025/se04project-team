# 📚 ドキュメント一覧

このディレクトリには、プロジェクトに関する各種ドキュメントが格納されています。

## 要件・計画

| ドキュメント | 説明 |
|--------------|------|
| [要求定義](./REQUEST_DEFINITION.md) | プロジェクトの元となる要求定義（課題文） |
| [要件定義](./REQUIREMENTS_DEFINITION.md) | システムの機能・非機能要件 |
| [開発計画](./DEVELOPMENT_PLAN.md) | フェーズ・ブランチ・作業順序（50 PRs） |

## 設計・アーキテクチャ

| ドキュメント | 説明 |
|--------------|------|
| [アーキテクチャ](./ARCHITECTURE.md) | システムアーキテクチャと設計方針 |

## 開発ガイド

| ドキュメント | 説明 |
|--------------|------|
| [開発環境セットアップ & 実行ガイド](./GETTING_STARTED.md) | プロジェクトのビルド・実行方法 |
| [ブランチ戦略](./BRANCH_STRATEGY.md) | Git ブランチの運用ルール |
| [CI/CD](./CI_CD.md) | CI/CD パイプラインの設定と運用 |
| [プルリクエストガイド](./PULL_REQUEST.md) | PRの作成方法とレビュープロセス |
| [テストガイドライン](./TESTING_GUIDELINES.md) | テストの書き方とベストプラクティス |

## クイックリンク

```bash
# Desktop 実行
./gradlew :composeApp:run

# Web 実行
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# CUI 実行
./gradlew runCli --args="--algorithm bubble --input 5,3,8,1,2"
```