package dotnet.sort.usecase

import dotnet.sort.algorithm.SortAlgorithmFactory
import dotnet.sort.model.SortResult
import dotnet.sort.model.SortType
import org.koin.core.annotation.Single

/**
 * ソート実行ユースケース。
 * UI層などのクライアントから要求を受け取り、指定されたアルゴリズムでソートを実行します。
 */
@Single
class ExecuteSortUseCase {

    /**
     * ソートを実行します。
     *
     * @param type 使用するソートアルゴリズムの種類 [SortType]
     * @param input ソート対象の整数リスト
     * @return [SortResult] ソート結果（整列済みリスト、手順スナップショット、統計情報）
     * @throws NotImplementedError 未実装のアルゴリズムが指定された場合
     */
    fun execute(type: SortType, input: List<Int>): SortResult {
        val algorithm = SortAlgorithmFactory.create(type)
        return algorithm.sort(input)
    }
}
