package dotnet.sort.designsystem.components.atoms

/**
 * バーの状態を表す列挙型。
 *
 * ソート可視化において、各バーの現在の状態を示します。
 */
enum class BarState {
    /** デフォルト状態 - 通常のバー */
    Default,

    /** 比較中 - 現在比較されている要素 */
    Comparing,

    /** 交換中 - スワップ処理中の要素 */
    Swapping,

    /** ソート済み - 正しい位置に配置された要素 */
    Sorted,

    /** ピボット - クイックソートのピボット要素 */
    Pivot,

    /** 選択中 - 選択ソートの最小/最大要素 */
    Selected,
}
