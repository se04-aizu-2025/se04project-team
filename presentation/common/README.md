---
title: Common コンポーネント
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ドキュメント一覧](../../doc/README.md)"
---

# Common コンポーネント

このドキュメントでは、`presentation/common` モジュールの役割と配置基準について説明します。

---

## 目的

**ドメイン知識を持つが、複数の Feature で共通利用されるコンポーネント** を配置する場所を定義します。

---

## 対象読者

- **UI実装者**: 共通コンポーネントを作成・利用する際の参照資料。
- **レビュアー**: コンポーネントの配置場所が適切か判断するため。

---

## 📦 `common` vs `designsystem` の違い

| モジュール | ドメイン知識 | 再利用性 | 例 |
|------------|-------------|----------|-----|
| **`designsystem`** | ❌ なし | ✅ 全機能で使用可能 | `SortButton`, `SortSlider`, `SortScaffold` |
| **`common`** | ✅ あり | ✅ 複数機能で使用 | 共通ViewModel, 共通UI状態 |

---

## 📏 配置基準

### `common` に置くべきもの

以下の **両方** を満たす場合に配置します：

- ✅ **ドメイン型を使用**: `SortType`, `SortStep` などのドメイン型に依存する。
- ✅ **複数の Feature で再利用**: 特定の1機能だけでなく、2つ以上の Feature で使われる。

**例**:
- 共通の ViewModel ベースクラス
- 共通のUI状態クラス
- ドメイン型を表示する共通コンポーネント

### `common` に置くべきでないもの

| 状況 | 配置先 |
|------|--------|
| ドメイン知識なし、汎用的 | `designsystem` |
| ドメイン知識あり、1機能のみで使用 | `feature/{name}/components/` |

---

## 📁 ディレクトリ構造

```
presentation/common/src/commonMain/kotlin/dotnet/sort/presentation/common/
├── di/          # 共通 DI モジュール
├── ui/          # 共通 UI コンポーネント
└── viewmodel/   # 共通 ViewModel ベースクラス
```

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [Design System コンポーネント粒度](../designsystem/README.md) | ドメイン非依存コンポーネントの定義 |
| [デザインシステム概要](../../doc/DESIGN_SYSTEM.md) | トークンやテーマの詳細定義 |
| [UIコンポーネント追加ガイド](../../doc/guide/tasks/ADD_UI_COMPONENT.md) | コンポーネントの実装手順 |
