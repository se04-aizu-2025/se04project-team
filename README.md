# dotnet

Kotlin Multiplatform を使用した **Web** および **Desktop (JVM)** 対応のクロスプラットフォームアプリケーションです。

## 技術スタック

| カテゴリ | 技術 |
|----------|------|
| **言語** | Kotlin |
| **UI フレームワーク** | Compose Multiplatform |
| **ターゲット** | Desktop (JVM), Web (Wasm/JS) |
| **ビルドツール** | Gradle (Kotlin DSL) |
| **コード品質** | ktlint, detekt |

## アーキテクチャ

```
dotnet/
├── composeApp/     # Compose Multiplatform アプリケーション
├── presentation/   # プレゼンテーション層
├── domain/         # ドメイン層 (ビジネスロジック)
└── data/           # データ層
```

## プロジェクト構成

- **[composeApp](./composeApp/src)** - クロスプラットフォーム共有コード
  - `commonMain` - 全ターゲット共通のコード
  - `jvmMain` - Desktop (JVM) 固有のコード
  - `wasmJsMain` / `jsMain` - Web 固有のコード

## ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [開発環境セットアップ & 実行ガイド](./doc/GETTING_STARTED.md) | ビルド・実行方法 |
| [アーキテクチャ](./doc/ARCHITECTURE.md) | システム設計・構成 |
| [ブランチ戦略](./doc/BRANCH_STRATEGY.md) | Git ブランチ運用 |
| [CI/CD](./doc/CI_CD.md) | 継続的インテグレーション・デリバリー |

## クイックスタート

### デスクトップアプリを実行

```shell
./gradlew :composeApp:run
```

### Web アプリを実行 (Wasm)

```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

> [!TIP]
> 詳細な実行方法は [開発環境セットアップ & 実行ガイド](./doc/GETTING_STARTED.md) をご覧ください。

## 参考リンク

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)