---
title: ComposeApp モジュール
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ドキュメント一覧](../doc/README.md)"
---

# ComposeApp モジュール

アプリケーションのエントリーポイントを提供するモジュールです。

---

## 目的

各プラットフォーム（Desktop, Web, Android）向けの `main()` 関数を提供し、アプリケーションを起動します。

---

## 📦 構造

```
composeApp/src/
├── commonMain/       # 共通コード (App.kt)
├── jvmMain/          # Desktop エントリーポイント
├── jsMain/           # Web (JS) エントリーポイント
├── wasmJsMain/       # Web (Wasm) エントリーポイント
└── androidMain/      # Android エントリーポイント
```

---

## 🚀 実行コマンド

```bash
# Desktop
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Web (JS)
./gradlew :composeApp:jsBrowserDevelopmentRun
```

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [開発環境セットアップ](../doc/GETTING_STARTED.md) | ビルド・実行方法 |
| [アーキテクチャ](../doc/ARCHITECTURE.md) | 全体設計 |
