---
title: ユーザーガイド
version: 1.0.0
last_updated: 2026-01-13
maintainer: Team
audience: end-users
---

# ユーザーガイド

DNSort - ソートアルゴリズム教育ツールの使い方ガイドです。

---

## 目次

1. [クイックスタート](#クイックスタート)
2. [GUI の使い方](#gui-の使い方)
3. [CLI の使い方](#cli-の使い方)
4. [対応アルゴリズム](#対応アルゴリズム)
5. [トラブルシューティング](#トラブルシューティング)

---

## クイックスタート

### デスクトップアプリ

```bash
./gradlew :composeApp:run
```

### Web アプリ

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

ブラウザで自動的に開きます。

### CLI

```bash
./gradlew runCli --args="--algorithm bubble --size 20"
```

---

## GUI の使い方

### ホーム画面

アプリ起動後、4つのメニューから選択：

| メニュー | 説明 |
|----------|------|
| **Visualizer** | ソートアルゴリズムを可視化 |
| **Learn** | アルゴリズムについて学ぶ（準備中） |
| **Compare** | アルゴリズム間の性能比較（準備中） |
| **Settings** | アプリ設定 |

### ソート可視化画面

1. **アルゴリズム選択** - ドロップダウンから選択
2. **配列サイズ調整** - スライダーでサイズ変更
3. **ソート開始** - ▶️ ボタンをクリック
4. **ステップ操作** - ⏸ 一時停止、◀ / ▶ ステップ移動

**キーボードショートカット**:

| キー | 操作 |
|------|------|
| `Space` | 再生 / 一時停止 |
| `←` | 前のステップ |
| `→` | 次のステップ |

### 設定画面

- **ダークモード切替** - スイッチで Light/Dark 切替

---

## CLI の使い方

### 基本コマンド

```bash
./gradlew runCli --args="--algorithm <name> --size <n>"
```

### オプション

| オプション | 説明 | 例 |
|------------|------|-----|
| `--algorithm` | アルゴリズム名 | `bubble`, `quick`, `heap` |
| `--size` | 配列サイズ | `20` |
| `--verbose` | 詳細出力 | - |
| `--help` | ヘルプ表示 | - |

### 対話モード

引数なしで起動：

```bash
./gradlew runCli
```

メニューに従って操作できます。

### 出力例

```
DNSort CLI Tool
Configuration: Algorithm=Quick Sort, Size=20, Verbose=false
Generating array...
Starting sort...
Sort completed!
Metrics:
  Comparisons: 87
  Swaps: 15
  Time: 0.3 ms
```

---

## 対応アルゴリズム

| アルゴリズム | CLI名 | 時間計算量 | 空間計算量 |
|--------------|-------|------------|------------|
| バブルソート | `bubble` | O(n²) | O(1) |
| 選択ソート | `selection` | O(n²) | O(1) |
| 挿入ソート | `insertion` | O(n²) | O(1) |
| シェルソート | `shell` | O(n log²n) | O(1) |
| マージソート | `merge` | O(n log n) | O(n) |
| クイックソート | `quick` | O(n log n) | O(log n) |
| ヒープソート | `heap` | O(n log n) | O(1) |

---

## トラブルシューティング

### アプリが起動しない

1. JDK 17 以上がインストールされているか確認
2. `./gradlew --version` でGradleの動作確認
3. `./gradlew clean build` でクリーンビルド

### CLI でエラーが出る

- アルゴリズム名が正しいか確認（小文字）
- `--args="..."` を忘れていないか確認

### Web版が表示されない

- モダンブラウザを使用（Chrome, Firefox, Edge）
- Wasm がサポートされているか確認

### その他の問題

`./gradlew --stop` で Gradle デーモンを停止後、再実行してください。
