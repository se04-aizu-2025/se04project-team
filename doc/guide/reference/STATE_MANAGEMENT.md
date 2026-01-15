# 状態管理 (MVI) ガイド

このガイドでは、MVI (Model-View-Intent) パターンの実装規則を定義します。

---

## 概念

```
┌───────────────────────────────────────────────────────────┐
│                          View                              │
│  ┌─────────────┐                      ┌─────────────────┐ │
│  │ UI を描画   │ ◄────── observe ──── │ State (Flow)    │ │
│  └─────────────┘                      └─────────────────┘ │
│         │                                      ↑          │
│         │ User Input                           │          │
│         ↓                                      │          │
│  ┌─────────────┐                      ┌─────────────────┐ │
│  │ Intent 発火 │ ─────── send ──────► │ ViewModel       │ │
│  └─────────────┘                      │ (状態を更新)    │ │
│                                       └─────────────────┘ │
└───────────────────────────────────────────────────────────┘
```

**参考**: [Android UDF Best Practices](https://developer.android.com/topic/architecture/ui-layer#udf)

---

## State 定義

### 必須ルール

```kotlin
// ✅ 正しい State 定義
data class SortState(
    // 1. すべて val (不変)
    val algorithm: SortType = SortType.BUBBLE,
    val arraySize: Int = 20,
    val currentNumbers: List<Int> = emptyList(),  // 不変コレクション
    
    // 2. Boolean は is/has プレフィックス
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val hasError: Boolean = false,
    
    // 3. Nullable は最小限
    val sortResult: SortResult? = null,
    val errorMessage: String? = null,
    
    // 4. デフォルト値を必ず設定
    val playbackSpeed: Float = 1.0f,
    val currentStepIndex: Int = 0
) : UiState
```

### 禁止事項

```kotlin
// ❌ 禁止 - var は使わない
data class BadState(
    var isLoading: Boolean = false  // ❌
)

// ❌ 禁止 - MutableList は使わない
data class BadState(
    val items: MutableList<Int> = mutableListOf()  // ❌
)

// ❌ 禁止 - デフォルト値なし
data class BadState(
    val isLoading: Boolean  // デフォルト値がないと初期化が難しい
)
```

### ルール表

| ルール | 詳細 |
|--------|------|
| **data class のみ** | copy() を使った不変更新のため |
| **すべて val** | 不変性を保証 |
| **不変コレクション** | `List`, `Set`, `Map` を使用 |
| **デフォルト値必須** | 初期状態を明確に |
| **UiState インターフェース実装** | 型の統一 |

---

## Intent 定義

### 必須ルール

```kotlin
sealed class SortIntent : Intent {
    // データなし → data object
    data object StartSort : SortIntent()
    data object PauseSort : SortIntent()
    data object ResumeSort : SortIntent()
    data object ResetSort : SortIntent()
    data object StepForward : SortIntent()
    data object StepBackward : SortIntent()
    
    // データあり → data class
    data class SelectAlgorithm(val type: SortType) : SortIntent()
    data class SetArraySize(val size: Int) : SortIntent()
    data class SetSpeed(val speed: Float) : SortIntent()
    data class SeekTo(val index: Int) : SortIntent()
}
```

### ルール表

| ルール | 詳細 |
|--------|------|
| **sealed class 必須** | 網羅的な when を強制 |
| **Intent インターフェース実装** | 型の統一 |
| **動詞で開始** | `Start`, `Set`, `Select`, `Update`, `Load` |
| **データなし → data object** | シングルトン |
| **データあり → data class** | プロパティとして引数を保持 |

---

## ViewModel 構造

### BaseViewModel

```kotlin
abstract class BaseViewModel<S : UiState, I : Intent>(
    initialState: S
) : ViewModel(), UnidirectionalViewModel<S, I> {

    // private な MutableStateFlow
    private val _state = MutableStateFlow(initialState)
    
    // public な StateFlow (読み取り専用)
    override val state: StateFlow<S> = _state.asStateFlow()

    // state 更新関数 (copy を使用)
    protected fun updateState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    // 非同期処理実行
    protected fun execute(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) { block() }
    }
    
    // エラーハンドラ
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }
    
    protected open fun handleError(throwable: Throwable) {
        // サブクラスでオーバーライド
    }
}
```

### 具体実装パターン

```kotlin
class SortViewModel(
    private val executeSortUseCase: ExecuteSortUseCase,
    private val arrayGenerator: ArrayGenerator
) : BaseViewModel<SortState, SortIntent>(SortState()) {

    init {
        generateInitialArray()
    }

    // ✅ 必須: send メソッドで Intent を処理
    override fun send(intent: SortIntent) {
        when (intent) {
            // 単純な状態更新
            is SortIntent.SelectAlgorithm -> updateState { 
                copy(algorithm = intent.type) 
            }
            
            // 複合アクション (状態更新 + 副作用)
            is SortIntent.SetArraySize -> {
                updateState { copy(arraySize = intent.size) }
                generateNewArray()
            }
            
            // 非同期処理
            is SortIntent.StartSort -> startSort()
            
            // 単純なトグル
            is SortIntent.PauseSort -> updateState { copy(isPlaying = false) }
            is SortIntent.ResumeSort -> updateState { copy(isPlaying = true) }
            
            // すべての Intent を網羅 (sealed class なので強制)
            is SortIntent.ResetSort -> reset()
            is SortIntent.StepForward -> stepForward()
            is SortIntent.StepBackward -> stepBackward()
            is SortIntent.SetSpeed -> updateState { copy(playbackSpeed = intent.speed) }
            is SortIntent.SeekTo -> seekTo(intent.index)
        }
    }
    
    private fun startSort() {
        execute {
            updateState { copy(isLoading = true, hasError = false) }
            
            try {
                val result = executeSortUseCase.execute(
                    state.value.algorithm,
                    state.value.currentNumbers
                )
                updateState { 
                    copy(
                        isLoading = false,
                        sortResult = result,
                        isPlaying = true
                    ) 
                }
            } catch (e: Exception) {
                updateState { 
                    copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}
```

---

## View での State 収集

### 必須パターン

```kotlin
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel()
) {
    // ✅ collectAsState で Flow を収集
    val state by viewModel.state.collectAsState()
    
    // ✅ state プロパティを UI に渡す
    SortContent(
        algorithm = state.algorithm,
        isLoading = state.isLoading,
        numbers = state.currentNumbers,
        onStartSort = { viewModel.send(SortIntent.StartSort) },
        onSelectAlgorithm = { viewModel.send(SortIntent.SelectAlgorithm(it)) }
    )
}
```

### 禁止事項

```kotlin
// ❌ 禁止 - ViewModel のメソッドを直接呼び出さない
fun SortScreen(viewModel: SortViewModel) {
    Button(onClick = { viewModel.startSort() })  // ❌ Intent を使う
}

// ❌ 禁止 - state を直接変更しない
fun SortScreen(viewModel: SortViewModel) {
    val state = viewModel.state.value
    state.isLoading = true  // ❌ data class は不変
}
```

---

## チェックリスト

新しい機能画面を追加する場合:

- [ ] `{Feature}State` を定義 (data class, UiState)
- [ ] `{Feature}Intent` を定義 (sealed class, Intent)
- [ ] `{Feature}ViewModel` を実装 (BaseViewModel 継承)
- [ ] `send()` で全 Intent を処理 (網羅的 when)
- [ ] Screen で `collectAsState()` を使用

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **UI Layer** | [Android UI Layer](https://developer.android.com/topic/architecture/ui-layer) |
| **UDF** | [Unidirectional Data Flow](https://developer.android.com/topic/architecture/ui-layer#udf) |
| **State Holders** | [State Holders](https://developer.android.com/topic/architecture/ui-layer/stateholders) |
| **UI Events** | [UI Events](https://developer.android.com/topic/architecture/ui-layer/events) |
| **ViewModel** | [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel) |
