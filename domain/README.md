---
title: Domain モジュール
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ドキュメント一覧](../doc/README.md)"
---

# Domain モジュール

ビジネスロジックとドメインモデルを定義する純粋 Kotlin モジュールです。

---

## 目的

プラットフォーム非依存のビジネスロジックを提供し、**クリーンアーキテクチャ**の Domain 層を実現します。

---

## 📦 パッケージ構造

```
domain/src/commonMain/kotlin/dotnet/sort/
├── algorithm/    # ソートアルゴリズム実装
├── model/        # ドメインモデル
├── usecase/      # ユースケース
├── generator/    # 配列生成ロジック
├── repository/   # リポジトリインターフェース
└── di/           # DI モジュール定義
```

---

## 🧠 主要コンポーネント

### アルゴリズム (`algorithm/`)
| クラス | 説明 |
|--------|------|
| `BaseSortAlgorithm` | 全アルゴリズムの基底クラス |
| `BubbleSortAlgorithm` | バブルソート |
| `QuickSortAlgorithm` | クイックソート |
| `MergeSortAlgorithm` | マージソート |
| `HeapSortAlgorithm` | ヒープソート |
| `InsertionSortAlgorithm` | 挿入ソート |
| `SelectionSortAlgorithm` | 選択ソート |
| `ShellSortAlgorithm` | シェルソート |
| `SortAlgorithmFactory` | ファクトリパターン |

### モデル (`model/`)
| クラス | 説明 |
|--------|------|
| `SortType` | ソートアルゴリズム種別 |
| `SortResult` | ソート結果 |
| `SortSnapshot` | ステップごとのスナップショット |
| `ComplexityMetrics` | 計算量メトリクス |

### ユースケース (`usecase/`)
| クラス | 説明 |
|--------|------|
| `ExecuteSortUseCase` | ソート実行 |
| `GenerateArrayUseCase` | 配列生成 |

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [アーキテクチャ](../doc/ARCHITECTURE.md) | 全体アーキテクチャ |
| [アルゴリズム追加ガイド](../doc/guide/tasks/ADD_ALGORITHM.md) | 新規アルゴリズム追加手順 |
| [UseCase追加ガイド](../doc/guide/tasks/ADD_USECASE.md) | 新規UseCase追加手順 |
| [アルゴリズムリファレンス](../doc/guide/reference/ALGORITHM.md) | 詳細仕様 |
