package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.model.SortType

/**
 * アルゴリズムのユースケースと使用場面を保持するデータクラス
 *
 * @param sortType 対象のソートアルゴリズム
 * @param bestUseCases 最適な使用場面のリスト
 * @param notRecommended 非推奨な場面のリスト
 * @param realWorldExamples 実世界での使用例 (プログラミング言語やライブラリでの採用実績など)
 */
data class AlgorithmUseCase(
    val sortType: SortType,
    val bestUseCases: List<String>,
    val notRecommended: List<String>,
    val realWorldExamples: List<String>
)
