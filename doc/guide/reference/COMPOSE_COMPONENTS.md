# Compose コンポーネントガイド

このガイドでは、Jetpack Compose コンポーネントの設計規則を定義します。

---

## Atomic Design

```
designsystem/components/
├── atoms/          # 最小単位
│   ├── SortBar.kt
│   ├── SortButton.kt
│   ├── SortSlider.kt
│   └── BarState.kt
└── molecules/      # 複合コンポーネント
    └── ArrayBar.kt
```

| 種類 | 定義 | 例 |
|------|------|-----|
| **Atoms** | 単一責任、再利用可能な最小単位 | Bar, Button, Slider |
| **Molecules** | 複数の Atoms を組み合わせ | AlgorithmSelector, ControlPanel |
| **Organisms** | 独立した機能単位 (画面固有) | SortVisualizer, MetricsDisplay |

---

## 関数シグネチャ

### 引数の順序

```kotlin
@Composable
fun SortBar(
    // 1. 必須パラメータ (データ)
    value: Int,
    maxValue: Int,
    
    // 2. オプションパラメータ (デフォルト値あり)
    state: BarState = BarState.Default,
    color: Color = MaterialTheme.colorScheme.primary,
    
    // 3. イベントコールバック
    onClick: (() -> Unit)? = null,
    
    // 4. Modifier は常に最後
    modifier: Modifier = Modifier
) {
    // 実装
}
```

### ルール表

| 順序 | 種類 | 詳細 |
|------|------|------|
| 1 | **必須データ** | デフォルト値なし、null 不可 |
| 2 | **オプションデータ** | デフォルト値あり |
| 3 | **コールバック** | `onClick`, `onValueChange` 等 |
| 4 | **Modifier** | 常に最後、デフォルト `Modifier` |

**参考**: [Compose API Guidelines - Parameters](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md#parameters)

---

## Modifier の使い方

### 必須ルール

```kotlin
// ✅ 外部から渡された modifier を最初に適用
@Composable
fun SortBar(
    value: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier           // 外部 modifier が最初
            .height(100.dp)           // 内部固定スタイルが後
            .background(Color.Blue)
    ) {
        // コンテンツ
    }
}
```

### 禁止事項

```kotlin
// ❌ 禁止 - 外部 modifier を無視
@Composable
fun BadComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxWidth()  // modifier を使っていない
    )
}

// ❌ 禁止 - 外部 modifier を後に適用
@Composable  
fun BadComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .then(modifier)  // 外部が後だと上書きされる可能性
    )
}
```

---

## State Hoisting

### パターン

```kotlin
// ✅ ステートレス - 推奨
@Composable
fun AlgorithmSelector(
    selectedAlgorithm: SortType,           // State を外部から受け取る
    onAlgorithmSelected: (SortType) -> Unit,  // 変更を外部に通知
    modifier: Modifier = Modifier
) {
    // 内部で state を持たない
}

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
// ✅ 一時的なUI状態のみ (ビジネスロジックに関係ない)
@Composable
fun ExpandableCard(content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }  // UI状態のみ
    
    Card(onClick = { isExpanded = !isExpanded }) {
        if (isExpanded) {
            content()
        }
    }
}
```

### ルール

| 状態の種類 | 管理場所 |
|------------|----------|
| **ビジネス状態** (algorithm, arraySize) | ViewModel |
| **UI 状態** (isExpanded, scrollPosition) | Composable (remember) |
| **一時的な入力** | Composable (validation後にViewModelへ) |

---

## Preview

### 必須ルール

```kotlin
// ✅ 各コンポーネントに Preview を追加
@Preview(showBackground = true)
@Composable
private fun SortBarPreview() {
    SortTheme {
        SortBar(
            value = 50,
            maxValue = 100,
            state = BarState.Highlighting
        )
    }
}

// ✅ 複数状態の Preview
@Preview(name = "Default")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SortBarPreviews() {
    // ...
}
```

### Preview のルール

| ルール | 詳細 |
|--------|------|
| **private** | Preview 関数は private |
| **{Component}Preview** | 命名規則 |
| **SortTheme でラップ** | テーマを適用 |
| **複数状態をカバー** | Default, Highlighted, Error 等 |

---

## Design Tokens

### 使用ルール

```kotlin
// ✅ Design Token を使用
@Composable
fun SortBar(...) {
    Box(
        modifier = modifier
            .background(SortTheme.colorScheme.surface)
            .padding(SpacingTokens.Medium)
    )
}

// ❌ 禁止 - ハードコード値
@Composable
fun BadBar(...) {
    Box(
        modifier = modifier
            .background(Color(0xFF123456))  // ❌
            .padding(16.dp)                  // ❌ SpacingTokens を使う
    )
}
```

### Token 参照

| Token 種類 | アクセス方法 |
|------------|--------------|
| **Color** | `SortTheme.colorScheme.primary` |
| **Spacing** | `SpacingTokens.Small`, `SpacingTokens.Medium` |
| **Animation** | `AnimationTokens.Standard`, `AnimationTokens.Fast` |

---

## アクセシビリティ

### 必須ルール

```kotlin
@Composable
fun SortBar(
    value: Int,
    maxValue: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .semantics {
                contentDescription = "Value $value of $maxValue"
            }
    )
}

// ✅ クリック可能な要素
Button(
    onClick = onStartSort,
    modifier = Modifier.semantics { 
        contentDescription = "Start sorting"
    }
) {
    Text("Start")
}
```

---

## チェックリスト

新しいコンポーネントを追加する場合:

- [ ] Atomic Design のレベルを決定 (Atom/Molecule/Organism)
- [ ] 引数順序を守る (必須 → オプション → コールバック → Modifier)
- [ ] 外部 Modifier を最初に適用
- [ ] State Hoisting を適用 (ステートレス優先)
- [ ] Preview を追加 (@Preview, private, Theme 適用)
- [ ] Design Token を使用 (ハードコード禁止)
- [ ] contentDescription を設定 (アクセシビリティ)
- [ ] KDoc を追加

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Compose API Guidelines** | [Compose API Guidelines](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md) |
| **State in Compose** | [State and Jetpack Compose](https://developer.android.com/jetpack/compose/state) |
| **State Hoisting** | [State Hoisting](https://developer.android.com/jetpack/compose/state#state-hoisting) |
| **Modifiers** | [Compose Modifiers](https://developer.android.com/jetpack/compose/modifiers) |
| **Accessibility** | [Accessibility in Compose](https://developer.android.com/jetpack/compose/accessibility) |
