package dotnet.sort.generator

/**
 * 配列生成のタイプを表す列挙型。
 */
enum class ArrayGeneratorType {
    RANDOM,           // 完全ランダム
    ASCENDING,        // 昇順（ソート済み）
    DESCENDING,       // 降順（逆順）
    PARTIALLY_SORTED, // 部分的にソート済み
    DUPLICATES        // 重複要素を含む
}
