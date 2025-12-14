# Branch Strategy & Protection Rules

本プロジェクトのブランチ戦略と、GitHubで設定すべきブランチ保護ルールについて説明します。

## ブランチ戦略

### メインブランチ

```
main (or master)
  ↑
  PR
  ↑
develop
  ↑
  PR
  ↑
feature/xxx
```

#### 1. **main / master** - 本番ブランチ
- 常に安定した状態を保つ
- 直接プッシュ禁止
- リリース可能な状態のみ

#### 2. **develop** - 開発ブランチ
- 次のリリースに向けた開発
- 機能ブランチをマージする先
- 直接プッシュ禁止

#### 3. **feature/\*** - 機能ブランチ
- 新機能・バグ修正用
- developからブランチを切る
- 作業完了後にdevelopへPR

#### 4. **hotfix/\*** - 緊急修正ブランチ
- 本番の緊急バグ修正用
- mainからブランチを切る
- main と develop の両方にマージ

---

## GitHub Branch Protection Rules

### main / master ブランチの保護設定

GitHubリポジトリの **Settings** → **Branches** → **Add branch protection rule** で以下を設定:

#### 基本設定
```
Branch name pattern: main
```

#### ✅ 必須設定

1. **Require a pull request before merging**
   - ✅ Require approvals: `1` (最低1人の承認が必要)
   - ✅ Dismiss stale pull request approvals when new commits are pushed
   - ✅ Require review from Code Owners (CODEOWNERSがある場合)

2. **Require status checks to pass before merging**
   - ✅ Require branches to be up to date before merging
   - **Required status checks**:
     - `build` (GitHub Actions: Build and Test)
     - `lint` (GitHub Actions: Code Quality Check)

3. **Require conversation resolution before merging**
   - ✅ 全てのコメントが解決されていること

4. **Require signed commits** (推奨)
   - ✅ 署名付きコミットを必須化

5. **Require linear history** (推奨)
   - ✅ マージコミットを禁止し、履歴を直線的に保つ

6. **Do not allow bypassing the above settings**
   - ✅ 管理者も含めてルールを適用

#### ⚠️ 制限設定

7. **Restrict who can push to matching branches**
   - 直接プッシュを禁止（PRのみ許可）

8. **Allow force pushes**
   - ❌ 無効（force pushを禁止）

9. **Allow deletions**
   - ❌ 無効（ブランチ削除を禁止）

---

### develop ブランチの保護設定

```
Branch name pattern: develop
```

#### ✅ 設定項目

1. **Require a pull request before merging**
   - ✅ Require approvals: `1`

2. **Require status checks to pass before merging**
   - ✅ Require branches to be up to date before merging
   - **Required status checks**:
     - `build`
     - `lint`

3. **Require conversation resolution before merging**
   - ✅ 有効

4. **Restrict who can push to matching branches**
   - 直接プッシュを禁止

---

## ブランチ命名規則

### Feature ブランチ

```
feature/短い説明
```

**例**:
- `feature/user-authentication`
- `feature/add-search-function`
- `feature/refactor-data-layer`

### Bugfix ブランチ

```
bugfix/Issue番号-短い説明
```

**例**:
- `bugfix/123-fix-login-error`
- `bugfix/456-null-pointer-exception`

### Hotfix ブランチ

```
hotfix/短い説明
```

**例**:
- `hotfix/critical-security-patch`
- `hotfix/production-crash`

### Refactoring ブランチ

```
refactor/短い説明
```

**例**:
- `refactor/cleanup-viewmodels`
- `refactor/optimize-database-queries`

---

## マージ戦略

### 推奨: Squash and Merge

**メリット**:
- コミット履歴がクリーンに保たれる
- 1つのPRが1つのコミットになる
- Git historyが読みやすい

**設定方法**:
GitHub Settings → General → Pull Requests
- ✅ Allow squash merging
- ❌ Allow merge commits (無効化推奨)
- ❌ Allow rebase merging (無効化推奨)

### マージメッセージのフォーマット

```
[Type] Short description (#PR番号)

Longer description if needed.

- Change 1
- Change 2
```

**Type**:
- `[Feature]` - 新機能
- `[Bug]` - バグ修正
- `[Refactor]` - リファクタリング
- `[Docs]` - ドキュメント
- `[Test]` - テスト追加

---

## PR作成のワークフロー

### 1. ブランチを切る

```bash
git checkout develop
git pull origin develop
git checkout -b feature/new-feature
```

### 2. 作業とコミット

```bash
# コミット時にpre-commit hookが自動実行
git add .
git commit -m "Add new feature"
```

### 3. プッシュ

```bash
# プッシュ時にpre-push hookが自動実行
git push origin feature/new-feature
```

### 4. PRを作成

GitHubでPRを作成すると:
- ✅ PRテンプレートが自動的に表示される
- ✅ CODEOWNERSに基づいてレビュアーが自動割り当て
- ✅ GitHub Actionsが自動実行される

### 5. CI/CDチェック

以下が自動的に実行される:
- ✅ Build and Test
- ✅ Code Quality Check (ktlint + detekt)

### 6. コードレビュー

- レビュアーの承認を待つ
- 指摘があれば修正してプッシュ
- 全てのコメントを解決

### 7. マージ

- ✅ 全てのステータスチェックがパス
- ✅ レビュー承認済み
- ✅ コメント解決済み
- ✅ 最新のdevelopにリベース済み

→ **Squash and Merge** でマージ

---

## リリースワークフロー (develop → main)

### リリース準備

developブランチに十分な機能が実装され、リリース可能な状態になったら：

#### 1. リリースブランチを作成（オプション）

```bash
# developから最新を取得
git checkout develop
git pull origin develop

# リリースブランチを作成
git checkout -b release/v1.0.0
```

#### 2. バージョン番号の更新

```bash
# gradle.propertiesやその他の設定ファイルでバージョンを更新
# 例: version=1.0.0

git add .
git commit -m "Bump version to 1.0.0"
git push origin release/v1.0.0
```

#### 3. mainへのPRを作成

GitHubで **release/v1.0.0** (または develop) から **main** へのPRを作成:

**PRタイトル例**:
```
[Release] v1.0.0
```

**PRの説明に含めるべき内容**:
- リリースバージョン
- 主要な新機能
- バグ修正
- Breaking Changes（もしあれば）

**例**:
```markdown
## Release v1.0.0

### 新機能
- ユーザー認証機能の追加
- 検索機能の実装
- ダークモード対応

### バグ修正
- ログイン時のクラッシュを修正
- データ同期の問題を解決

### Breaking Changes
- API v1を廃止、v2に移行
```

#### 4. CI/CDチェック

PRが作成されると、自動的に以下が実行される:
- ✅ Build and Test
- ✅ Code Quality Check
- ✅ すべてのステータスチェックがパス

#### 5. レビューと承認

- チーム全体でレビュー
- 本番環境への影響を確認
- 必要に応じてQA/ステージング環境でテスト

#### 6. mainへマージ

すべての確認が完了したら:
- **Squash and Merge** または **Create a merge commit** でマージ
- リリースの場合は **Create a merge commit** を推奨（履歴を保持）

### リリース後の作業

#### 7. リリースタグの作成

```bash
git checkout main
git pull origin main

# タグを作成
git tag -a v1.0.0 -m "Release version 1.0.0"

# タグをプッシュ
git push origin v1.0.0
```

または、GitHubのReleases機能を使用:
1. GitHub → **Releases** → **Create a new release**
2. Tag version: `v1.0.0`
3. Release title: `Version 1.0.0`
4. 説明を追加（Changelog）
5. **Publish release**

#### 8. developを最新のmainに同期

```bash
# mainの変更をdevelopに反映
git checkout develop
git pull origin develop
git merge main
git push origin develop
```

または、mainからdevelopへのPRを作成してマージ。

---

## リリースサイクルの例

### 通常のリリースフロー

```
1. feature → develop (日常的)
   ↓
2. 複数のfeatureをdevelopに統合
   ↓
3. develop → release/v1.0.0 (リリース準備)
   ↓
4. release/v1.0.0 → main (リリース)
   ↓
5. タグ作成 & GitHub Release
   ↓
6. main → develop (同期)
```

### タイムライン例

| Week | Activity |
|------|----------|
| Week 1-2 | feature開発 → develop |
| Week 3 | developでの統合テスト |
| Week 3末 | release/v1.0.0作成 |
| Week 4 | QA/ステージングテスト |
| Week 4末 | main へマージ & リリース |

---

## 簡略版フロー（小規模プロジェクト向け）

リリースブランチを作らない場合:

```bash
# 1. developからmainへのPRを直接作成
# GitHub で develop → main のPRを作成

# 2. レビュー & CI/CDチェック

# 3. mainへマージ

# 4. タグ作成
git checkout main
git pull origin main
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin v1.0.0

# 5. mainをdevelopに同期
git checkout develop
git merge main
git push origin develop
```

---

## 緊急時の対応

### Force Pushが必要な場合

```bash
# 一時的に保護を解除する必要がある
# GitHubのSettings → Branches で一時的に無効化
```

⚠️ **注意**: 作業後は必ず保護ルールを再度有効化すること

### Hotfixの場合

```bash
# mainから直接ブランチを切る
git checkout main
git pull origin main
git checkout -b hotfix/critical-fix

# 修正作業後
git push origin hotfix/critical-fix

# mainとdevelopの両方にPRを作成
```

---

## まとめ: 設定チェックリスト

GitHub Settings → Branches で以下を確認:

### main ブランチ
- [x] Require pull request reviews
- [x] Require status checks (build, lint)
- [x] Require conversation resolution
- [x] Require signed commits (推奨)
- [x] Require linear history (推奨)
- [x] Restrict pushes
- [x] Prevent force pushes
- [x] Prevent deletion

### develop ブランチ  
- [x] Require pull request reviews
- [x] Require status checks (build, lint)
- [x] Require conversation resolution
- [x] Restrict pushes

### Repository Settings
- [x] Allow squash merging only
- [x] Automatically delete head branches

これらの設定により、コードの品質と安全性が大幅に向上します！
