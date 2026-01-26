---
name: pr
description: Provides guidance on creating and reviewing pull requests for the DNSort project. Use this when creating PRs, reviewing code, or understanding PR processes.
---

You are the pr skill for the DNSort project. When activated, provide comprehensive guidance on pull request creation and review processes:

## 1. PRの書き方

### 概要
PRの目的を簡潔に説明してください。何を解決しようとしているのか、なぜこの変更が必要なのかを明確にします。

### 変更内容
主要な変更点をリストアップしてください：
- 機能Aを追加
- バグBを修正
- ファイルCをリファクタリング

### 変更の種類
該当するものにチェックを入れます：

| 絵文字 | 種類 | 説明 |
|--------|------|------|
| 🐛 | Bug fix | バグ修正 |
| ✨ | New feature | 新機能 |
| 💄 | UI/UX | UIやスタイルの変更 |
| ♻️ | Refactoring | リファクタリング |
| 📝 | Documentation | ドキュメント更新 |
| 🧪 | Test | テストの追加・修正 |
| 🔧 | Configuration | 設定ファイルの変更 |
| ⚡️ | Performance | パフォーマンス改善 |

### 関連Issue
- 関連するIssue番号を記載（例: #123）
- 解決するIssueがある場合は "Fixes #123" や "Closes #123" を使用

## 2. レビュープロセス

### レビュアーのチェックポイント
- **機能要件**: 要求された機能が正しく実装されているか
- **コード品質**: コーディング規約に従っているか
- **テスト**: 適切なテストが追加されているか
- **ドキュメント**: 必要に応じてドキュメントが更新されているか
- **パフォーマンス**: パフォーマンスに悪影響がないか

### レビューコメントの書き方
- 建設的なフィードバックを提供
- なぜその変更を提案するのか説明
- コードの改善点を具体的に指摘

## 3. マージ条件
- すべてのレビューコメントが解決されている
- CI/CDがパスしている
- 必要な承認が得られている

Always reference the PULL_REQUEST.md file in the doc/ directory for the most current information.