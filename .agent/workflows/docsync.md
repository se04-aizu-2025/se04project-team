---
description: Sync docs with implementation and check consistency
---

# Documentation Sync Skill

ドキュメントを実装に同期し、ドキュメント間の整合性を確認するスキル。

---

## Step 1: ドキュメント一覧の確認

以下のドキュメントを確認対象とする:

### Core Docs
- `AGENTS.md` - AI context (Phase 状態を確認)
- `doc/DEVELOPMENT_PLAN.md` - PR計画と進捗状態

### Architecture & Design
- `doc/ARCHITECTURE.md` - レイヤー構成、モジュール構造
- `doc/DESIGN_SYSTEM.md` - トークン、テーマ、コンポーネント一覧

### Module READMEs
- `composeApp/README.md`
- `domain/README.md`
- `data/README.md`
- `presentation/designsystem/README.md`
- `presentation/common/README.md`
- `presentation/navigation/README.md`
- `presentation/feature/README.md`
- 各 `presentation/feature/*/README.md` (存在する場合)

### Guides
- `doc/guide/FUNDAMENTALS.md`
- `doc/guide/reference/*.md`
- `doc/guide/tasks/*.md`

---

## Step 2: 実装との乖離チェック

### 2.1 モジュール構造チェック

```bash
# 実際のモジュール構造を確認
ls -la presentation/feature/
ls -la presentation/designsystem/src/commonMain/kotlin/dotnet/sort/designsystem/components/
```

確認項目:
- [ ] ドキュメントに記載のモジュールが実際に存在するか
- [ ] 実装に存在するモジュールがドキュメントに記載されているか

### 2.2 Design System コンポーネント一覧チェック

```bash
# 実際のコンポーネント一覧
find presentation/designsystem/src/commonMain/kotlin -name "*.kt" -type f
```

確認項目:
- [ ] `DESIGN_SYSTEM.md` に全コンポーネントが記載されているか
- [ ] Atoms/Molecules/Organisms の分類が正しいか

### 2.3 アルゴリズム一覧チェック

```bash
# 実装されているアルゴリズム
find domain/src/commonMain/kotlin -name "*SortAlgorithm.kt"
```

確認項目:
- [ ] `SortType` enum と一致しているか
- [ ] ドキュメントに全アルゴリズムが記載されているか

### 2.4 Koin モジュールチェック

```bash
# 実際の DI モジュール
grep -r "val.*Module = module" --include="*.kt"
```

確認項目:
- [ ] `DEPENDENCY_INJECTION.md` の記載と一致するか

---

## Step 3: ドキュメント間の整合性チェック

### 3.1 禁止事項の一貫性

以下のドキュメントで禁止事項が一致しているか確認:
- `doc/guide/FUNDAMENTALS.md`
- `doc/guide/reference/COMPOSE_COMPONENTS.md`
- `doc/guide/reference/VIEWMODEL_SCREEN.md`
- `.agent/workflows/rules.md`

### 3.2 命名規則の一貫性

以下のドキュメントで命名規則が一致しているか確認:
- `doc/guide/FUNDAMENTALS.md`
- `doc/guide/reference/NAMING_CONVENTIONS.md`
- `AGENTS.md`
- `.agent/workflows/rules.md`

### 3.3 Atomic Design 分類の一貫性

以下のドキュメントで分類が一致しているか確認:
- `presentation/designsystem/README.md`
- `doc/DESIGN_SYSTEM.md`
- `doc/guide/reference/COMPOSE_COMPONENTS.md`

### 3.4 MVI パターンの一貫性

以下のドキュメントで説明が一致しているか確認:
- `doc/guide/reference/STATE_MANAGEMENT.md`
- `doc/guide/reference/VIEWMODEL_SCREEN.md`
- `doc/ARCHITECTURE.md`

---

## Step 4: DEVELOPMENT_PLAN.md 進捗更新

`doc/DEVELOPMENT_PLAN.md` の Phase 状態を実装に合わせて更新:

### チェック方法

```bash
# 各 feature ブランチの存在を確認
git branch -a | grep feature/

# マージ済み PR を確認
git log --oneline --merges
```

### 更新項目

1. **Phase 状態**: `[完了✅]` / `[進行中🔄]` / `[計画済📝]`
2. **AGENTS.md の Phase 一覧**: 上記と同期

---

## Step 5: バージョン・日付の更新

各ドキュメントの YAML frontmatter を更新:

```yaml
---
title: ドキュメントタイトル
version: X.X.X          # バージョンをインクリメント
last_updated: YYYY-MM-DD  # 今日の日付に更新
maintainer: Team
---
```

---

## Step 6: 更新が必要な項目のリストアップ

以下の形式で報告:

### 乖離検出レポート

| カテゴリ | ドキュメント | 問題 | 修正内容 |
|----------|--------------|------|----------|
| モジュール | ARCHITECTURE.md | feature/learn 未記載 | Learn 機能を追加 |
| コンポーネント | DESIGN_SYSTEM.md | SortTabRow 未記載 | コンポーネント一覧に追加 |
| 進捗 | DEVELOPMENT_PLAN.md | PR-54 未完了 | ステータスを更新 |

### 整合性問題レポート

| ドキュメント A | ドキュメント B | 矛盾点 | 正しい内容 |
|----------------|----------------|--------|------------|
| FUNDAMENTALS.md | rules.md | 禁止事項が異なる | FUNDAMENTALS.md に合わせる |

---

## Step 7: 自動修正の実行

検出した問題を修正:

1. **ドキュメント更新**: 実装に合わせて内容を修正
2. **バージョン更新**: `version` と `last_updated` を更新
3. **整合性修正**: 矛盾するドキュメントを統一

---

## チェックリスト

- [ ] モジュール構造がドキュメントと一致
- [ ] Design System コンポーネント一覧が最新
- [ ] アルゴリズム一覧が最新
- [ ] Koin モジュール記載が最新
- [ ] 禁止事項がドキュメント間で一致
- [ ] 命名規則がドキュメント間で一致
- [ ] MVI パターン説明がドキュメント間で一致
- [ ] DEVELOPMENT_PLAN.md の進捗が最新
- [ ] AGENTS.md の Phase 状態が最新
- [ ] 各ドキュメントの version/last_updated が更新済み
