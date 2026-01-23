package dotnet.sort.domain.usecase

import dotnet.sort.domain.generator.ArrayGenerator
import dotnet.sort.domain.generator.ArrayGeneratorType
import org.koin.core.annotation.Single

/**
 * 配列生成ユースケース。
 *
 * UI層などのクライアントから要求を受け取り、指定されたタイプの配列を生成します。
 */
@Single
class GenerateArrayUseCase(
    private val arrayGenerator: ArrayGenerator
) {

    /**
     * 指定されたサイズとタイプで配列を生成します。
     * 値の範囲はデフォルト（1..100）が使用されます。
     *
     * @param size 生成する配列のサイズ
     * @param type 生成する配列の特徴（ランダム、ソート済みなど）
     * @return 生成された整数配列
     */
    operator fun invoke(size: Int, type: ArrayGeneratorType): List<Int> {
        return arrayGenerator.generate(size, type)
    }

    /**
     * 指定されたサイズ、タイプ、値の範囲で配列を生成します。
     *
     * @param size 生成する配列のサイズ
     * @param type 生成する配列の特徴
     * @param range 要素の値の範囲
     * @return 生成された整数配列
     */
    operator fun invoke(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> {
        return arrayGenerator.generate(size, type, range)
    }
}
