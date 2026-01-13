---
title: 開発環境セットアップ
version: 1.0.0
last_updated: 2026-01-13
maintainer: Team
---

# 開発環境セットアップ & 実行ガイド

このドキュメントでは、プロジェクトのビルドと実行方法を説明します。

## 前提条件

- **JDK 17** 以上
- **Gradle** (含まれている gradlew を使用可能)
- モダンブラウザ (Web版の場合)

---

## デスクトップアプリケーション (JVM)

開発版のデスクトップアプリをビルド・実行するには、以下のコマンドを使用します：

### macOS / Linux

```shell
./gradlew :composeApp:run
```

### Windows

```shell
.\gradlew.bat :composeApp:run
```

---

## Web アプリケーション

### Wasm ターゲット (推奨)

> [!TIP]
> Wasmターゲットはより高速で、モダンブラウザに最適化されています。

#### macOS / Linux

```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

#### Windows

```shell
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

### JavaScript ターゲット

> [!NOTE]
> JSターゲットは古いブラウザもサポートしますが、Wasmより遅くなります。

#### macOS / Linux

```shell
./gradlew :composeApp:jsBrowserDevelopmentRun
```

#### Windows

```shell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

---

## Git Hooks のセットアップ

プロジェクトにはコード品質を維持するためのGit Hooksが含まれています。

```shell
./gradlew setupGitHooks
```

---

## CLI (コマンドラインインターフェース)

ソートアルゴリズムをコマンドラインから実行することもできます。

### 引数指定モード

アルゴリズムとサイズを指定して実行：

```shell
./gradlew runCli --args="--algorithm bubble --size 20"
```

**使用可能なオプション**:

| オプション | 説明 | 例 |
|------------|------|-----|
| `--algorithm` | アルゴリズム名 (bubble, selection, insertion, shell, merge, quick, heap) | `--algorithm quick` |
| `--size` | 配列サイズ | `--size 30` |
| `--verbose` | 詳細出力（各ステップを表示） | `--verbose` |
| `--help` | ヘルプ表示 | `--help` |

### 対話モード

引数なしで起動すると対話モードになります：

```shell
./gradlew runCli
```

対話モードでは、メニューからアルゴリズムとサイズを選択できます。

### 詳細出力 (verbose)

`--verbose` オプションでステップごとの配列状態を表示：

```shell
./gradlew runCli --args="--algorithm bubble --size 10 --verbose"
```

**出力例**:

```
DNSort CLI Tool
Configuration: Algorithm=Bubble Sort, Size=10, Verbose=true
Generating array...
Starting sort...
Sort completed!
Metrics:
  Comparisons: 45
  Swaps: 23
  Time: 0.5 ms

Visualization:
Step 0: Start
[3, 7, 1, 9, 2, 8, 4, 6, 5, 0]
Step 1: Compare [0] and [1]
...
```

