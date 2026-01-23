package dotnet.sort.domain.generator

/**
 * 配列生成のインターフェース。
 *
 * 指定されたサイズ、タイプ、範囲に基づいて配列を生成します。
 */
interface ArrayGenerator {
    /**
     * 指定されたサイズとタイプで配列を生成します。
     * 値の範囲はデフォルト（例: 0..100）が使用されます。
     *
     * @param size生成する配列のサイズ
     * @param type 生成する配列の特徴（ランダム、ソート済みなど）
     * @return 生成された整数配列
     */
    fun generate(size: Int, type: ArrayGeneratorType): List<Int>

    /**
     * 指定されたサイズ、タイプ、値の範囲で配列を生成します。
     *
     * @param size 生成する配列のサイズ
     * @param type 生成する配列の特徴
     * @param range 要素の値の範囲
     * @return 生成された整数配列
     */
    fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int>
}
