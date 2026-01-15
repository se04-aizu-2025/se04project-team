# UI コンポーネントを追加する

このガイドでは、新しい Compose コンポーネントを追加するために必要なすべての手順を説明します。

---

## 概要

コンポーネントは **Atomic Design** に基づいて分類します:

```
designsystem/components/
├── atoms/          # 最小単位
└── molecules/      # 複合コンポーネント

feature/{name}/components/  # 画面固有コンポーネント
```

---

## Step 1: 分類を決定

| 種類 | 定義 | 例 |
|------|------|-----|
| **Atom** | 最小単位、汎用 | `SortText`, `SortButton`, `SortBar` |
| **Molecule** | 複数 Atoms の組み合わせ | `ArrayBar`, `AlgorithmCard` |
| **Organism** | 画面固有、機能単位 | `SortVisualizer` (feature内) |

---

## Step 2: コンポーネントを作成

```kotlin
// designsystem/components/atoms/{Name}.kt

/**
 * ソート可視化用のバー。
 *
 * @param value バーが表す値
 * @param maxValue 配列内の最大値 (高さ計算用)
 * @param state バーの状態 (ハイライト等)
 * @param modifier レイアウト調整用の Modifier
 */
@Composable
fun SortBar(
    // 1. 必須パラメータ
    value: Int,
    maxValue: Int,
    
    // 2. オプションパラメータ (デフォルト値)
    state: BarState = BarState.Default,
    
    // 3. イベントコールバック
    onClick: (() -> Unit)? = null,
    
    // 4. Modifier (常に最後)
    modifier: Modifier = Modifier
) {
    val height = (value.toFloat() / maxValue) * 200.dp.value
    val color = when (state) {
        BarState.Default -> SortTheme.colorScheme.primary
        BarState.Highlighting -> SortTheme.colorScheme.secondary
        BarState.Sorted -> SortTheme.colorScheme.tertiary
    }
    
    Box(
        modifier = modifier       // 外部 Modifier 最初
            .width(16.dp)
            .height(height.dp)
            .background(color)
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
    )
}
```

### パラメータ順序ルール

1. **必須データ** (デフォルト値なし)
2. **オプションデータ** (デフォルト値あり)
3. **コールバック** (`onClick`, `onValueChange`)
4. **Modifier** (常に最後、デフォルト `Modifier`)

---

## Step 3: State を定義 (必要な場合)

```kotlin
// designsystem/components/atoms/BarState.kt

enum class BarState {
    Default,
    Highlighting,
    Sorted
}
```

---

## Step 4: Design Token を使用

```kotlin
// ✅ Design Token を使用
Box(
    modifier = modifier
        .background(SortTheme.colorScheme.surface)
        .padding(SpacingTokens.Medium)
)

// ❌ 禁止 - ハードコード値
Box(
    modifier = modifier
        .background(Color(0xFF123456))  // ❌
        .padding(16.dp)                  // ❌
)
```

---

## Step 5: Preview を追加

```kotlin
@Preview(showBackground = true)
@Composable
private fun SortBarPreview() {
    SortTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            SortBar(value = 30, maxValue = 100, state = BarState.Default)
            SortBar(value = 60, maxValue = 100, state = BarState.Highlighting)
            SortBar(value = 90, maxValue = 100, state = BarState.Sorted)
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SortBarDarkPreview() {
    SortTheme {
        SortBar(value = 50, maxValue = 100)
    }
}
```

### Preview ルール

| ルール | 詳細 |
|--------|------|
| `private` | Preview 関数は private |
| `{Component}Preview` | 命名規則 |
| `SortTheme` でラップ | テーマを適用 |
| 複数状態をカバー | Default, Highlighted, Error 等 |

---

## Step 6: アクセシビリティ

```kotlin
Box(
    modifier = modifier
        .semantics {
            contentDescription = "Value $value of $maxValue"
        }
)

// クリック可能な要素
Box(
    modifier = modifier
        .clickable(
            onClick = onClick,
            role = Role.Button
        )
        .semantics {
            contentDescription = "Sort bar, value $value"
        }
)
```

---

## ステートレス設計

### 推奨パターン

```kotlin
// ✅ ステートレス (state を外部から受け取る)
@Composable
fun AlgorithmSelector(
    selectedAlgorithm: SortType,
    onAlgorithmSelected: (SortType) -> Unit,
    modifier: Modifier = Modifier
)

// 使用側
@Composable
fun SortScreen(viewModel: SortViewModel) {
    val state by viewModel.state.collectAsState()
    
    AlgorithmSelector(
        selectedAlgorithm = state.algorithm,
        onAlgorithmSelected = { viewModel.send(SortIntent.SelectAlgorithm(it)) }
    )
}
```

### ステートフルが許容される場合

```kotlin
// ✅ 一時的な UI 状態のみ
@Composable
fun ExpandableCard(content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    // ...
}
```

---

## チェックリスト

- [ ] 分類を決定 (Atom / Molecule / Organism)
- [ ] 適切なディレクトリに配置
- [ ] パラメータ順序を守る
- [ ] 外部 Modifier を最初に適用
- [ ] Design Token を使用
- [ ] ステートレス設計 (可能な限り)
- [ ] Preview を複数追加
- [ ] contentDescription を設定
- [ ] KDoc を追加

---

## 参考

詳細が必要な場合は、[reference/COMPOSE_COMPONENTS.md](../reference/COMPOSE_COMPONENTS.md) を参照してください。
