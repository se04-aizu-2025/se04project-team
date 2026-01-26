---
description: Read all development, design, and implementation rules
---

# Development Rules Skill

すべての開発・設計・実装ルールを読み込むスキル。

---

## 📖 参照すべきドキュメント

### Core Documents
1. `AGENTS.md` - AI assistant context
2. `doc/guide/ONBOARDING.md` - Architecture overview
3. `doc/guide/FUNDAMENTALS.md` - Core rules
4. `doc/ARCHITECTURE.md` - Detailed architecture
5. `doc/DESIGN_SYSTEM.md` - Design tokens, theme

### Reference Guides
6. `doc/guide/reference/NAMING_CONVENTIONS.md` - Naming rules
7. `doc/guide/reference/STATE_MANAGEMENT.md` - MVI & State
8. `doc/guide/reference/COMPOSE_COMPONENTS.md` - Compose rules
9. `doc/guide/reference/DEPENDENCY_INJECTION.md` - Koin usage
10. `doc/guide/reference/VIEWMODEL_SCREEN.md` - ViewModel/Screen rules

### Module READMEs
11. `presentation/designsystem/README.md` - Atomic Design rules
12. `presentation/common/README.md` - Common components
13. `domain/README.md` - Domain layer
14. `data/README.md` - Data layer

---

# 🚫 禁止事項一覧

## コード禁止事項

| 禁止 | 理由 | 代替 |
|------|------|------|
| `var` in data class | 不変性違反 | `val` を使う |
| `MutableList` in State | 不変性違反 | `List` を使う |
| ハードコード色/サイズ | 保守性低下 | Design Token を使う |
| Screen に NavController | テスト困難 | コールバックで抽象化 |
| ViewModel メソッド直接呼び出し | MVI 違反 | `send(Intent)` を使う |
| GlobalScope | ライフサイクル無視 | `viewModelScope` を使う |
| 例外を握りつぶす | デバッグ困難 | 適切にハンドル |
| **Raw Material3 Component** | **一貫性欠如** | **Design System を使う** |
| 完全修飾名の使用 | 可読性低下 | import または typealias |
| 深い if/when ネスト (3段以上) | 可読性低下 | ガード節・分割関数 |
| 1関数に大量の処理 | 保守性低下 | 小さな関数に分割 |

## 命名禁止事項

| 禁止 | 理由 | 代替 |
|------|------|------|
| `Manager`, `Handler`, `Helper` | 曖昧 | 具体的な名前 |
| `Data`, `Info` サフィックス | 情報が増えない | 具体的な名前 |
| 1文字変数 (ループ以外) | 意味不明 | 説明的な名前 |
| 省略形 | 読みにくい | `algorithm` not `alg` |
| 型名の繰り返し | 冗長 | `sortResult` not `sortResultData` |

---

# 📛 命名規則

## ファイル/クラス命名

| 種類 | 規則 | 例 |
|------|------|-----|
| Screen | `{Feature}Screen.kt` | `SortScreen.kt` |
| ViewModel | `{Feature}ViewModel.kt` | `SortViewModel.kt` |
| Intent | `{Feature}Intent.kt` | `SortIntent.kt` |
| State | `{Feature}State.kt` | `SortState` |
| UseCase | `{Verb}{Noun}UseCase.kt` | `ExecuteSortUseCase.kt` |
| Algorithm | `{Name}SortAlgorithm.kt` | `BubbleSortAlgorithm.kt` |
| Repository | `{Name}Repository.kt` (interface) | `SettingsRepository.kt` |
| Repository Impl | `{Name}RepositoryImpl.kt` | `SettingsRepositoryImpl.kt` |
| Koin Module | `{Feature}Module.kt` | `SortFeatureModule.kt` |

## 関数命名

| 種類 | 規則 | 例 |
|------|------|-----|
| **Composable関数** | PascalCase | `SortScreen()` |
| **通常関数** | camelCase | `executeSortAlgorithm()` |
| **NavGraphBuilder拡張** | `{feature}Destination()` | `sortDestination()` |
| **NavController拡張** | `navigateTo{Feature}()` | `navigateToSort()` |

## 変数命名

| 種類 | 規則 | 例 |
|------|------|-----|
| 変数/プロパティ | camelCase | `currentIndex` |
| 定数 (const) | SCREAMING_SNAKE_CASE | `MAX_ARRAY_SIZE` |
| private backing field | `_` プレフィックス | `_state` |
| **Boolean変数** | **is/has/can プレフィックス** | `isLoading`, `hasError` |

## Intent 命名

```kotlin
sealed class SortIntent : Intent {
    // データなし → data object
    data object StartSort : SortIntent()
    
    // データあり → data class (動詞で開始)
    data class SelectAlgorithm(val type: SortType) : SortIntent()
}
```

---

# 🎨 Design System ルール (最重要)

## Material3 直接使用禁止

```kotlin
// ❌ 禁止 - Raw Material3 Component
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
Text(text = "Hello")

// ✅ 正解 - Design System Component
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.components.organisms.SortScaffold
SortText(text = "Hello")
```

## Design System に代替がない場合

**Material3 に存在するが Design System に存在しないコンポーネントを使いたい場合:**

1. **ラップして新規作成**: Material3 コンポーネントを Design System スタイルでラップした新コンポーネントを `designsystem/components/` に作成
2. **同等機能を自作**: 同等機能を持つ新コンポーネントを Design System に作成

```kotlin
// 例: Tab/TabRow がない場合 → SortTab/SortTabRow を作成
// designsystem/components/molecules/SortTabRow.kt
@Composable
fun SortTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { SortText(text = title) }
            )
        }
    }
}
```

**その後、Feature で使用:**
```kotlin
// feature/learn/AlgorithmDetailScreen.kt
SortTabRow(
    selectedTabIndex = selectedTabIndex,
    tabs = listOf("Overview", "Analysis", "Implementation"),
    onTabSelected = { selectedTabIndex = it }
)
```

## Design Token 使用必須

```kotlin
// ✅ Design Token を使用
Box(
    modifier = modifier
        .background(SortTheme.colorScheme.surface)
        .padding(SpacingTokens.M)
)

// ❌ 禁止 - ハードコード値
Box(
    modifier = modifier
        .background(Color(0xFF123456))  // ❌
        .padding(16.dp)                  // ❌
)
```

## Atomic Design 分類

| レベル | 定義 | 配置場所 | 例 |
|-------|------|----------|-----|
| **Atoms** | 最小単位、ステートレス | `designsystem/components/atoms/` | `SortText`, `SortButton` |
| **Molecules** | Atoms の組み合わせ | `designsystem/components/molecules/` | `SortSectionCard`, `SortTopBar` |
| **Organisms** | 画面の主要セクション (汎用) | `designsystem/components/organisms/` | `SortScaffold` |
| **Feature Organisms** | 特定機能専用 | `feature/{name}/components/` | `SortVisualizer` |

---

# 📊 MVI パターン

## State 定義

```kotlin
data class SortState(
    // すべて val + デフォルト値 + 不変コレクション
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 20,
    val items: List<Int> = emptyList(),  // List, not MutableList
    
    // Boolean は is/has プレフィックス
    val isLoading: Boolean = false,
    val hasError: Boolean = false
) : UiState
```

## Intent 定義

```kotlin
sealed class SortIntent : Intent {
    data object StartSort : SortIntent()           // データなし
    data class SelectAlgorithm(val type: SortType) : SortIntent()  // データあり
}
```

## ViewModel 構造

```kotlin
class SortViewModel(
    private val useCase: ExecuteSortUseCase
) : BaseViewModel<SortState, SortIntent>(SortState()) {

    override fun send(intent: SortIntent) {
        when (intent) {
            is SortIntent.SelectAlgorithm -> 
                updateState { copy(algorithm = intent.type) }
            is SortIntent.StartSort -> startSort()
            // sealed class なので網羅必須
        }
    }
}
```

## Screen 構造

```kotlin
// Screen はステートレス
@Composable
fun SortScreen(
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // UI のみ、ViewModel への依存なし
}

// Destination で DI と State 収集
fun NavGraphBuilder.sortDestination(onBackClick: () -> Unit) {
    composable<Screen.Sort> {
        val viewModel: SortViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        SortScreen(
            state = state,
            onIntent = viewModel::send,
            onBackClick = onBackClick
        )
    }
}
```

---

# 🧩 Compose コンポーネント規則

## パラメータ順序

```kotlin
@Composable
fun SortBar(
    // 1. 必須パラメータ (データ)
    value: Int,
    maxValue: Int,
    
    // 2. オプションパラメータ (デフォルト値あり)
    state: BarState = BarState.Default,
    
    // 3. イベントコールバック
    onClick: (() -> Unit)? = null,
    
    // 4. Modifier は常に最後
    modifier: Modifier = Modifier
)
```

## Modifier 適用順序

```kotlin
// ✅ 外部 modifier を最初に適用
Box(
    modifier = modifier           // 外部が先
        .height(100.dp)           // 内部が後
        .background(Color.Blue)
)

// ❌ 禁止 - 外部 modifier を後に適用
Box(
    modifier = Modifier
        .height(100.dp)
        .then(modifier)  // ❌
)
```

## State Hoisting

```kotlin
// ✅ ステートレス - 推奨
@Composable
fun AlgorithmSelector(
    selectedAlgorithm: SortType,           // State を外部から受け取る
    onAlgorithmSelected: (SortType) -> Unit,  // 変更を外部に通知
    modifier: Modifier = Modifier
)

// ステートフルは一時的なUI状態のみ
@Composable
fun ExpandableCard(content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }  // UI状態のみOK
}
```

## Preview 規則

```kotlin
@Preview(showBackground = true)
@Composable
private fun SortBarPreview() {  // private, {Component}Preview 命名
    SortTheme {                  // SortTheme でラップ必須
        SortBar(value = 50, maxValue = 100)
    }
}
```

---

# 💉 依存性注入 (Koin)

## スコープ

| スコープ | 用途 | 例 |
|----------|------|-----|
| `single` | シングルトン (ステートレス) | UseCase, Repository |
| `factory` | 毎回新規 (ステートフル) | Helper |
| `viewModel` | ViewModel 専用 | `viewModel { SortViewModel(get()) }` |

## インターフェース bind

```kotlin
// ✅ インターフェースで bind
single<ArrayGenerator> { ArrayGeneratorImpl() }

// ❌ 禁止 - 具象型で bind
single { ArrayGeneratorImpl() }  // テスト時に差し替え困難
```

## 禁止事項

```kotlin
// ❌ 禁止 - 循環依存
single { A(get<B>()) }
single { B(get<A>()) }

// ❌ 禁止 - Composable 内で get()
@Composable
fun BadScreen() {
    val useCase = get<MyUseCase>()  // ❌ koinViewModel() を使う
}
```

---

# 📝 KDoc 必須対象

- public クラス
- public 関数
- public プロパティ

```kotlin
/**
 * ソートアルゴリズムを実行する。
 *
 * @param type アルゴリズムの種類
 * @param input ソート対象のリスト
 * @return ソート結果
 */
fun execute(type: SortType, input: List<Int>): SortResult
```

---

# 🧪 テスト命名

```kotlin
@Test
fun `GIVEN unsorted list WHEN sort is called THEN returns sorted list`() {
    // Given
    val input = listOf(5, 3, 1)
    
    // When
    val result = algorithm.sort(input)
    
    // Then
    assertEquals(listOf(1, 3, 5), result.finalArray)
}
```

---

# ✅ チェックリスト

## 新しい画面を追加する場合

- [ ] `{Feature}State` を定義 (data class, UiState)
- [ ] `{Feature}Intent` を定義 (sealed class, Intent)
- [ ] `{Feature}ViewModel` を実装 (BaseViewModel継承)
- [ ] `{Feature}Screen.kt` を作成 (ステートレス)
- [ ] `{Feature}Destination.kt` を作成 (DI, State収集)
- [ ] Preview を追加
- [ ] Koin モジュールに登録
- [ ] Navigation に追加

## 新しい UI コンポーネントを追加する場合

- [ ] Atomic Design のレベルを決定 (Atom/Molecule/Organism)
- [ ] 適切なディレクトリに配置
- [ ] パラメータ順序を守る (必須 → オプション → コールバック → Modifier)
- [ ] 外部 Modifier を最初に適用
- [ ] Design Token を使用 (ハードコード禁止)
- [ ] ステートレス設計 (可能な限り)
- [ ] Preview を追加 (private, SortTheme ラップ)
- [ ] contentDescription を設定 (アクセシビリティ)
- [ ] KDoc を追加
