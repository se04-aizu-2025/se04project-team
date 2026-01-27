package dotnet.sort.presentation.feature.learn

import dotnet.sort.model.SortType

data class LearnAlgorithmDetail(
    val type: SortType,
    val history: String,
    val principle: String,
    val complexity: String,
    val spaceComplexity: String,
    val useCases: String,
    val kotlinCode: String,
    val example: String,
)

fun buildAlgorithmDetails(): Map<SortType, LearnAlgorithmDetail> {
    return listOf(
        LearnAlgorithmDetail(
            type = SortType.BUBBLE,
            history = "Named by Edward Friend (1956).",
            principle = "Compare adjacent elements and swap if out of order.",
            complexity = "Best: O(n), Avg/Worst: O(n²)",
            spaceComplexity = "O(1)",
            useCases = "Nearly sorted data or teaching basics.",
            kotlinCode = """
fun bubbleSort(a: IntArray) {
    for (i in 0 until a.lastIndex) {
        for (j in 0 until a.lastIndex - i) {
            if (a[j] > a[j + 1]) {
                val tmp = a[j]
                a[j] = a[j + 1]
                a[j + 1] = tmp
            }
        }
    }
}
""".trimIndent(),
            example = "[5,3,1,4,2] -> [3,1,4,2,5] -> ... -> [1,2,3,4,5]",
        ),
        LearnAlgorithmDetail(
            type = SortType.SELECTION,
            history = "Classic selection strategy used since early sorting studies.",
            principle = "Select the minimum and place it at the front.",
            complexity = "Best/Avg/Worst: O(n²)",
            spaceComplexity = "O(1)",
            useCases = "Small arrays; minimal swaps.",
            kotlinCode = """
fun selectionSort(a: IntArray) {
    for (i in 0 until a.lastIndex) {
        var min = i
        for (j in i + 1 until a.size) {
            if (a[j] < a[min]) min = j
        }
        val tmp = a[i]
        a[i] = a[min]
        a[min] = tmp
    }
}
""".trimIndent(),
            example = "[5,3,1,4,2] -> pick 1 -> [1,3,5,4,2] -> ...",
        ),
        LearnAlgorithmDetail(
            type = SortType.INSERTION,
            history = "Derived from card-sorting practices.",
            principle = "Insert each element into the already-sorted prefix.",
            complexity = "Best: O(n), Avg/Worst: O(n²)",
            spaceComplexity = "O(1)",
            useCases = "Small or nearly sorted datasets.",
            kotlinCode = """
fun insertionSort(a: IntArray) {
    for (i in 1 until a.size) {
        val key = a[i]
        var j = i - 1
        while (j >= 0 && a[j] > key) {
            a[j + 1] = a[j]
            j--
        }
        a[j + 1] = key
    }
}
""".trimIndent(),
            example = "[5,3,1,4,2] -> insert 3 -> [3,5,1,4,2] -> ...",
        ),
        LearnAlgorithmDetail(
            type = SortType.SHELL,
            history = "Proposed by Donald Shell (1959).",
            principle = "Perform gapped insertion sorts with shrinking gaps.",
            complexity = "Avg ~ O(n^(3/2)), Worst: O(n²)",
            spaceComplexity = "O(1)",
            useCases = "Medium-sized arrays needing faster than insertion.",
            kotlinCode = """
fun shellSort(a: IntArray) {
    var gap = a.size / 2
    while (gap > 0) {
        for (i in gap until a.size) {
            val temp = a[i]
            var j = i
            while (j >= gap && a[j - gap] > temp) {
                a[j] = a[j - gap]
                j -= gap
            }
            a[j] = temp
        }
        gap /= 2
    }
}
""".trimIndent(),
            example = "Gap=2 then Gap=1 to finish sort.",
        ),
        LearnAlgorithmDetail(
            type = SortType.MERGE,
            history = "John von Neumann (1945).",
            principle = "Divide array and merge sorted halves.",
            complexity = "Best/Avg/Worst: O(n log n)",
            spaceComplexity = "O(n)",
            useCases = "Stable sorting and large datasets.",
            kotlinCode = """
fun mergeSort(a: IntArray): IntArray {
    if (a.size <= 1) return a
    val mid = a.size / 2
    val left = mergeSort(a.copyOfRange(0, mid))
    val right = mergeSort(a.copyOfRange(mid, a.size))
    return merge(left, right)
}
""".trimIndent(),
            example = "Split -> sort halves -> merge.",
        ),
        LearnAlgorithmDetail(
            type = SortType.QUICK,
            history = "Tony Hoare (1960).",
            principle = "Partition by pivot; recurse on subarrays.",
            complexity = "Best/Avg: O(n log n), Worst: O(n²)",
            spaceComplexity = "O(log n) average",
            useCases = "General-purpose fast in-memory sorting.",
            kotlinCode = """
fun quickSort(a: IntArray, lo: Int = 0, hi: Int = a.lastIndex) {
    if (lo >= hi) return
    val p = partition(a, lo, hi)
    quickSort(a, lo, p - 1)
    quickSort(a, p + 1, hi)
}
""".trimIndent(),
            example = "Choose pivot, partition, recurse.",
        ),
        LearnAlgorithmDetail(
            type = SortType.HEAP,
            history = "Based on heap data structure (1964).",
            principle = "Build heap and repeatedly extract max/min.",
            complexity = "Best/Avg/Worst: O(n log n)",
            spaceComplexity = "O(1)",
            useCases = "In-place sorting with guaranteed bounds.",
            kotlinCode = """
fun heapSort(a: IntArray) {
    buildMaxHeap(a)
    for (i in a.lastIndex downTo 1) {
        val tmp = a[0]
        a[0] = a[i]
        a[i] = tmp
        heapify(a, 0, i)
    }
}
""".trimIndent(),
            example = "Build heap -> extract max repeatedly.",
        ),
    ).associateBy { it.type }
}
