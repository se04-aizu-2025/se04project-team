<div align="center">

# 🔢 DNSort

**ソートアルゴリズム教育ツール for Kotlin Multiplatform**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.7.1-4285F4?style=flat&logo=jetpackcompose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg?style=flat)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/Platform-Desktop_|_Web-brightgreen?style=flat)](https://www.jetbrains.com/lp/compose-multiplatform/)

</div>

---

## 📖 概要

**DNSort** は、ソートアルゴリズムを視覚的に学習できる教育ツールです。
Kotlin Multiplatform を使用し、**Desktop (JVM)** と **Web (Wasm/JS)** の両方で動作します。

### ✨ 特徴

- 🎯 **7種類のソートアルゴリズム** - バブル、選択、挿入、シェル、マージ、クイック、ヒープ
- 📊 **リアルタイム可視化** - 各ステップの配列状態をアニメーション表示
- 📈 **計算量分析** - 比較回数、スワップ回数、実行時間を計測
- 🖥️ **GUI/CUI対応** - グラフィカルUIとコマンドライン両方に対応
- 🌐 **クロスプラットフォーム** - Desktop と Web で動作

---

## 🚀 クイックスタート

### Desktop アプリを実行

```bash
./gradlew :composeApp:run
```

### Web アプリを実行 (Wasm)

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### CUI を実行

```bash
./gradlew runCli --args="--algorithm bubble --input 5,3,8,1,2"
```

---

## 🏗️ アーキテクチャ

**Layered Architecture (Clean Architecture-like)** を採用しています。

```
dotnet/
├── composeApp/     # アプリケーションエントリポイント
├── presentation/   # Presentation Layer (MVI)
│   └── designsystem/   # Design System
├── domain/         # Domain Layer (DDD)
└── data/           # Data Layer
```

### 採用パターン

| パターン | 適用箇所 |
|----------|----------|
| **Strategy** | `SortAlgorithm` - アルゴリズムの動的切り替え |
| **Factory** | `SortAlgorithmFactory` - インスタンス生成 |
| **MVI** | ViewModel / Intent / State - 単方向データフロー |
| **Template Method** | `BaseSortAlgorithm` - 共通処理の抽象化 |

---

## 📦 モジュール構成

| モジュール | 説明 |
|------------|------|
| `composeApp` | Compose Multiplatform アプリケーション |
| `presentation` | UI コンポーネント、ViewModel |
| `domain` | ソートアルゴリズム、ユースケース |
| `data` | データジェネレーター |

---

## 🛠️ 技術スタック

| カテゴリ | 技術 |
|----------|------|
| **言語** | Kotlin |
| **UI** | Compose Multiplatform |
| **ターゲット** | Desktop (JVM), Web (Wasm/JS) |
| **ビルド** | Gradle (Kotlin DSL) |
| **コード品質** | ktlint, detekt |
| **CI/CD** | GitHub Actions |

---

## 📚 ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [要件定義](./doc/REQUIREMENTS_DEFINITION.md) | システム要件 |
| [開発計画](./doc/DEVELOPMENT_PLAN.md) | 開発フェーズ・PR計画 |
| [アーキテクチャ](./doc/ARCHITECTURE.md) | システム設計 |
| [開発環境セットアップ](./doc/GETTING_STARTED.md) | ビルド・実行方法 |
| [ブランチ戦略](./doc/BRANCH_STRATEGY.md) | Git運用ルール |

---

## 🎮 使用例

### GUI

```kotlin
// SortScreen でアルゴリズムを選択し、可視化を開始
// ステップ実行、速度調整、一時停止/再開が可能
```

### CUI

```bash
$ ./gradlew runCli --args="--algorithm quick --random 10"

=== DNSort (CLI) ===
Algorithm: Quick Sort
Input:  [5, 3, 8, 1, 9, 2, 7, 4, 6, 0]
Output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

--- Statistics ---
Comparisons: 24
Swaps: 12
Time: 0.3ms
```

---

## 🤝 Contributing

1. `develop` ブランチから `feature/{番号}` ブランチを作成
2. 変更をコミット
3. プルリクエストを作成
4. CI がパスしたらレビュー依頼

詳細は [PULL_REQUEST.md](./doc/PULL_REQUEST.md) を参照してください。

---

## 📄 License

```
Copyright 2026

Licensed under the Apache License, Version 2.0
```

---

## 🔗 参考リンク

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)