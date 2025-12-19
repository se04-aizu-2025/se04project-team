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
