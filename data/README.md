---
title: Data モジュール
version: 1.1.0
last_updated: 2026-01-17
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
data/
├── src/commonMain/kotlin/dotnet/sort/data/
│   ├── history/          # Feature: algorithm history
│   │   ├── adapter/
│   │   ├── datasource/
│   │   ├── mapper/
│   │   └── policy/
│   ├── quiz/             # Feature: quiz score
│   │   ├── adapter/
│   │   ├── datasource/
│   │   ├── mapper/
│   │   └── policy/
│   └── infrastructure/   # DB/Driver/Provider
├── src/commonMain/sqldelight/ # SQLDelight schema
├── src/jvmMain/              # JVM 固有実装
├── src/jsMain/               # JS 固有実装
└── src/wasmJsMain/           # WASM 固有実装
```

---

## 🔧 Data層のアーキテクチャ

Data層は **Hexagonal Architecture の Adapter** として設計し、Feature単位で責務を分割します。

| 役割 | 説明 | 例 |
|------|------|----|
| **Port** | Domain 側の Repository インターフェース | `AlgorithmHistoryRepository` |
| **Adapter** | Repository 実装 | `AlgorithmHistoryRepositoryImpl` |
| **DataSource** | Local/Remote の実データアクセス | `HistoryLocalDataSource` |
| **Mapper** | Domain ⇄ DB/API 変換 | `HistoryMapper` |
| **Policy** | キャッシュ/同期/取得戦略 | `HistorySyncPolicy` |
| **Infrastructure** | DB/Driver/Schema | `DnsortDatabaseProvider`, `sqldelight/` |

---

## 🔧 主要コンポーネント

### SQLDelight (`sqldelight/`)
アルゴリズム履歴を保存するための SQLDelight スキーマとクエリを管理します。

### DatabaseProvider (`database/`)
SQLDelight のドライバ生成と履歴イベントの読み書きを提供します。

### Repository 実装 (`repository/`)
`AlgorithmHistoryRepository` を通じて履歴イベントの保存・取得を担当します。

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [アーキテクチャ](../doc/ARCHITECTURE.md) | 全体アーキテクチャ |
| [Repository追加ガイド](../doc/guide/tasks/ADD_REPOSITORY.md) | Repository実装追加手順 |
| [データ永続化ガイド](../doc/guide/tasks/ADD_PERSISTENCE.md) | 永続化追加手順 |
| [Design System](../doc/DESIGN_SYSTEM.md) | トークン・UI設計との整合 |
