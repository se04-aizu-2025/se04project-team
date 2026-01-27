# Project Context Builder

プロジェクトのドキュメントや設計を全て調査して、包括的なコンテキストを収集・理解する。

## 手順

### Step 1: プロジェクト概要の把握

以下のファイルを読んでプロジェクトの全体像を理解する:
1. `AGENTS.md` - AI向けプロジェクトコンテキスト（最重要）
2. `README.md` - プロジェクト概要
3. `CONTRIBUTING.md` - 開発ガイドライン

### Step 2: ドキュメント体系の調査

`doc/` ディレクトリ内の全てのドキュメントを読む:

**ガイド系**:
- `doc/guide/ONBOARDING.md` - アーキテクチャ概要
- `doc/guide/FUNDAMENTALS.md` - 基礎ルール、命名規則、禁止事項

**設計系**:
- `doc/ARCHITECTURE.md` - アーキテクチャ詳細
- `doc/REQUIREMENTS_DEFINITION.md` - 要件定義
- `doc/DESIGN_SYSTEM.md` - デザインシステム

**計画系**:
- `doc/DEVELOPMENT_PLAN.md` - 開発計画、PR計画

**リファレンス系**:
- `doc/guide/reference/` 配下の全ファイル
- `doc/guide/tasks/` 配下の全ファイル

### Step 3: モジュールREADMEの調査

各モジュールのREADMEを読んで構造を理解する:
- `composeApp/README.md`
- `domain/README.md`
- `data/README.md`
- `presentation/README.md`

### Step 4: 現在の開発状況の確認

1. `git status` - 現在の変更状態
2. `git branch -a` - ブランチ一覧
3. `git log -10 --oneline` - 最近のコミット

### Step 5: コンテキストの要約

以下の形式でプロジェクトコンテキストを要約して報告する:

```markdown
# プロジェクトコンテキスト要約

## プロジェクト概要
[プロジェクト名、目的、技術スタック]

## アーキテクチャ
[レイヤー構成、採用パターン]

## 現在の開発フェーズ
[開発計画における現在位置、進捗]

## 主要なルール・規約
[命名規則、禁止事項、重要なガイドライン]

## 現在のブランチ状況
[ブランチ名、変更状態、最近のコミット]

## 読んだドキュメント一覧
[読んだファイルのリスト]
```

## 注意事項

- ファイルの変更は行わない
- 全てのドキュメントを実際に読んで理解する
- 不明な点や矛盾があれば報告する
- 開発作業の前にこのスキルを使ってコンテキストを確立することを推奨
