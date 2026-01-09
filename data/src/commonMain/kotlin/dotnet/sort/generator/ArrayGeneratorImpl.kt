package dotnet.sort.generator

import kotlin.random.Random

import org.koin.core.annotation.Single

/**
 * 配列生成のインターフェース実装。
 *
 * 各種タイプに応じた配列を生成します。
 */
@Single
class ArrayGeneratorImpl : ArrayGenerator {

    private val random = Random

    override fun generate(size: Int, type: ArrayGeneratorType): List<Int> {
        return generate(size, type, 1..100)
    }

    override fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> {
        require(size >= 0) { "Size must be non-negative" }
        require(range.first <= range.last) { "Invalid range: ${range.first}..${range.last}" }

        return when (type) {
            ArrayGeneratorType.RANDOM -> generateRandom(size, range)
            ArrayGeneratorType.ASCENDING -> generateAscending(size, range)
            ArrayGeneratorType.DESCENDING -> generateDescending(size, range)
            ArrayGeneratorType.PARTIALLY_SORTED -> generatePartiallySorted(size, range)
            ArrayGeneratorType.DUPLICATES -> generateWithDuplicates(size, range)
        }
    }

    /**
     * ランダムな配列を生成します。
     */
    private fun generateRandom(size: Int, range: IntRange): List<Int> {
        return List(size) { random.nextInt(range.first, range.last + 1) }
    }

    /**
     * 昇順にソートされた配列を生成します（最良ケース用）。
     */
    private fun generateAscending(size: Int, range: IntRange): List<Int> {
        return generateRandom(size, range).sorted()
    }

    /**
     * 降順にソートされた配列を生成します（最悪ケース用）。
     */
    private fun generateDescending(size: Int, range: IntRange): List<Int> {
        return generateRandom(size, range).sortedDescending()
    }

    /**
     * 部分的にソートされた配列を生成します。
     * 昇順ソート後、約10%の要素をランダムにスワップします。
     */
    private fun generatePartiallySorted(size: Int, range: IntRange): List<Int> {
        if (size == 0) return emptyList()

        val sorted = generateAscending(size, range).toMutableList()
        val swapCount = (size / 10).coerceAtLeast(1)

        repeat(swapCount) {
            val i = random.nextInt(size)
            val j = random.nextInt(size)
            val temp = sorted[i]
            sorted[i] = sorted[j]
            sorted[j] = temp
        }
        return sorted
    }

    /**
     * 重複要素を含む配列を生成します。
     * 配列サイズの約1/3のユニークな値からランダムに選択します。
     */
    private fun generateWithDuplicates(size: Int, range: IntRange): List<Int> {
        if (size == 0) return emptyList()

        val uniqueValueCount = (size / 3).coerceAtLeast(1)
        val uniqueValues = List(uniqueValueCount) {
            random.nextInt(range.first, range.last + 1)
        }
        return List(size) { uniqueValues.random(random) }
    }
}
