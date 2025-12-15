package dotnet.sort.model

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

    // 必要に応じて BOGO("Bogo Sort") などを追加して拡張性を確保
    // GUI表示用などに toString をオーバーライドしたり、displayNameを使う
}