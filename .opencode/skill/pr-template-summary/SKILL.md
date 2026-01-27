---
name: pr-template-summary
description: Generate PR template draft from current branch changes
compatibility: opencode
metadata:
  audience: maintainers
  workflow: github
---
## What I do
- Read `.github/pull_request_template.md`
- Inspect `git status`, `git diff`, and recent `git log`
- Draft a PR template body with a code block containing the summary
- Ask clarifying questions for missing context (issue number, tests, screenshots)

## When to use me
Use this when you want a PR template draft aligned to this repository's format.

## Output format
Return a single Markdown code block that strictly follows this template structure.
**IMPORTANT: All content (summary, changes, test methods) MUST be written in Japanese.**

```markdown
## 概要
<!-- このPRの目的を簡潔に説明してください -->

## 変更内容
<!-- 何を変更したか、主要な変更点をリストアップしてください -->
- 

## 変更の種類
<!-- 該当するものにチェックを入れてください -->
- [ ] 🐛 Bug fix (バグ修正)
- [ ] ✨ New feature (新機能)
- [ ] 💄 UI/UX (UIやスタイルの変更)
- [ ] ♻️ Refactoring (リファクタリング)
- [ ] 📝 Documentation (ドキュメント更新)
- [ ] 🧪 Test (テストの追加・修正)
- [ ] 🔧 Configuration (設定ファイルの変更)
- [ ] ⚡️ Performance (パフォーマンス改善)

## 関連Issue
<!-- 関連するIssueがあれば記載してください -->
Closes #

## テスト方法
<!-- この変更をどのようにテストしたか説明してください -->
1. 

## スクリーンショット/動画
<!-- UIの変更がある場合、スクリーンショットまたは動画を添付してください -->

## チェックリスト
<!-- レビュー前に確認してください -->
- [ ] コードが本プロジェクトのコーディング規約に従っている
- [ ] 自分のコードをセルフレビューした
- [ ] コードに必要なコメントを追加した
- [ ] **ドキュメントを確認・更新した** ([doc/](../doc/)、モジュールREADME)
- [ ] 新しい警告が発生していない
- [ ] テストを追加した（必要な場合）
- [ ] すべてのテストがパスした
- [ ] ビルドが成功している

## その他
<!-- レビュアーに伝えたいことがあれば記載してください -->
```

## Required inputs
- The current git branch with changes

## Notes
- Do not commit or push
- Keep bullets concise and factual
