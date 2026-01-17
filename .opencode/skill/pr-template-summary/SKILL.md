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
Return a single Markdown code block that follows the template sections:
- 概要
- 変更内容
- 変更の種類 (best-guess checkbox)
- 関連Issue
- テスト方法
- スクリーンショット/動画
- チェックリスト
- その他

## Required inputs
- The current git branch with changes

## Notes
- Do not commit or push
- Keep bullets concise and factual
