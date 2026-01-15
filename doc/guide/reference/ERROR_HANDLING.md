# エラーハンドリングガイド

このガイドでは、エラー処理の規則とパターンを定義します。

---

## 基本方針

| 方針 | 詳細 |
|------|------|
| **Fail Fast** | 早期に検証、早期に失敗 |
| **明示的な型** | 例外よりも Result/sealed class |
| **ユーザーフレンドリー** | 技術的エラーを人間向けに |
| **ログ記録** | デバッグ用に詳細をログ |

---

## Domain 層

### パターン: Result 型

```kotlin
// Result を使用
sealed class SortResult {
    data class Success(
        val finalArray: List<Int>,
        val steps: List<SortSnapshot>,
        val metrics: ComplexityMetrics
    ) : SortResult()
    
    data class Error(
        val type: SortErrorType,
        val message: String
    ) : SortResult()
}

enum class SortErrorType {
    EMPTY_INPUT,
    INPUT_TOO_LARGE,
    ALGORITHM_TIMEOUT,
    UNKNOWN
}
```

### UseCase でのハンドリング

```kotlin
class ExecuteSortUseCase {
    fun execute(type: SortType, input: List<Int>): SortResult {
        // 早期検証
        if (input.isEmpty()) {
            return SortResult.Error(
                type = SortErrorType.EMPTY_INPUT,
                message = "Input list cannot be empty"
            )
        }
        
        if (input.size > MAX_INPUT_SIZE) {
            return SortResult.Error(
                type = SortErrorType.INPUT_TOO_LARGE,
                message = "Input size exceeds maximum of $MAX_INPUT_SIZE"
            )
        }
        
        return try {
            val algorithm = SortAlgorithmFactory.create(type)
            val result = algorithm.sort(input)
            SortResult.Success(
                finalArray = result.finalArray,
                steps = result.steps,
                metrics = result.metrics
            )
        } catch (e: Exception) {
            SortResult.Error(
                type = SortErrorType.UNKNOWN,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
    
    companion object {
        private const val MAX_INPUT_SIZE = 1000
    }
}
```

---

## Presentation 層

### ViewModel でのハンドリング

```kotlin
class SortViewModel(
    private val executeSortUseCase: ExecuteSortUseCase
) : BaseViewModel<SortState, SortIntent>(SortState()) {

    override fun handleError(throwable: Throwable) {
        updateState {
            copy(
                isLoading = false,
                hasError = true,
                errorMessage = throwable.message ?: "An error occurred"
            )
        }
    }

    private fun startSort() {
        execute {
            updateState { copy(isLoading = true, hasError = false) }
            
            when (val result = executeSortUseCase.execute(
                state.value.algorithm,
                state.value.currentNumbers
            )) {
                is SortResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            sortResult = result,
                            isPlaying = true
                        )
                    }
                }
                is SortResult.Error -> {
                    updateState {
                        copy(
                            isLoading = false,
                            hasError = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
}
```

### State でのエラー表現

```kotlin
data class SortState(
    // ... other properties
    
    // エラー状態
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val errorType: SortErrorType? = null
) : UiState {
    
    // ヘルパープロパティ
    val isErrorVisible: Boolean
        get() = hasError && errorMessage != null
}
```

---

## UI でのエラー表示

### パターン

```kotlin
@Composable
fun SortScreen(viewModel: SortViewModel) {
    val state by viewModel.state.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        // メインコンテンツ
        SortContent(state = state)
        
        // エラー表示
        if (state.isErrorVisible) {
            ErrorSnackbar(
                message = state.errorMessage!!,
                onDismiss = { viewModel.send(SortIntent.DismissError) }
            )
        }
    }
}

@Composable
fun ErrorSnackbar(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        action = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    ) {
        Text(message)
    }
}
```

---

## 入力検証

### パターン

```kotlin
// Composable での入力検証
@Composable
fun ArraySizeInput(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(value.toString()) }
    var isError by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            val parsed = newText.toIntOrNull()
            
            when {
                parsed == null -> {
                    isError = true
                }
                parsed < MIN_SIZE || parsed > MAX_SIZE -> {
                    isError = true
                }
                else -> {
                    isError = false
                    onValueChange(parsed)
                }
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text("Enter a value between $MIN_SIZE and $MAX_SIZE")
            }
        },
        modifier = modifier
    )
}
```

---

## 例外 vs Result

### 使い分け

| 状況 | 推奨 |
|------|------|
| **予期されるエラー** (空入力、範囲外) | `Result` / `sealed class` |
| **予期しないエラー** (システムエラー) | 例外 + CoroutineExceptionHandler |
| **プログラミングエラー** | `require()`, `check()` |

### 例

```kotlin
// ✅ 予期されるエラー → Result
fun generate(size: Int): GenerateResult {
    if (size <= 0) {
        return GenerateResult.Error("Size must be positive")
    }
    // ...
}

// ✅ プログラミングエラー → require/check
fun sort(input: List<Int>): SortResult {
    require(input.isNotEmpty()) { "Input must not be empty" }
    check(isInitialized) { "Algorithm must be initialized first" }
    // ...
}
```

---

## 禁止事項

```kotlin
// ❌ 禁止 - 例外を握りつぶす
try {
    riskyOperation()
} catch (e: Exception) {
    // 何もしない
}

// ❌ 禁止 - 汎用的すぎる catch
try {
    operation()
} catch (e: Exception) {  // 全ての例外をキャッチ
    showError("Something went wrong")
}

// ❌ 禁止 - ユーザーに技術的メッセージ
showError(exception.stackTraceToString())  // スタックトレースをそのまま表示
```

---

## チェックリスト

エラーハンドリングを実装する場合:

- [ ] Input validation を最初に行う
- [ ] Result 型または sealed class を使用
- [ ] ViewModel で例外をキャッチ
- [ ] State にエラー状態を含める
- [ ] UI でエラーを適切に表示
- [ ] ユーザーフレンドリーなメッセージを提供

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Result Type** | [Kotlin Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/) |
| **Coroutine Exception** | [Exception Handling](https://kotlinlang.org/docs/exception-handling.html) |
| **require/check** | [Preconditions](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/require.html) |
