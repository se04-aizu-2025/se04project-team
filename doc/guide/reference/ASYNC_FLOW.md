# 非同期処理 / Flow ガイド

このガイドでは、Coroutines と Flow の使用規則を定義します。

---

## スコープ

### ViewModel

```kotlin
class SortViewModel : BaseViewModel<SortState, SortIntent>() {
    
    // ✅ viewModelScope を使用
    private fun startSort() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = executeSortUseCase.execute(...)
            updateState { copy(isLoading = false, sortResult = result) }
        }
    }
}
```

### BaseViewModel

```kotlin
abstract class BaseViewModel<S : UiState, I : Intent> {
    // ✅ 共通の非同期ヘルパー
    protected fun execute(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) { block() }
    }
    
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }
}
```

---

## StateFlow パターン

### 必須構造

```kotlin
// ✅ private MutableStateFlow + public StateFlow
private val _state = MutableStateFlow(SortState())
val state: StateFlow<SortState> = _state.asStateFlow()

// ❌ 禁止 - 外部から変更可能
val state = MutableStateFlow(SortState())  // ❌
```

### 更新

```kotlin
// ✅ copy() で更新
private fun updateState(reducer: SortState.() -> SortState) {
    _state.value = _state.value.reducer()
}

// 使用
updateState { copy(isLoading = true) }
```

---

## Composable での収集

### collectAsState

```kotlin
@Composable
fun SortScreen(viewModel: SortViewModel) {
    // ✅ collectAsState で収集
    val state by viewModel.state.collectAsState()
    
    // state を使用
    if (state.isLoading) {
        CircularProgressIndicator()
    }
}
```

### collectAsStateWithLifecycle

```kotlin
// ✅ ライフサイクル対応 (推奨)
val state by viewModel.state.collectAsStateWithLifecycle()
```

---

## suspend 関数

### Repository

```kotlin
interface SettingsRepository {
    // ✅ I/O 操作は suspend
    suspend fun getTheme(): Theme
    suspend fun setTheme(theme: Theme)
}
```

### UseCase

```kotlin
class LoadHistoryUseCase {
    // ✅ Repository の suspend を呼び出す
    suspend fun execute(): List<HistoryItem> {
        return historyRepository.getAll()
    }
}
```

### ViewModel での呼び出し

```kotlin
private fun loadHistory() {
    viewModelScope.launch {
        updateState { copy(isLoading = true) }
        
        try {
            val history = loadHistoryUseCase.execute()  // suspend
            updateState { copy(isLoading = false, history = history) }
        } catch (e: Exception) {
            updateState { copy(isLoading = false, hasError = true) }
        }
    }
}
```

---

## Flow

### 生成

```kotlin
// ✅ flow ビルダー
fun observeProgress(): Flow<Int> = flow {
    for (i in 0..100) {
        emit(i)
        delay(100)
    }
}
```

### 収集

```kotlin
// ViewModel
viewModelScope.launch {
    observeProgress().collect { progress ->
        updateState { copy(progress = progress) }
    }
}
```

### StateFlow への変換

```kotlin
// ✅ stateIn で StateFlow に
val progress: StateFlow<Int> = observeProgress()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
```

---

## エラーハンドリング

### try-catch

```kotlin
viewModelScope.launch {
    try {
        val result = riskyOperation()
        updateState { copy(data = result) }
    } catch (e: IOException) {
        updateState { copy(hasError = true, errorMessage = "Network error") }
    } catch (e: Exception) {
        updateState { copy(hasError = true, errorMessage = e.message) }
    }
}
```

### CoroutineExceptionHandler

```kotlin
private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    // UI スレッドで状態更新
    _state.value = _state.value.copy(
        isLoading = false,
        hasError = true,
        errorMessage = throwable.message
    )
}

viewModelScope.launch(exceptionHandler) {
    // 例外は handler でキャッチされる
}
```

---

## Dispatchers

### ルール

| Dispatcher | 用途 |
|------------|------|
| `Dispatchers.Main` | UI 更新 (デフォルト) |
| `Dispatchers.IO` | I/O 操作 (ファイル、ネットワーク) |
| `Dispatchers.Default` | CPU 負荷の高い計算 |

### 使い分け

```kotlin
// ✅ withContext で切り替え
suspend fun heavyCalculation(): Result {
    return withContext(Dispatchers.Default) {
        // CPU 負荷の高い処理
    }
}

suspend fun readFile(): String {
    return withContext(Dispatchers.IO) {
        // ファイル読み込み
    }
}
```

---

## キャンセル

### Job の保持

```kotlin
class SortViewModel {
    private var playbackJob: Job? = null
    
    private fun startPlayback() {
        // 既存のジョブをキャンセル
        playbackJob?.cancel()
        
        playbackJob = viewModelScope.launch {
            while (isActive) {  // キャンセル対応
                delay(100)
                stepForward()
            }
        }
    }
    
    private fun stopPlayback() {
        playbackJob?.cancel()
        playbackJob = null
    }
}
```

---

## 禁止事項

```kotlin
// ❌ 禁止 - GlobalScope
GlobalScope.launch { }  // ライフサイクルと無関係

// ❌ 禁止 - runBlocking (本番)
runBlocking { }  // メインスレッドをブロック

// ❌ 禁止 - 外部から直接更新
viewModel._state.value = newState  // private にする
```

---

## チェックリスト

非同期処理を追加する場合:

- [ ] `viewModelScope` を使用
- [ ] `StateFlow` は private MutableStateFlow + public StateFlow
- [ ] I/O は `suspend` 関数
- [ ] エラーは try-catch または ExceptionHandler
- [ ] 適切な Dispatcher を使用
- [ ] キャンセル対応 (`isActive` チェック)

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Coroutines** | [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) |
| **StateFlow** | [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) |
| **viewModelScope** | [Use Coroutines with ViewModel](https://developer.android.com/topic/libraries/architecture/coroutines) |
| **Exception Handling** | [Exception Handling](https://kotlinlang.org/docs/exception-handling.html) |
