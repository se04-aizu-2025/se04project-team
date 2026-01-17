---
title: タスク別ガイド
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ベストプラクティスガイド](../README.md)"
---

# タスク別ガイド

このディレクトリには、日常の開発タスクを実行するためのステップバイステップガイドが格納されています。

## このディレクトリの目的

- **繰り返し行う開発作業を効率化**する
- 手順を標準化し、**一貫性のあるコード**を維持する
- 新しいチームメンバーが**すぐに作業を開始**できるようにする

## 対象読者

- プロジェクトの基礎を理解した開発者
- 具体的なタスクを実行しようとしている人
- 「○○を追加したいが、どこから始めればいいかわからない」という状況の人

---

## ガイド一覧

### 🎨 UI / Presentation

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_SCREEN.md](./ADD_SCREEN.md) | 新しい画面を追加 | 新機能の画面が必要な時 |
| [ADD_UI_COMPONENT.md](./ADD_UI_COMPONENT.md) | UIコンポーネントを追加 | 再利用可能なUI部品が必要な時 |
| [ADD_DESIGN_TOKEN.md](./ADD_DESIGN_TOKEN.md) | Design Tokenを追加 | 新しい色・サイズを定義する時 |

### 🧠 Domain

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_ALGORITHM.md](./ADD_ALGORITHM.md) | ソートアルゴリズムを追加 | 新しいソートを実装する時 |
| [ADD_USECASE.md](./ADD_USECASE.md) | UseCaseを追加 | 新しいビジネスロジックが必要な時 |

### 💾 Data

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_REPOSITORY.md](./ADD_REPOSITORY.md) | Repositoryを追加 | データアクセス層が必要な時 |
| [ADD_DATA_GENERATOR.md](./ADD_DATA_GENERATOR.md) | データジェネレーターを追加 | 新しいテストデータ生成パターンが必要な時 |
| [ADD_PERSISTENCE.md](./ADD_PERSISTENCE.md) | データ永続化を追加 | ローカルストレージが必要な時 |

### 🏗️ Architecture

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_FEATURE_MODULE.md](./ADD_FEATURE_MODULE.md) | Feature Moduleを追加 | 新しい機能モジュールが必要な時 |
| [ADD_KOIN_MODULE.md](./ADD_KOIN_MODULE.md) | Koin Moduleを追加 | DIモジュールを追加する時 |

### 🌐 Extension

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_LOCALIZATION.md](./ADD_LOCALIZATION.md) | 多言語対応を追加 | 国際化対応が必要な時 |
| [ADD_CLI_COMMAND.md](./ADD_CLI_COMMAND.md) | CLIコマンドを追加 | CLI機能を拡張する時 |

### ✅ Testing

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [ADD_TEST.md](./ADD_TEST.md) | テストを追加 | ユニットテスト・UIテストを書く時 |

### 🔧 Troubleshooting

| ガイド | 内容 | 使用場面 |
|--------|------|---------|
| [DEBUG_GUIDE.md](./DEBUG_GUIDE.md) | デバッグ・問題解決 | エラーが発生した時 |

---

## ガイドの使い方

1. 実行したいタスクに対応するガイドを開く
2. **前提条件**を確認する
3. **手順**に従って作業する
4. 完了後、**チェックリスト**で確認する

---

## 新しいガイドを追加する

[DOCUMENTATION_GUIDELINES.md](../../DOCUMENTATION_GUIDELINES.md) の「タスクガイド作成手順」を参照してください。
