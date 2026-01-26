---
title: Guess Feature
version: 1.0.0
last_updated: 2026-01-26
maintainer: Team
parent: "[📚 ドキュメント一覧](../../../doc/README.md)"
---

# Guess Feature

アルゴリズム推測ゲーム機能を提供するモジュールです。

ソートアニメーションを見て、どのアルゴリズムかを当てるゲームを実装します。

---

## 目的

ゲーミフィケーションを通じて、ソートアルゴリズムの理解を深める機能を提供します。

---

## 🎮 機能概要

- **アニメーション表示**: ソート過程のリアルタイムアニメーション
- **アルゴリズム推測**: 7つのアルゴリズムから正解を選択
- **ヒント機能**: 計算量などのヒント表示
- **スコアリング**: 正解/不正解によるスコア管理

---

## 📁 構造

```
feature/guess/
├── GuessScreen.kt        # ゲーム画面 UI
├── GuessViewModel.kt     # ゲーム状態管理
├── GuessIntent.kt        # ユーザーアクション
├── GuessState.kt         # UI状態
├── GuessFeatureModule.kt # DI 登録
└── components/           # ゲーム固有コンポーネント
```

---

## 🔗 依存関係

- `domain`: ソートアルゴリズム、ロジック
- `presentation.common`: 共通ViewModel基底クラス
- `presentation.designsystem`: UIコンポーネント
- `data`: スコア永続化（将来）