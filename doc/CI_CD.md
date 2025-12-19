# CI/CD and Code Quality Setup

このプロジェクトには以下のCI/CDとコード品質チェックツールが設定されています。

## GitHub Actions

### CI Workflow (`.github/workflows/ci.yml`)

**トリガー**:
- `push`: main, master, develop, architecture ブランチへのプッシュ
- `pull_request`: main, master, develop ブランチへのPR

**ジョブ**:

#### 1. Build and Test
- コードのチェックアウト
- JDK 17のセットアップ
- Gradleキャッシュ
- プロジェクトのビルド
- テストの実行
- ビルドレポートのアップロード

#### 2. Code Quality Check
- ktlintによるコードスタイルチェック
- detektによる静的解析
- Lintレポートのアップロード

## ktlint

Kotlinのコードスタイルチェッカー

**実行方法**:
```bash
./gradlew ktlintCheck         # チェックのみ
./gradlew ktlintFormat        # 自動フォーマット
```

**設定**:
- バージョン: 1.1.1
- 設定ファイル: `.editorconfig`

## detekt

Kotlinの静的解析ツール

**実行方法**:
```bash
./gradlew detekt
```

**設定**:
- 設定ファイル: `config/detekt/detekt.yml`
- 最大行数: 120
- 関数の長さ: 60行まで
- パラメータ数: 6個まで

**チェック内容**:
- コーディングスタイル
- 複雑度
- 命名規則
- 潜在的なバグ
- パフォーマンス
- 空のブロック

## CODEOWNERS

`.github/CODEOWNERS`

**機能**:
- PRが作成されると、変更されたファイルに応じて自動的にレビュアーが割り当てられる

**現在の設定**:
- すべてのファイル: @segnities007
- ドキュメント: @segnities007
- 各レイヤー: @segnities007

## Git Hooks

### Pre-commit Hook

**コミット前に自動実行**:
- `ktlintFormat` - コードを自動フォーマット
- `ktlintCheck` - フォーマットチェック
- `detekt` - 静的解析

コミット前に自動的にコードがフォーマットされ、品質チェックが実行されます。

### Pre-push Hook

**プッシュ前に自動実行**:
- `ktlintCheck` - コードスタイルチェック
- `detekt` - 静的解析
- `test` - すべてのテスト実行

**セットアップ方法**:

初回のみ、以下のコマンドでGit Hooksをインストール:
```bash
./gradlew setupGitHooks
```

または、ビルド時に自動的にインストールされます:
```bash
./gradlew build
```

**Git Hooksを一時的に無効化**:
```bash
git commit --no-verify      # pre-commitをスキップ
git push --no-verify        # pre-pushをスキップ
```

> ⚠️ **注意**: `--no-verify`は緊急時のみ使用してください。CI/CDで同じチェックが実行されるため、最終的には修正が必要です。

## ローカルでの実行

### すべてのチェックを実行
```bash
./gradlew ktlintCheck detekt test
```

### 自動修正
```bash
./gradlew ktlintFormat
```

### CI環境と同じコマンド
```bash
./gradlew build test ktlintCheck detekt
```

## PRのマージ前に確認すること

- [ ] `./gradlew ktlintCheck` がパスする
- [ ] `./gradlew detekt` がパスする
- [ ] `./gradlew test` がパスする
- [ ] `./gradlew build` が成功する

これらは GitHub Actions で自動的にチェックされます。
