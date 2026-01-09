package dotnet.sort.presentation.common.viewmodel

import kotlinx.coroutines.flow.StateFlow

/**
 * 単方向データフロー（Unidirectional Data Flow）を持つViewModelの契約を定義するインターフェース。
 *
 * @param STATE UI状態の型。[UiState] を実装する必要があります。
 * @param INTENT ユーザーの意図の型。[Intent] を実装する必要があります。
 */
interface UnidirectionalViewModel<STATE : UiState, INTENT : Intent> {
    /**
     * UIの現在の状態。[StateFlow] として公開されます。
     */
    val state: StateFlow<STATE>

    /**
     * ユーザーの意図（Intent）を処理します。
     *
     * @param intent 処理するIntent。
     */
    fun send(intent: INTENT)
}
