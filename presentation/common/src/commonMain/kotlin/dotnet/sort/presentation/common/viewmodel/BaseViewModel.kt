package dotnet.sort.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MVIパターンを実装するViewModelの基底クラス。
 *
 * [viewModelScope] を管理し、エラーハンドリング付きでコルーチンを起動する簡易手段を提供します。
 */
abstract class BaseViewModel<S : UiState, I : Intent>(
    initialState: S
) : ViewModel(), UnidirectionalViewModel<S, I> {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<S> = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    protected fun updateState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    /**
     * デフォルトの例外ハンドラを使用して、[viewModelScope] 内でコルーチンを起動します。
     *
     * @param block 実行するコルーチンのコードブロック。
     */
    protected fun execute(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            block()
        }
    }

    /**
     * [execute] 内で発生した例外を処理します。
     * カスタムのエラーハンドリングを提供するには、このメソッドをオーバーライドしてください。
     *
     * @param throwable 発生した例外。
     */
    protected open fun handleError(throwable: Throwable) {
        // デフォルト実装では、エラーを標準エラー出力に出力します
        println("BaseViewModel Error: ${throwable.message}")
        throwable.printStackTrace()
    }
}
