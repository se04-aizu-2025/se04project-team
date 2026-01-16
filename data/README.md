---
title: Data モジュール
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ドキュメント一覧](../doc/README.md)"
---

# Data モジュール

プラットフォーム固有の実装とデータアクセス層を提供するモジュールです。

---

## 目的

**クリーンアーキテクチャ**の Data 層として、Domain 層で定義されたインターフェースの実装を提供します。

---

## 📦 パッケージ構造

```
data/src/
├── commonMain/   # 共通実装
├── jvmMain/      # JVM 固有実装
├── jsMain/       # JS 固有実装
└── wasmJsMain/   # WASM 固有実装
```

---

## 🔧 主要コンポーネント

### Platform (`Platform.kt`)
各プラットフォーム固有の機能を抽象化するクラスです。

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [アーキテクチャ](../doc/ARCHITECTURE.md) | 全体アーキテクチャ |
| [Repository追加ガイド](../doc/guide/tasks/ADD_REPOSITORY.md) | Repository実装追加手順 |
| [データ永続化ガイド](../doc/guide/tasks/ADD_PERSISTENCE.md) | 永続化追加手順 |
