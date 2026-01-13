# ViewModel / Screen ガイド

このガイドでは、ViewModel と Screen の実装規則を定義します。

---

## ViewModel

### 構造

```kotlin
class SortViewModel(
    // 依存の注入
    private val executeSortUseCase: ExecuteSortUseCase,
    private val arrayGenerator: ArrayGenerator
) : BaseViewModel<SortState, SortIntent>(SortState()) {
    
    // 初期化
    init {
        initialize()
    }
    
    // Intent 処理 (必須)
    override fun send(intent: SortIntent) {
        when (intent) {
            // 網羅的に処理
        }
    }
    
    // private メソッド
    private fun initialize() { }
    private fun startSort() { }
}
```

### 命名

| 要素 | 規則 |
|------|------|
| **クラス名** | `{Feature}ViewModel` |
| **ファイル名** | `{Feature}ViewModel.kt` |

---

## BaseViewModel

### 継承

```kotlin
abstract class BaseViewModel<S : UiState, I : Intent>(
    initialState: S
) : ViewModel(), UnidirectionalViewModel<S, I> {
    
    // State
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<S> = _state.asStateFlow()
    
    // State 更新
    protected fun updateState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }
    
    // 非同期実行
    protected fun execute(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) { block() }
    }
    
    // エラーハンドラ
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }
    
    protected open fun handleError(throwable: Throwable) { }
    
    // Intent 処理 (サブクラスで実装)
    abstract fun send(intent: I)
}
```

---

## Intent 処理

### when 式

```kotlin
override fun send(intent: SortIntent) {
    when (intent) {
        // 単純更新
        is SortIntent.SelectAlgorithm -> 
            updateState { copy(algorithm = intent.type) }
        
        // 複合処理
        is SortIntent.SetArraySize -> {
            updateState { copy(arraySize = intent.size) }
            generateNewArray()
        }
        
        // 非同期
        is SortIntent.StartSort -> startSort()
        
        // すべて網羅 (sealed class の強制)
        is SortIntent.PauseSort -> updateState { copy(isPlaying = false) }
        is SortIntent.ResumeSort -> updateState { copy(isPlaying = true) }
        SortIntent.ResetSort -> reset()
        SortIntent.StepForward -> stepForward()
        SortIntent.StepBackward -> stepBackward()
        is SortIntent.SetSpeed -> updateState { copy(playbackSpeed = intent.speed) }
        is SortIntent.SeekTo -> seekTo(intent.index)
    }
}
```

### ルール

- `when` は**網羅的** (else 不要)
- 単純更新は直接 `updateState`
- 複雑な処理は private メソッドに

---

## Screen

### 構造

```kotlin
@OptIn(KoinExperimentalAPI::class)
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel(),  // DI
    onBackClick: () -> Unit = {},                // コールバック
    modifier: Modifier = Modifier                // 最後
) {
    // State 収集
    val state by viewModel.state.collectAsState()
    
    // UI
    Scaffold(
        topBar = { TopAppBar(...) }
    ) { padding ->
        SortContent(
            state = state,
            onIntent = { viewModel.send(it) },
            modifier = Modifier.padding(padding)
        )
    }
}
```

### 命名

| 要素 | 規則 |
|------|------|
| **関数名** | `{Feature}Screen` |
| **ファイル名** | `{Feature}Screen.kt` |

---

## State 収集

### collectAsState

```kotlin
// ✅ 基本
val state by viewModel.state.collectAsState()

// ✅ ライフサイクル対応 (推奨)
val state by viewModel.state.collectAsStateWithLifecycle()
```

---

## Intent 送信

### パターン

```kotlin
// 方法 1: 直接送信
Button(onClick = { viewModel.send(SortIntent.StartSort) })

// 方法 2: コールバック経由
@Composable
fun SortContent(
    state: SortState,
    onIntent: (SortIntent) -> Unit
) {
    Button(onClick = { onIntent(SortIntent.StartSort) })
}

// 使用側
SortContent(
    state = state,
    onIntent = { viewModel.send(it) }
)
```

---

## 画面分割

### パターン

```kotlin
// Screen: DI + State 収集
@Composable
fun SortScreen(viewModel: SortViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    
    SortContent(
        state = state,
        onIntent = { viewModel.send(it) }
    )
}

// Content: 純粋な UI (ステートレス)
@Composable
fun SortContent(
    state: SortState,
    onIntent: (SortIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        // UI 実装
    }
}
```

### 利点

| 分離 | 利点 |
|------|------|
| **Screen** | DI、State収集、ナビゲーション |
| **Content** | 純粋UI、Preview可能、テスト容易 |

---

## Preview

```kotlin
@Preview(showBackground = true)
@Composable
private fun SortContentPreview() {
    SortTheme {
        SortContent(
            state = SortState(
                algorithm = SortType.QUICK,
                isLoading = false
            ),
            onIntent = { }
        )
    }
}
```

---

## 禁止事項

```kotlin
// ❌ 禁止 - Screen に NavController
@Composable
fun SortScreen(navController: NavController) {
    Button(onClick = { navController.navigate(...) })  // ❌
}

// ❌ 禁止 - ViewModel メソッド直接呼び出し
@Composable
fun SortScreen(viewModel: SortViewModel) {
    Button(onClick = { viewModel.startSort() })  // ❌ Intent を使う
}

// ❌ 禁止 - State を直接変更
viewModel.state.value.isLoading = true  // ❌ 不変
```

---

## チェックリスト

新しい画面を追加する場合:

- [ ] `{Feature}State` を定義
- [ ] `{Feature}Intent` を定義
- [ ] `{Feature}ViewModel` を実装 (BaseViewModel継承)
- [ ] `{Feature}Screen.kt` を作成
- [ ] `{Feature}Content` (ステートレス) を分離
- [ ] Preview を追加
- [ ] Koin モジュールに登録
- [ ] Navigation に追加

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **ViewModel** | [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel) |
| **UI Layer** | [UI Layer](https://developer.android.com/topic/architecture/ui-layer) |
| **State Holders** | [State Holders](https://developer.android.com/topic/architecture/ui-layer/stateholders) |
