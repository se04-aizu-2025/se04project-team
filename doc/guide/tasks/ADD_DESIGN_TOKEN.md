# Design Token を追加する

このガイドでは、新しい Design Token (色、スペーシング、アニメーション) を追加するために必要なすべての手順を説明します。

---

## 概要

Design Token は UI の一貫性を保つための設計値です:

```
designsystem/
└── tokens/
    ├── ColorTokens.kt      # 色
    ├── SpacingTokens.kt    # 間隔
    └── AnimationTokens.kt  # アニメーション
```

---

## Step 1: Color Token を追加

```kotlin
// designsystem/tokens/ColorTokens.kt

object ColorTokens {
    // 既存の色
    val primary = Color(0xFF6750A4)
    val onPrimary = Color(0xFFFFFFFF)
    val secondary = Color(0xFF625B71)
    val onSecondary = Color(0xFFFFFFFF)
    
    // 🆕 新規追加
    val success = Color(0xFF4CAF50)
    val onSuccess = Color(0xFFFFFFFF)
    val warning = Color(0xFFFF9800)
    val onWarning = Color(0xFF000000)
    val info = Color(0xFF2196F3)
    val onInfo = Color(0xFFFFFFFF)
    
    // ダークモード用
    object Dark {
        val primary = Color(0xFFD0BCFF)
        val onPrimary = Color(0xFF381E72)
        val success = Color(0xFF81C784)
        val warning = Color(0xFFFFB74D)
    }
}
```

---

## Step 2: Spacing Token を追加

```kotlin
// designsystem/tokens/SpacingTokens.kt

object SpacingTokens {
    // 基本スペーシング
    val None = 0.dp
    val XXSmall = 2.dp
    val XSmall = 4.dp
    val Small = 8.dp
    val Medium = 16.dp
    val Large = 24.dp
    val XLarge = 32.dp
    val XXLarge = 48.dp
    
    // 🆕 新規追加 - 特定用途
    val BarGap = 2.dp       // ソートバー間の間隔
    val CardPadding = 16.dp // カード内パディング
    val ScreenMargin = 20.dp // 画面端マージン
}
```

---

## Step 3: Animation Token を追加

```kotlin
// designsystem/tokens/AnimationTokens.kt

object AnimationTokens {
    // Duration (ミリ秒)
    val Instant = 0
    val Fast = 150
    val Standard = 300
    val Slow = 500
    val VerySlow = 1000
    
    // 🆕 新規追加 - ソート用
    val SwapDuration = 200
    val HighlightDuration = 100
    val CompletionWaveDuration = 50  // 完了ウェーブ1バーあたり
    
    // Easing
    val StandardEasing = FastOutSlowInEasing
    val DecelerateEasing = LinearOutSlowInEasing
    val AccelerateEasing = FastOutLinearInEasing
}
```

---

## Step 4: Theme に統合

```kotlin
// designsystem/theme/SortColorScheme.kt

data class SortColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val success: Color,       // 🆕
    val onSuccess: Color,     // 🆕
    val warning: Color,       // 🆕
    val onWarning: Color,     // 🆕
    // ...
)

// Light テーマ
val LightColorScheme = SortColorScheme(
    primary = ColorTokens.primary,
    onPrimary = ColorTokens.onPrimary,
    success = ColorTokens.success,
    onSuccess = ColorTokens.onSuccess,
    warning = ColorTokens.warning,
    onWarning = ColorTokens.onWarning,
    // ...
)

// Dark テーマ
val DarkColorScheme = SortColorScheme(
    primary = ColorTokens.Dark.primary,
    onPrimary = ColorTokens.Dark.onPrimary,
    success = ColorTokens.Dark.success,
    onSuccess = ColorTokens.onSuccess,
    warning = ColorTokens.Dark.warning,
    onWarning = ColorTokens.onWarning,
    // ...
)
```

---

## Step 5: コンポーネントで使用

```kotlin
// ✅ Token を使用
@Composable
fun SortBar(
    value: Int,
    state: BarState,
    modifier: Modifier = Modifier
) {
    val color = when (state) {
        BarState.Default -> SortTheme.colorScheme.primary
        BarState.Highlighting -> SortTheme.colorScheme.warning
        BarState.Sorted -> SortTheme.colorScheme.success
    }
    
    Box(
        modifier = modifier
            .padding(horizontal = SpacingTokens.BarGap)
            .background(color)
            .animateContentSize(
                animationSpec = tween(AnimationTokens.SwapDuration)
            )
    )
}
```

### 禁止

```kotlin
// ❌ ハードコード禁止
Box(
    modifier = Modifier
        .padding(2.dp)           // ❌ SpacingTokens.BarGap を使う
        .background(Color.Green) // ❌ colorScheme.success を使う
)
```

---

## 命名規則

### Color Token

| 種類 | 命名 |
|------|------|
| **メイン色** | `primary`, `secondary`, `tertiary` |
| **テキスト色** | `onPrimary`, `onSecondary` |
| **状態色** | `success`, `warning`, `error`, `info` |
| **背景色** | `surface`, `background` |

### Spacing Token

| 種類 | 命名 |
|------|------|
| **サイズ系** | `XXSmall`, `XSmall`, `Small`, `Medium`, `Large`, `XLarge` |
| **用途系** | `BarGap`, `CardPadding`, `ScreenMargin` |

### Animation Token

| 種類 | 命名 |
|------|------|
| **速度系** | `Instant`, `Fast`, `Standard`, `Slow` |
| **用途系** | `SwapDuration`, `HighlightDuration` |

---

## チェックリスト

- [ ] Token ファイルに値を追加
- [ ] Light/Dark 両方に定義
- [ ] ColorScheme に統合 (色の場合)
- [ ] コンポーネントで Token を使用
- [ ] ハードコード値を置換
- [ ] Preview で確認

---

## 参考

- [reference/COMPOSE_COMPONENTS.md](../reference/COMPOSE_COMPONENTS.md)
- [Material 3 Color System](https://m3.material.io/styles/color/overview)
