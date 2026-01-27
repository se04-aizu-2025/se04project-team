package dotnet.sort.domain.quiz.generator

import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.quiz.model.QuizQuestion

/**
 * クイズ問題を生成するジェネレータ。
 */
interface QuizGenerator {
    /**
     * 指定された条件でクイズ問題を生成する。
     *
     * @param type アルゴリズムの種類
     * @param arraySize 配列のサイズ
     * @param timeLimitSeconds 制限時間（秒）
     * @return 生成されたクイズ問題
     */
    fun generate(
        type: SortType,
        arraySize: Int = 10,
        timeLimitSeconds: Int = 10
    ): QuizQuestion
}
