package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.model.SortType

/**
 * アルゴリズムの計算量と複雑性に関する情報を保持するデータクラス
 *
 * @param sortType 対象のソートアルゴリズム
 * @param timeComplexityBest 最良ケースの時間計算量 (Big O)
 * @param timeComplexityAverage 平均ケースの時間計算量 (Big O)
 * @param timeComplexityWorst 最悪ケースの時間計算量 (Big O)
 * @param spaceComplexity 空間計算量 (Big O)
 * @param timeComplexityExplanation 時間計算量の理由説明
 * @param spaceComplexityExplanation 空間計算量の理由説明
 * @param intuition 直感的な説明 ("なぜその計算量になるのか")
 */
data class AlgorithmComplexity(
    val sortType: SortType,
    val timeComplexityBest: String,
    val timeComplexityAverage: String,
    val timeComplexityWorst: String,
    val spaceComplexity: String,
    val timeComplexityExplanation: String,
    val spaceComplexityExplanation: String,
    val intuition: String
)
