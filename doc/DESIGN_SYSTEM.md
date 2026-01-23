---
title: デザインシステム
version: 1.2.0
last_updated: 2026-01-23
maintainer: Team
---

# Design System ガイド

このドキュメントでは、DNSort の Design System について説明します。

---

## 概要

Design System は **Atomic Design** に基づいて構築されています。

```
designsystem/
├── tokens/       # デザイントークン (色、スペーシング、アニメーション)
├── theme/        # テーマ (Material 3 ベース)
└── components/   # UI コンポーネント
    ├── atoms/    # 最小単位のコンポーネント
    └── molecules/# 複合コンポーネント

※ コンポーネントの粒度定義については [こちら](../presentation/designsystem/README.md) を参照してください。
※ ドメイン依存の共通コンポーネントについては [こちら](../presentation/common/README.md) を参照してください。
```

---

## デザイントークン

### ColorTokens

Kotlin ブランドカラーをベースにしたカラーパレット：

| トークン | 説明 | 用途 |
|----------|------|------|
| `KotlinPurple` | #7F52FF | Primary カラー |
| `KotlinOrange` | #F88909 | Secondary / 比較中の要素 |
| `KotlinPink` | #E44857 | 交換中の要素 |

**可視化用カラー**:

| トークン | 説明 | 用途 |
|----------|------|------|
| `BarDefault` | #5C9CE6 | デフォルトのバー |
| `BarComparing` | Orange | 比較中の要素 |
| `BarSwapping` | Pink | 交換中の要素 |
| `BarSorted` | #4CAF50 | ソート完了した要素 |
| `BarPivot` | Purple | ピボット要素 |

**使用方法**:

```kotlin
import dotnet.sort.designsystem.tokens.ColorTokens

Box(
    modifier = Modifier.background(ColorTokens.BarDefault)
)
```

### SpacingTokens

一貫性のあるスペーシング：

| トークン | 値 | 用途 |
|----------|-----|------|
| `None` | 0.dp | なし |
| `XXS` | 2.dp | 最小スペース |
| `XS` | 4.dp | 微小スペース |
| `S` | 8.dp | 小スペース |
| `M` | 16.dp | 中スペース (デフォルト) |
| `L` | 24.dp | 大スペース |
| `XL` | 32.dp | 特大スペース |
| `XXL` | 48.dp | 最大スペース |
| `FloatingTopBarInset` | 80.dp | フローティングトップバー余白 |
| `FloatingBottomBarInset` | 96.dp | フローティングボトムバー余白 |

**使用方法**:

```kotlin
import dotnet.sort.designsystem.tokens.SpacingTokens

Spacer(modifier = Modifier.height(SpacingTokens.M))
```

### AnimationTokens

アニメーションのタイミング定数：

| トークン | 値 | 用途 |
|----------|-----|------|
| `FastDuration` | 150ms | 高速アニメーション |
| `NormalDuration` | 300ms | 標準アニメーション |
| `SlowDuration` | 500ms | 低速アニメーション |
| `VisualizationDelay` | 50ms | 可視化ステップ間隔 |

---

## テーマ

### SortTheme

Material 3 ベースのカスタムテーマ：

```kotlin
// 📁 designsystem/theme/Theme.kt (検証済み: 2026-01-16)
@Composable
fun SortTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val sortColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    CompositionLocalProvider(
        LocalSortColorScheme provides sortColorScheme,
        LocalSortTypography provides DefaultTypography,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}
```

**テーマ値へのアクセス**:

```kotlin
// 📁 Composable 関数内 (検証済み: 2026-01-16)
@Composable
fun MyComponent() {
    Text(
        text = "Hello",
        style = SortTheme.typography.body,  // SortTypography
        color = SortTheme.colorScheme.primary  // SortColorScheme
    )
}
```

---

## コンポーネント

### Atoms (9)

**SortBar** - ソート可視化のバー：

```kotlin
@Composable
fun SortBar(
    value: Int,
    maxValue: Int,
    state: BarState = BarState.Default,
    modifier: Modifier = Modifier
)
```

**BarState** - バーの状態:
- `Default` - 通常状態
- `Comparing` - 比較中
- `Swapping` - 交換中
- `Sorted` - ソート完了
- `Pivot` - ピボット

**SortButton** - アクションボタン

**SortSlider** - 値調整スライダー

**SortText** - テキスト表示 (+ `SortHeadline`, `SortTitle`, `SortLabel` バリエーション)

**SortIcon** - アイコン表示

**SortIconButton** - アイコンボタン

**SortDivider** - 区切り線

**SortDropdown** - ドロップダウン選択

---

### Molecules (7)

**ArrayBar** - 配列全体の可視化：

```kotlin
@Composable
fun ArrayBar(
    array: List<Int>,
    highlightIndices: List<Int> = emptyList(),
    sortedIndices: Set<Int> = emptySet(),
    pivotIndex: Int? = null,
    modifier: Modifier = Modifier
)
```

**SortBarBase** - バー可視化の基盤コンポーネント

**SortCard** - 汎用カード

**SortSectionCard** - タイトル付きセクションカード

**SortTopBar** - トップバー

**SortBottomBar** - ボトムバー

**SortSettingsRow** - 設定行コンポーネント

**SortInfoRow** - 情報表示行コンポーネント

---

### Organisms (1)

**SortScaffold** - 画面レイアウト：

```kotlin
@Composable
fun SortScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
)
```

---

## ベストプラクティス

### 1. トークンを直接使用

```kotlin
// ✅ Good - トークン使用
Spacer(modifier = Modifier.height(SpacingTokens.M))

// ❌ Bad - ハードコード
Spacer(modifier = Modifier.height(16.dp))
```

### 2. テーマ経由でアクセス

```kotlin
// ✅ Good - テーマ経由
color = SortTheme.colorScheme.primary

// ❌ Bad - 直接指定
color = Color(0xFF7F52FF)
```

### 3. コンポーネントの再利用

```kotlin
// ✅ Good - Design System コンポーネント使用
SortButton(onClick = { }, text = "Start")

// ❌ Bad - 独自ボタン作成
Button(onClick = { }) { Text("Start") }
```
