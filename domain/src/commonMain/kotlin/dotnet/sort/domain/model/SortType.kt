package dotnet.sort.domain.model

/**
 * サポートされているソートアルゴリズムの種類
 */
/**
 * アプリケーションでサポートされているソートアルゴリズムの種類を定義する列挙型。
 *
 * UIでの選択肢や、アルゴリズムの生成（Factory）に使用されます。
 *
 * @property displayName UI表示用のユーザーフレンドリーな名称
 */

enum class SortType(val displayName: String) {
    BUBBLE("Bubble Sort"),
    SELECTION("Selection Sort"),
    INSERTION("Insertion Sort"),
    SHELL("Shell Sort"),
    MERGE("Merge Sort"),
    QUICK("Quick Sort"),
    HEAP("Heap Sort"),
    COUNTING("Counting Sort"),
    RADIX("Radix Sort"),
    BUCKET("Bucket Sort"),
    TIM("Tim Sort"),
}