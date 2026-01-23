package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.model.SortType

object AlgorithmContentProvider {
    fun getHistory(sortType: SortType): AlgorithmHistory {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmHistory(
                sortType = SortType.BUBBLE,
                originYear = "1956",
                inventor = "Edward Friend",
                description = "Originally analyzed by Edward Friend in 1956. The name 'Bubble Sort' was popularized in the early 1960s, describing how smaller elements 'bubble' to the top of the list."
            )
            SortType.SELECTION -> AlgorithmHistory(
                sortType = SortType.SELECTION,
                originYear = "Pre-computer era",
                inventor = "Unknown",
                description = "Selection Sort is one of the most intuitive sorting methods, likely used by humans manually long before computers. It minimizes memory writes but is inefficient for large datasets."
            )
            SortType.INSERTION -> AlgorithmHistory(
                sortType = SortType.INSERTION,
                originYear = "1946",
                inventor = "John Mauchly",
                description = "Insertion Sort is the natural way humans sort cards in a hand. It was one of the earliest sorting algorithms implemented on computers, mentioned by John Mauchly in 1946."
            )
            SortType.SHELL -> AlgorithmHistory(
                sortType = SortType.SHELL,
                originYear = "1959",
                inventor = "Donald Shell",
                description = "Invented by Donald Shell in 1959 as a generalization of Insertion Sort. It was one of the first algorithms to break the O(n²) barrier for many practical cases."
            )
            SortType.MERGE -> AlgorithmHistory(
                sortType = SortType.MERGE,
                originYear = "1945",
                inventor = "John von Neumann",
                description = "Invented by John von Neumann in 1945. It uses a divide-and-conquer approach and is a classic example of an efficient, stable sort."
            )
            SortType.QUICK -> AlgorithmHistory(
                sortType = SortType.QUICK,
                originYear = "1959",
                inventor = "Tony Hoare",
                description = "Developed by Tony Hoare in 1959 (published 1961) while he was a visiting student at Moscow State University. It remains one of the most widely used general-purpose sorting algorithms."
            )
            SortType.HEAP -> AlgorithmHistory(
                sortType = SortType.HEAP,
                originYear = "1964",
                inventor = "J. W. J. Williams",
                description = "Invented by J. W. J. Williams in 1964. It introduced the heap data structure specifically for this algorithm, providing a worst-case O(n log n) sort."
            )
        }
    }

    fun getConcept(sortType: SortType): AlgorithmConcept {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmConcept(
                sortType = SortType.BUBBLE,
                howItWorks = "Bubble Sort repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order. This process is repeated until the list is sorted.",
                keyIdea = "Smaller elements 'bubble' to the top (beginning) of the list through repeated adjacent comparisons and swaps.",
                steps = listOf(
                    "Start at the beginning of the array",
                    "Compare each pair of adjacent elements",
                    "Swap them if they are in the wrong order",
                    "Repeat the process for the entire array",
                    "After each pass, the largest unsorted element moves to its final position",
                    "Continue until no swaps are needed"
                )
            )
            SortType.SELECTION -> AlgorithmConcept(
                sortType = SortType.SELECTION,
                howItWorks = "Selection Sort divides the array into sorted and unsorted regions. It repeatedly selects the smallest (or largest) element from the unsorted region and moves it to the end of the sorted region.",
                keyIdea = "Find the minimum element in the unsorted portion and place it at the beginning of that portion.",
                steps = listOf(
                    "Divide the array into sorted (initially empty) and unsorted regions",
                    "Find the minimum element in the unsorted region",
                    "Swap it with the first element of the unsorted region",
                    "Move the boundary between sorted and unsorted regions one element to the right",
                    "Repeat until the entire array is sorted"
                )
            )
            SortType.INSERTION -> AlgorithmConcept(
                sortType = SortType.INSERTION,
                howItWorks = "Insertion Sort builds the final sorted array one item at a time. It takes each element and inserts it into its correct position within the already sorted portion.",
                keyIdea = "Similar to sorting playing cards in your hand - pick up one card at a time and insert it into its correct position.",
                steps = listOf(
                    "Start with the second element (first is considered sorted)",
                    "Compare the current element with elements in the sorted portion",
                    "Shift larger elements one position to the right",
                    "Insert the current element into its correct position",
                    "Move to the next element and repeat",
                    "Continue until all elements are processed"
                )
            )
            SortType.SHELL -> AlgorithmConcept(
                sortType = SortType.SHELL,
                howItWorks = "Shell Sort is a generalization of Insertion Sort. It starts by sorting pairs of elements far apart from each other, then progressively reduces the gap between elements to be compared.",
                keyIdea = "Sort elements at intervals (gaps), gradually reducing the gap size until it becomes 1 (standard insertion sort).",
                steps = listOf(
                    "Choose an initial gap size (e.g., n/2)",
                    "Perform insertion sort on elements that are 'gap' distance apart",
                    "Reduce the gap (e.g., gap = gap/2)",
                    "Repeat the gapped insertion sort with the new gap",
                    "Continue until gap = 1",
                    "Perform a final insertion sort with gap = 1"
                )
            )
            SortType.MERGE -> AlgorithmConcept(
                sortType = SortType.MERGE,
                howItWorks = "Merge Sort uses a divide-and-conquer approach. It divides the array into two halves, recursively sorts them, and then merges the two sorted halves back together.",
                keyIdea = "Divide the problem into smaller subproblems, solve them recursively, then combine the solutions.",
                steps = listOf(
                    "Divide the unsorted array into two halves",
                    "Recursively sort each half",
                    "Merge the two sorted halves into a single sorted array",
                    "During merge: compare elements from both halves and place smaller one first",
                    "Continue until all elements are merged",
                    "Base case: an array of size 1 is already sorted"
                )
            )
            SortType.QUICK -> AlgorithmConcept(
                sortType = SortType.QUICK,
                howItWorks = "Quick Sort picks an element as a pivot and partitions the array around it. Elements smaller than the pivot go to the left, larger elements go to the right. Then it recursively sorts the subarrays.",
                keyIdea = "Choose a pivot, partition the array so elements are on the correct side of the pivot, then recursively sort each partition.",
                steps = listOf(
                    "Choose a pivot element from the array",
                    "Partition: rearrange array so elements < pivot are on left, elements > pivot are on right",
                    "Place the pivot in its final sorted position",
                    "Recursively apply Quick Sort to the left partition",
                    "Recursively apply Quick Sort to the right partition",
                    "Base case: partition of size 0 or 1 is already sorted"
                )
            )
            SortType.HEAP -> AlgorithmConcept(
                sortType = SortType.HEAP,
                howItWorks = "Heap Sort first builds a max heap from the input data. It then repeatedly extracts the maximum element from the heap and rebuilds the heap until all elements are sorted.",
                keyIdea = "Use a heap data structure to efficiently find and remove the maximum element repeatedly.",
                steps = listOf(
                    "Build a max heap from the input array",
                    "The largest element is now at the root of the heap",
                    "Swap the root with the last element in the heap",
                    "Reduce the heap size by 1",
                    "Heapify the root to restore the max heap property",
                    "Repeat steps 2-5 until heap size is 1"
                )
            )
        }
    }

    fun getComplexity(sortType: SortType): AlgorithmComplexity {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmComplexity(
                sortType = SortType.BUBBLE,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Best case occurs when the array is already sorted (one pass). Average and worst cases require nested loops to traverse the array n times.",
                spaceComplexityExplanation = "Sorts in-place, requiring only a constant amount of extra memory for temporary variables.",
                intuition = "Comparing every pair requires roughly n² operations. With an 'already sorted' check, we can stop early."
            )
            SortType.SELECTION -> AlgorithmComplexity(
                sortType = SortType.SELECTION,
                timeComplexityBest = "O(n²)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Always performs O(n²) comparisons regardless of the initial order, as it must scan the remaining unsorted portion to find the minimum.",
                spaceComplexityExplanation = "Sorts in-place, using minimal extra space.",
                intuition = "We must loop through the entire remaining list for every single position to find the absolute smallest item."
            )
            SortType.INSERTION -> AlgorithmComplexity(
                sortType = SortType.INSERTION,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Linear time O(n) if the array is already sorted. Quadratic O(n²) in average/worst cases due to shifting elements.",
                spaceComplexityExplanation = "In-place sorting.",
                intuition = "Like sorting cards: usually quick to slide a card into place, but if we have to shift every card for every new card, it's slow."
            )
            SortType.SHELL -> AlgorithmComplexity(
                sortType = SortType.SHELL,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "Depends on gap sequence",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Highly dependent on the gap sequence used. Can be much faster than O(n²) (e.g., O(n^1.5)) but worst case remains O(n²) with poor gaps.",
                spaceComplexityExplanation = "In-place sorting.",
                intuition = "Moving elements long distances early on reduces the number of small shifts required later, improving efficiency."
            )
            SortType.MERGE -> AlgorithmComplexity(
                sortType = SortType.MERGE,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "O(n log n)",
                timeComplexityWorst = "O(n log n)",
                spaceComplexity = "O(n)",
                timeComplexityExplanation = "Consistently O(n log n) because it always divides the array in half (log n levels) and merges them linearly O(n).",
                spaceComplexityExplanation = "Requires O(n) auxiliary space for the temporary arrays used during merging.",
                intuition = "Splitting is fast. Merging two sorted lists is efficient (linear). We do this 'splitting and merging' log(n) times."
            )
            SortType.QUICK -> AlgorithmComplexity(
                sortType = SortType.QUICK,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "O(n log n)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(log n)",
                timeComplexityExplanation = "O(n log n) on average due to partitioning. Degrades to O(n²) if the pivot is always the smallest/largest element (e.g., sorted array).",
                spaceComplexityExplanation = "In-place, but requires O(log n) stack space for recursion.",
                intuition = "If we pick a good pivot, we cut the problem in half each time. If we pick a bad pivot (min/max), we only shrink the problem by 1."
            )
            SortType.HEAP -> AlgorithmComplexity(
                sortType = SortType.HEAP,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "O(n log n)",
                timeComplexityWorst = "O(n log n)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Guaranteed O(n log n). Building the heap takes O(n), and extracting n elements takes O(log n) each.",
                spaceComplexityExplanation = "In-place sorting (using the array itself as the heap).",
                intuition = "A heap structure guarantees we can find and remove the max element in logarithmic time, regardless of data order."
            )
        }
    }
    
    fun getUseCase(sortType: SortType): AlgorithmUseCase {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmUseCase(
                sortType = SortType.BUBBLE,
                bestUseCases = listOf(
                    "Educational purpose (easy to understand)",
                    "Checking if a list is already sorted (one pass)",
                    "Very small datasets where code simplicity is key"
                ),
                notRecommended = listOf(
                    "Large datasets (very slow)",
                    "Performance-critical applications",
                    "Reverse-sorted data (worst case)"
                ),
                realWorldExamples = listOf(
                    "Rarely used in production",
                    "Computer graphics (detecting nearly sorted polygons)"
                )
            )
            SortType.SELECTION -> AlgorithmUseCase(
                sortType = SortType.SELECTION,
                bestUseCases = listOf(
                    "When memory writes are extremely expensive (minimizes swaps)",
                    "Small lists",
                    "Embedded systems with limited write cycles"
                ),
                notRecommended = listOf(
                    "Large datasets",
                    "Already sorted lists (still performs comparisons)"
                ),
                realWorldExamples = listOf(
                    "Flash memory sorting (minimizing writes)",
                    "Simple microcontrollers"
                )
            )
            SortType.INSERTION -> AlgorithmUseCase(
                sortType = SortType.INSERTION,
                bestUseCases = listOf(
                    "Small arrays (typically < 50 elements)",
                    "Nearly sorted arrays (extremely fast)",
                    "Sorting data as it arrives (online sorting)"
                ),
                notRecommended = listOf(
                    "Large, random datasets",
                    "Reverse-sorted data"
                ),
                realWorldExamples = listOf(
                    "Standard library implementations (e.g., used for small chunks in TimSort)",
                    "Insertion in a sorted database"
                )
            )
            SortType.SHELL -> AlgorithmUseCase(
                sortType = SortType.SHELL,
                bestUseCases = listOf(
                    "Medium-sized arrays",
                    "Systems with limited stack space (no recursion)",
                    "Hardware implementations (simple logic)"
                ),
                notRecommended = listOf(
                    "Very large datasets (Merge/Quick sort are better)",
                    "When stability (preserving order) is required"
                ),
                realWorldExamples = listOf(
                    "Embedded systems (uClibc qsort)",
                    "Older operating system kernels"
                )
            )
            SortType.MERGE -> AlgorithmUseCase(
                sortType = SortType.MERGE,
                bestUseCases = listOf(
                    "Sorting linked lists (no random access needed)",
                    "Large datasets (stable and guaranteed O(n log n))",
                    "External sorting (data doesn't fit in RAM)"
                ),
                notRecommended = listOf(
                    "Memory-constrained environments (requires O(n) space)",
                    "Small arrays (slower than insertion sort due to overhead)"
                ),
                realWorldExamples = listOf(
                    "Java's Collections.sort (legacy)",
                    "Python's Timsort (hybrid of Merge and Insertion)",
                    "Perl, Firefox (Array.prototype.sort)",
                    "Database external merge sort"
                )
            )
            SortType.QUICK -> AlgorithmUseCase(
                sortType = SortType.QUICK,
                bestUseCases = listOf(
                    "General-purpose sorting for arrays",
                    "Large datasets in memory",
                    "When average performance matters most"
                ),
                notRecommended = listOf(
                    "Worst-case sensitive applications (unless randomized pivot used)",
                    "Stable sort requirements (Quick Sort is unstable)",
                    "Already sorted data (with naive pivot)"
                ),
                realWorldExamples = listOf(
                    "C standard library (qsort)",
                    "C++ STL (std::sort - usually hybrid)",
                    "Java's Arrays.sort (for primitives)",
                    "Unix sort command"
                )
            )
            SortType.HEAP -> AlgorithmUseCase(
                sortType = SortType.HEAP,
                bestUseCases = listOf(
                    "Systems requiring guaranteed O(n log n) with O(1) space",
                    "Real-time systems (consistent performance)",
                    "Priority queues"
                ),
                notRecommended = listOf(
                    "When stability is required",
                    "When cache performance is critical (poor locality of reference)",
                    "Small arrays"
                ),
                realWorldExamples = listOf(
                    "Linux Kernel (heapsort)",
                    "C++ STL (std::partial_sort)",
                    "Introsort (fallback for QuickSort depth)"
                )
            )
        }
    }
}
