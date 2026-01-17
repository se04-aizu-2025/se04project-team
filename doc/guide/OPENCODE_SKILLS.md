---
title: OpenCode Skills 運用ガイド
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ベストプラクティスガイド](./README.md)"
---

# OpenCode Skills 運用ガイド

このドキュメントは、OpenCode の skills 機能を本プロジェクトで使うための手順と運用ルールをまとめたものです。

## 目的

- PR テンプレ作成などの定型作業を自動化する
- リポジトリ固有の手順を再利用可能な形で保持する
- チーム内で同じ操作手順を共有する

## Skill の配置場所

プロジェクト固有の Skill は `.opencode/skill/<name>/SKILL.md` に配置します。

例:

```
.opencode/skill/pr-template-summary/SKILL.md
.opencode/skill/read-all-docs/SKILL.md
```

## 使い方

Skill はツールとしてエージェントが読み込みます。ユーザーは通常の会話で次のように依頼してください。

- 「`pr-template-summary` の skill を使って、PR テンプレ草案を作成して」
- 「`read-all-docs` の skill を使って `doc/` を要約して」

## 権限設定

`opencode.json` の `permission.skill` でアクセス制御します。

```
{
  "$schema": "https://opencode.ai/config.json",
  "permission": {
    "skill": {
      "*": "allow"
    }
  }
}
```

## 追加済み Skill

| Skill | 目的 | 備考 |
|-------|------|------|
| `pr-template-summary` | PR テンプレの草案を生成 | 変更差分と履歴から要約 | 
| `read-all-docs` | `doc/` の全体要約 | ドキュメント全体の把握 | 

## 新しい Skill の追加

1. `.opencode/skill/<name>/SKILL.md` を作成
2. `name` と `description` を含む YAML frontmatter を記述
3. 使い方・出力形式・注意点を明記

## 注意事項

- Skill はエージェントが必要な時に読み込みます
- Skill の内容は短く具体的に書く
- 機密情報や外部 URL を含めない
