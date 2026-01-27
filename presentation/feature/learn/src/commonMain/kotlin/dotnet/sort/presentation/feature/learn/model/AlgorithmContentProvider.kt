package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.domain.model.SortType

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
            SortType.COUNTING -> AlgorithmHistory(
                sortType = SortType.COUNTING,
                originYear = "1954",
                inventor = "Harold H. Seward",
                description = "Introduced by Harold H. Seward in 1954. Counting Sort is a non-comparison sorting algorithm that counts the frequency of each value, then reconstructs the array in order."
            )
            SortType.RADIX -> AlgorithmHistory(
                sortType = SortType.RADIX,
                originYear = "1950s",
                inventor = "Harold H. Seward",
                description = "Radix Sort processes numbers digit by digit, typically using a stable counting sort as a subroutine. It is efficient for fixed-length integer keys."
            )
            SortType.BUCKET -> AlgorithmHistory(
                sortType = SortType.BUCKET,
                originYear = "1950s",
                inventor = "Various researchers",
                description = "Bucket Sort is a distribution-based algorithm that groups elements into buckets by range and sorts each bucket before concatenation."
            )
            SortType.TIM -> AlgorithmHistory(
                sortType = SortType.TIM,
                originYear = "2002",
                inventor = "Tim Peters",
                description = "Tim Sort is a hybrid stable sorting algorithm designed by Tim Peters for Python. It combines run detection, insertion sort, and merge operations."
            )
            SortType.COMB -> AlgorithmHistory(
                sortType = SortType.COMB,
                originYear = "1980",
                inventor = "Włodzimierz Dobosiewicz",
                description = "Comb Sort improves Bubble Sort by comparing elements farther apart and shrinking the gap over time."
            )
            SortType.COCKTAIL -> AlgorithmHistory(
                sortType = SortType.COCKTAIL,
                originYear = "1950s",
                inventor = "Unknown",
                description = "Cocktail Sort is a bidirectional Bubble Sort variant that moves large elements to the end and small elements to the start in each pass."
            )
            SortType.GNOME -> AlgorithmHistory(
                sortType = SortType.GNOME,
                originYear = "2000",
                inventor = "Hamid Sarbazi-Azad",
                description = "Gnome Sort is a simple comparison sort that moves backward to fix local inversions, similar to insertion sort with swaps."
            )
            SortType.ODD_EVEN -> AlgorithmHistory(
                sortType = SortType.ODD_EVEN,
                originYear = "1962",
                inventor = "Kenneth E. Batcher",
                description = "Odd-Even (Brick) Sort compares odd-even indexed pairs alternately and is suitable for parallel execution."
            )
            SortType.BOGO -> AlgorithmHistory(
                sortType = SortType.BOGO,
                originYear = "1960s",
                inventor = "Unknown",
                description = "Bogo Sort is a humorous algorithm that repeatedly shuffles until the array becomes sorted."
            )
            SortType.BITONIC -> AlgorithmHistory(
                sortType = SortType.BITONIC,
                originYear = "1968",
                inventor = "Kenneth E. Batcher",
                description = "Bitonic Sort builds bitonic sequences and merges them, making it practical for parallel sorting networks."
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
            SortType.COUNTING -> AlgorithmConcept(
                sortType = SortType.COUNTING,
                howItWorks = "Counting Sort counts how many times each value appears. It then rebuilds the array by writing each value the number of times it appears.",
                keyIdea = "Use a frequency array (counting array) to place elements directly without comparisons.",
                steps = listOf(
                    "Find the minimum and maximum values",
                    "Create a counting array of size (max - min + 1)",
                    "Count occurrences of each value",
                    "Iterate over the counting array in order",
                    "Write each value back to the original array the counted number of times"
                )
            )
            SortType.RADIX -> AlgorithmConcept(
                sortType = SortType.RADIX,
                howItWorks = "Radix Sort sorts numbers by individual digits, starting from the least significant digit (LSD) and moving to the most significant digit.",
                keyIdea = "Apply a stable sort to each digit so the ordering of previous digits is preserved.",
                steps = listOf(
                    "Find the maximum number to know the number of digits",
                    "For each digit from LSD to MSD",
                    "Perform a stable counting sort based on that digit",
                    "Repeat until all digits are processed"
                )
            )
            SortType.BUCKET -> AlgorithmConcept(
                sortType = SortType.BUCKET,
                howItWorks = "Bucket Sort distributes elements into buckets based on their value range, sorts each bucket individually, and then concatenates them.",
                keyIdea = "Uniform distribution keeps buckets small, making local sorts fast.",
                steps = listOf(
                    "Find the minimum and maximum values",
                    "Choose the number of buckets",
                    "Distribute elements into buckets by range",
                    "Sort each bucket independently",
                    "Concatenate buckets in order"
                )
            )
            SortType.TIM -> AlgorithmConcept(
                sortType = SortType.TIM,
                howItWorks = "Tim Sort identifies already sorted runs, extends them with insertion sort, then merges runs efficiently.",
                keyIdea = "Exploit existing order in the data and merge runs for stability and speed.",
                steps = listOf(
                    "Scan the array to find ascending runs",
                    "Extend short runs with insertion sort",
                    "Merge runs while maintaining stability",
                    "Repeat merges until one run remains"
                )
            )
            SortType.COMB -> AlgorithmConcept(
                sortType = SortType.COMB,
                howItWorks = "Comb Sort compares elements with a shrinking gap, reducing turtles (small elements near the end).",
                keyIdea = "Start with a large gap and shrink it until it becomes 1.",
                steps = listOf(
                    "Set initial gap to array size",
                    "Compare elements gap apart",
                    "Swap out-of-order pairs",
                    "Shrink the gap by a factor",
                    "Continue until gap is 1 and no swaps occur"
                )
            )
            SortType.COCKTAIL -> AlgorithmConcept(
                sortType = SortType.COCKTAIL,
                howItWorks = "Cocktail Sort sweeps left-to-right and right-to-left in alternating passes.",
                keyIdea = "Move large values to the end and small values to the start each cycle.",
                steps = listOf(
                    "Forward pass: swap adjacent out-of-order pairs",
                    "Backward pass: swap adjacent out-of-order pairs",
                    "Shrink the unsorted range",
                    "Repeat until no swaps occur"
                )
            )
            SortType.GNOME -> AlgorithmConcept(
                sortType = SortType.GNOME,
                howItWorks = "Gnome Sort moves forward when in order and steps back when it finds an inversion.",
                keyIdea = "Fix local inversions by swapping and stepping back.",
                steps = listOf(
                    "Start at index 0",
                    "If elements are in order, move forward",
                    "If out of order, swap and step back",
                    "Continue until reaching the end"
                )
            )
            SortType.ODD_EVEN -> AlgorithmConcept(
                sortType = SortType.ODD_EVEN,
                howItWorks = "Odd-Even Sort alternates between comparing odd/even indexed pairs.",
                keyIdea = "Two-phase passes mimic parallel bubble sort.",
                steps = listOf(
                    "Odd phase: compare (1,2), (3,4), ...",
                    "Even phase: compare (0,1), (2,3), ...",
                    "Repeat until no swaps occur"
                )
            )
            SortType.BOGO -> AlgorithmConcept(
                sortType = SortType.BOGO,
                howItWorks = "Bogo Sort keeps shuffling the array until it becomes sorted.",
                keyIdea = "Randomness eventually yields a sorted order.",
                steps = listOf(
                    "Check if the array is sorted",
                    "If not sorted, shuffle randomly",
                    "Repeat until sorted"
                )
            )
            SortType.BITONIC -> AlgorithmConcept(
                sortType = SortType.BITONIC,
                howItWorks = "Bitonic Sort builds bitonic sequences and merges them into sorted order.",
                keyIdea = "Recursive compare-and-swap operations create sorted sequences.",
                steps = listOf(
                    "Split into two halves",
                    "Sort one half ascending and the other descending",
                    "Merge the bitonic sequence",
                    "Repeat recursively"
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
            SortType.COUNTING -> AlgorithmComplexity(
                sortType = SortType.COUNTING,
                timeComplexityBest = "O(n + k)",
                timeComplexityAverage = "O(n + k)",
                timeComplexityWorst = "O(n + k)",
                spaceComplexity = "O(k)",
                timeComplexityExplanation = "Counting occurrences is O(n). Rebuilding the array by scanning the counting array is O(k), where k is the value range.",
                spaceComplexityExplanation = "Requires an auxiliary counting array of size k.",
                intuition = "Instead of comparing elements, we count how many of each value exists, then output them in order."
            )
            SortType.RADIX -> AlgorithmComplexity(
                sortType = SortType.RADIX,
                timeComplexityBest = "O(nk)",
                timeComplexityAverage = "O(nk)",
                timeComplexityWorst = "O(nk)",
                spaceComplexity = "O(n + k)",
                timeComplexityExplanation = "Each digit pass is O(n + k) with counting sort, repeated for k digits.",
                spaceComplexityExplanation = "Requires output and counting arrays for each digit pass.",
                intuition = "Sorting digit by digit avoids comparisons while maintaining order through stable passes."
            )
            SortType.BUCKET -> AlgorithmComplexity(
                sortType = SortType.BUCKET,
                timeComplexityBest = "O(n + k)",
                timeComplexityAverage = "O(n + k)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(n + k)",
                timeComplexityExplanation = "Distribution is O(n). If buckets are balanced, sorting each bucket is fast; worst case occurs when all elements fall into one bucket.",
                spaceComplexityExplanation = "Requires additional buckets to store elements and intermediate results.",
                intuition = "Grouping values reduces the sorting work per bucket when the distribution is uniform."
            )
            SortType.TIM -> AlgorithmComplexity(
                sortType = SortType.TIM,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n log n)",
                timeComplexityWorst = "O(n log n)",
                spaceComplexity = "O(n)",
                timeComplexityExplanation = "Runs reduce work for nearly sorted data. Merging runs dominates to O(n log n) for random data.",
                spaceComplexityExplanation = "Requires auxiliary storage for merging runs.",
                intuition = "Tim Sort is fast because it takes advantage of already ordered segments and merges them efficiently."
            )
            SortType.COMB -> AlgorithmComplexity(
                sortType = SortType.COMB,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "O(n²/2^p)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Large gaps reduce inversions quickly; worst case still quadratic when gap becomes 1.",
                spaceComplexityExplanation = "In-place with constant extra memory.",
                intuition = "Shrinking the gap helps move small values forward faster than bubble sort."
            )
            SortType.COCKTAIL -> AlgorithmComplexity(
                sortType = SortType.COCKTAIL,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Each pass scans the array; reverse pass helps but still quadratic.",
                spaceComplexityExplanation = "In-place with constant extra memory.",
                intuition = "Bidirectional passes move both large and small items toward their ends."
            )
            SortType.GNOME -> AlgorithmComplexity(
                sortType = SortType.GNOME,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Swapping backward for each inversion yields quadratic behavior in worst cases.",
                spaceComplexityExplanation = "In-place and uses constant extra space.",
                intuition = "Like insertion sort with swaps instead of shifts."
            )
            SortType.ODD_EVEN -> AlgorithmComplexity(
                sortType = SortType.ODD_EVEN,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Alternating passes behave like bubble sort; parallelism is the main benefit.",
                spaceComplexityExplanation = "In-place with constant extra memory.",
                intuition = "Sorting progresses by alternating odd and even index comparisons."
            )
            SortType.BOGO -> AlgorithmComplexity(
                sortType = SortType.BOGO,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O((n+1)!)",
                timeComplexityWorst = "Unbounded",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Random shuffles may take factorial time on average.",
                spaceComplexityExplanation = "Uses only constant extra space.",
                intuition = "Randomness makes it extremely inefficient for real use."
            )
            SortType.BITONIC -> AlgorithmComplexity(
                sortType = SortType.BITONIC,
                timeComplexityBest = "O(n log² n)",
                timeComplexityAverage = "O(n log² n)",
                timeComplexityWorst = "O(n log² n)",
                spaceComplexity = "O(n)",
                timeComplexityExplanation = "Bitonic merges require log² n compare-swap layers.",
                spaceComplexityExplanation = "This implementation pads to power-of-two, using extra space.",
                intuition = "Well-suited for parallel hardware due to regular compare-swap structure."
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
            SortType.COUNTING -> AlgorithmUseCase(
                sortType = SortType.COUNTING,
                bestUseCases = listOf(
                    "Integers with a small, known range",
                    "Histogram or frequency-based tasks",
                    "As a stable subroutine in Radix Sort"
                ),
                notRecommended = listOf(
                    "Large value ranges (k is huge)",
                    "Memory-constrained environments",
                    "When values are not integers"
                ),
                realWorldExamples = listOf(
                    "Counting grades or scores",
                    "Bucket-based preprocessing",
                    "Radix Sort implementations"
                )
            )
            SortType.RADIX -> AlgorithmUseCase(
                sortType = SortType.RADIX,
                bestUseCases = listOf(
                    "Fixed-length integers (IDs, timestamps)",
                    "Large datasets with uniform key length",
                    "Stable sorting requirements"
                ),
                notRecommended = listOf(
                    "Variable-length or non-integer keys",
                    "Very small datasets",
                    "When memory is extremely constrained"
                ),
                realWorldExamples = listOf(
                    "Sorting large numeric IDs",
                    "Database indexing pipelines",
                    "String sorting with fixed-length keys"
                )
            )
            SortType.BUCKET -> AlgorithmUseCase(
                sortType = SortType.BUCKET,
                bestUseCases = listOf(
                    "Uniformly distributed values",
                    "Known numeric ranges",
                    "Floating point values in a bounded interval"
                ),
                notRecommended = listOf(
                    "Highly skewed distributions",
                    "Very small datasets",
                    "Memory-constrained environments"
                ),
                realWorldExamples = listOf(
                    "Histogram bucketing",
                    "Range-based preprocessing",
                    "Sorting scores by ranges"
                )
            )
            SortType.TIM -> AlgorithmUseCase(
                sortType = SortType.TIM,
                bestUseCases = listOf(
                    "Real-world datasets with existing order",
                    "Stable sorting requirements",
                    "General-purpose list sorting"
                ),
                notRecommended = listOf(
                    "Very memory-constrained environments",
                    "When a simpler algorithm is sufficient"
                ),
                realWorldExamples = listOf(
                    "Python list sort",
                    "Java's Arrays.sort for objects (TimSort variant)",
                    "Android/Kotlin standard library sorting"
                )
            )
            SortType.COMB -> AlgorithmUseCase(
                sortType = SortType.COMB,
                bestUseCases = listOf(
                    "Medium-sized arrays",
                    "Educational comparison with Bubble Sort",
                    "When simple improvements are needed"
                ),
                notRecommended = listOf(
                    "Large datasets",
                    "Stability requirements"
                ),
                realWorldExamples = listOf(
                    "Teaching gap-based improvements",
                    "Legacy utility implementations"
                )
            )
            SortType.COCKTAIL -> AlgorithmUseCase(
                sortType = SortType.COCKTAIL,
                bestUseCases = listOf(
                    "Nearly sorted data",
                    "Small datasets",
                    "Educational visualizations"
                ),
                notRecommended = listOf(
                    "Large datasets",
                    "Performance-critical systems"
                ),
                realWorldExamples = listOf(
                    "Teaching bidirectional bubble sort",
                    "Simple visualization demos"
                )
            )
            SortType.GNOME -> AlgorithmUseCase(
                sortType = SortType.GNOME,
                bestUseCases = listOf(
                    "Small arrays",
                    "Educational purposes",
                    "When simplicity matters"
                ),
                notRecommended = listOf(
                    "Large datasets",
                    "Performance-sensitive workloads"
                ),
                realWorldExamples = listOf(
                    "Introductory algorithm classes",
                    "Step-by-step visualization"
                )
            )
            SortType.ODD_EVEN -> AlgorithmUseCase(
                sortType = SortType.ODD_EVEN,
                bestUseCases = listOf(
                    "Parallel or GPU-friendly environments",
                    "Educational demonstrations",
                    "Small arrays"
                ),
                notRecommended = listOf(
                    "Large sequential workloads",
                    "When stability is required"
                ),
                realWorldExamples = listOf(
                    "Sorting networks",
                    "Parallel sorting demos"
                )
            )
            SortType.BOGO -> AlgorithmUseCase(
                sortType = SortType.BOGO,
                bestUseCases = listOf(
                    "Educational humor",
                    "Very small arrays"
                ),
                notRecommended = listOf(
                    "Any practical sorting task",
                    "Large datasets"
                ),
                realWorldExamples = listOf(
                    "Algorithm jokes",
                    "Teaching randomized algorithms"
                )
            )
            SortType.BITONIC -> AlgorithmUseCase(
                sortType = SortType.BITONIC,
                bestUseCases = listOf(
                    "Parallel hardware",
                    "Sorting networks",
                    "Fixed-size data batches"
                ),
                notRecommended = listOf(
                    "Memory-constrained environments",
                    "Small arrays on CPU"
                ),
                realWorldExamples = listOf(
                    "GPU sorting kernels",
                    "Hardware sorting networks"
                )
            )
        }
    }

    fun getImplementation(sortType: SortType): AlgorithmImplementation {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmImplementation(
                sortType = SortType.BUBBLE,
                code = """
fun bubbleSort(arr: IntArray) {
    val n = arr.size
    for (i in 0 until n - 1) {
        var swapped = false
        for (j in 0 until n - i - 1) {
            if (arr[j] > arr[j + 1]) {
                // Swap arr[j] and arr[j+1]
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
                swapped = true
            }
        }
        // If no two elements were swapped by inner loop, then break
        if (!swapped) break
    }
}
                """.trimIndent(),
                description = "Standard optimized Bubble Sort with early exit flag."
            )
            SortType.SELECTION -> AlgorithmImplementation(
                sortType = SortType.SELECTION,
                code = """
fun selectionSort(arr: IntArray) {
    val n = arr.size
    for (i in 0 until n - 1) {
        var minIdx = i
        for (j in i + 1 until n) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j
            }
        }
        // Swap the found minimum element with the first element
        val temp = arr[minIdx]
        arr[minIdx] = arr[i]
        arr[i] = temp
    }
}
                """.trimIndent(),
                description = "Basic Selection Sort implementation."
            )
            SortType.INSERTION -> AlgorithmImplementation(
                sortType = SortType.INSERTION,
                code = """
fun insertionSort(arr: IntArray) {
    val n = arr.size
    for (i in 1 until n) {
        val key = arr[i]
        var j = i - 1

        // Move elements of arr[0..i-1], that are greater than key,
        // to one position ahead of their current position
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j = j - 1
        }
        arr[j + 1] = key
    }
}
                """.trimIndent(),
                description = "Standard Insertion Sort."
            )
            SortType.SHELL -> AlgorithmImplementation(
                sortType = SortType.SHELL,
                code = """
fun shellSort(arr: IntArray) {
    val n = arr.size
    // Start with a big gap, then reduce the gap
    var gap = n / 2
    while (gap > 0) {
        for (i in gap until n) {
            val temp = arr[i]
            var j = i
            while (j >= gap && arr[j - gap] > temp) {
                arr[j] = arr[j - gap]
                j -= gap
            }
            arr[j] = temp
        }
        gap /= 2
    }
}
                """.trimIndent(),
                description = "Shell Sort using the original shell sequence (n/2, n/4, ...)."
            )
            SortType.MERGE -> AlgorithmImplementation(
                sortType = SortType.MERGE,
                code = """
fun mergeSort(arr: IntArray) {
    if (arr.size < 2) return
    val mid = arr.size / 2
    val left = arr.copyOfRange(0, mid)
    val right = arr.copyOfRange(mid, arr.size)

    mergeSort(left)
    mergeSort(right)

    merge(arr, left, right)
}

fun merge(arr: IntArray, left: IntArray, right: IntArray) {
    var i = 0; var j = 0; var k = 0
    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            arr[k++] = left[i++]
        } else {
            arr[k++] = right[j++]
        }
    }
    while (i < left.size) arr[k++] = left[i++]
    while (j < right.size) arr[k++] = right[j++]
}
                """.trimIndent(),
                description = "Recursive Merge Sort implementation."
            )
            SortType.QUICK -> AlgorithmImplementation(
                sortType = SortType.QUICK,
                code = """
fun quickSort(arr: IntArray, low: Int, high: Int) {
    if (low < high) {
        val pi = partition(arr, low, high)
        quickSort(arr, low, pi - 1)
        quickSort(arr, pi + 1, high)
    }
}

fun partition(arr: IntArray, low: Int, high: Int): Int {
    val pivot = arr[high]
    var i = (low - 1)
    for (j in low until high) {
        if (arr[j] < pivot) {
            i++
            val temp = arr[i]
            arr[i] = arr[j]
            arr[j] = temp
        }
    }
    val temp = arr[i + 1]
    arr[i + 1] = arr[high]
    arr[high] = temp
    return i + 1
}
                """.trimIndent(),
                description = "Standard Quick Sort using Lomuto partition scheme."
            )
            SortType.HEAP -> AlgorithmImplementation(
                sortType = SortType.HEAP,
                code = """
fun heapSort(arr: IntArray) {
    val n = arr.size
    // Build heap (rearrange array)
    for (i in n / 2 - 1 downTo 0)
        heapify(arr, n, i)

    // One by one extract an element from heap
    for (i in n - 1 downTo 0) {
        // Move current root to end
        val temp = arr[0]
        arr[0] = arr[i]
        arr[i] = temp

        // call max heapify on the reduced heap
        heapify(arr, i, 0)
    }
}

fun heapify(arr: IntArray, n: Int, i: Int) {
    var largest = i
    val l = 2 * i + 1
    val r = 2 * i + 2

    if (l < n && arr[l] > arr[largest])
        largest = l

    if (r < n && arr[r] > arr[largest])
        largest = r

    if (largest != i) {
        val swap = arr[i]
        arr[i] = arr[largest]
        arr[largest] = swap

        heapify(arr, n, largest)
    }
}
                """.trimIndent(),
                description = "Heap Sort implementation."
            )
            SortType.COUNTING -> AlgorithmImplementation(
                sortType = SortType.COUNTING,
                code = """
fun countingSort(arr: IntArray) {
    if (arr.isEmpty()) return
    val min = arr.minOrNull() ?: return
    val max = arr.maxOrNull() ?: return
    val range = max - min + 1
    val count = IntArray(range)

    // Count each value
    for (v in arr) {
        count[v - min]++
    }

    // Rebuild array in order
    var index = 0
    for (i in count.indices) {
        val value = i + min
        repeat(count[i]) {
            arr[index++] = value
        }
    }
}
                """.trimIndent(),
                description = "Counting Sort using a frequency array (handles negative values via offset)."
            )
            SortType.RADIX -> AlgorithmImplementation(
                sortType = SortType.RADIX,
                code = """
fun radixSort(arr: IntArray) {
    if (arr.isEmpty()) return
    val max = arr.maxOrNull() ?: return
    var exp = 1
    while (max / exp > 0) {
        countingSortByDigit(arr, exp)
        exp *= 10
    }
}

fun countingSortByDigit(arr: IntArray, exp: Int) {
    val output = IntArray(arr.size)
    val count = IntArray(10)

    for (v in arr) {
        val digit = (v / exp) % 10
        count[digit]++
    }

    for (i in 1 until 10) count[i] += count[i - 1]

    for (i in arr.size - 1 downTo 0) {
        val v = arr[i]
        val digit = (v / exp) % 10
        output[count[digit] - 1] = v
        count[digit]--
    }

    for (i in arr.indices) arr[i] = output[i]
}
                """.trimIndent(),
                description = "Radix Sort (LSD) with stable counting sort for each digit."
            )
            SortType.BUCKET -> AlgorithmImplementation(
                sortType = SortType.BUCKET,
                code = """
fun bucketSort(arr: IntArray) {
    if (arr.isEmpty()) return
    val min = arr.minOrNull() ?: return
    val max = arr.maxOrNull() ?: return
    if (min == max) return

    val range = max - min + 1
    val bucketCount = kotlin.math.sqrt(arr.size.toDouble()).toInt().coerceAtLeast(1)
    val buckets = List(bucketCount) { mutableListOf<Int>() }

    for (v in arr) {
        val index = ((v - min) * bucketCount) / range
        buckets[index].add(v)
    }

    var idx = 0
    for (bucket in buckets) {
        bucket.sort()
        for (v in bucket) {
            arr[idx++] = v
        }
    }
}
                """.trimIndent(),
                description = "Bucket Sort using range-based buckets and per-bucket sort."
            )
            SortType.TIM -> AlgorithmImplementation(
                sortType = SortType.TIM,
                code = """
fun timSort(arr: IntArray) {
    val n = arr.size
    val minRun = 32

    var start = 0
    while (start < n) {
        val end = kotlin.math.min(start + minRun - 1, n - 1)
        insertionSortRange(arr, start, end)
        start += minRun
    }

    var size = minRun
    while (size < n) {
        var left = 0
        while (left < n) {
            val mid = left + size - 1
            val right = kotlin.math.min(left + 2 * size - 1, n - 1)
            if (mid < right) merge(arr, left, mid, right)
            left += 2 * size
        }
        size *= 2
    }
}
                """.trimIndent(),
                description = "Simplified Tim Sort using fixed-size runs and merge passes."
            )
            SortType.COMB -> AlgorithmImplementation(
                sortType = SortType.COMB,
                code = """
fun combSort(arr: IntArray) {
    val shrink = 1.3
    var gap = arr.size
    var swapped = true

    while (gap > 1 || swapped) {
        gap = (gap / shrink).toInt().coerceAtLeast(1)
        swapped = false
        for (i in 0 until arr.size - gap) {
            val j = i + gap
            if (arr[i] > arr[j]) {
                val tmp = arr[i]
                arr[i] = arr[j]
                arr[j] = tmp
                swapped = true
            }
        }
    }
}
                """.trimIndent(),
                description = "Comb Sort shrinks the gap and swaps out-of-order pairs."
            )
            SortType.COCKTAIL -> AlgorithmImplementation(
                sortType = SortType.COCKTAIL,
                code = """
fun cocktailSort(arr: IntArray) {
    var start = 0
    var end = arr.size - 1
    var swapped = true

    while (swapped) {
        swapped = false
        for (i in start until end) {
            if (arr[i] > arr[i + 1]) {
                val tmp = arr[i]
                arr[i] = arr[i + 1]
                arr[i + 1] = tmp
                swapped = true
            }
        }
        if (!swapped) break
        swapped = false
        end--
        for (i in end downTo start + 1) {
            if (arr[i - 1] > arr[i]) {
                val tmp = arr[i - 1]
                arr[i - 1] = arr[i]
                arr[i] = tmp
                swapped = true
            }
        }
        start++
    }
}
                """.trimIndent(),
                description = "Cocktail Sort performs forward and backward bubble passes."
            )
            SortType.GNOME -> AlgorithmImplementation(
                sortType = SortType.GNOME,
                code = """
fun gnomeSort(arr: IntArray) {
    var index = 0
    while (index < arr.size) {
        if (index == 0 || arr[index - 1] <= arr[index]) {
            index++
        } else {
            val tmp = arr[index]
            arr[index] = arr[index - 1]
            arr[index - 1] = tmp
            index--
        }
    }
}
                """.trimIndent(),
                description = "Gnome Sort steps backward on inversions and swaps adjacent items."
            )
            SortType.ODD_EVEN -> AlgorithmImplementation(
                sortType = SortType.ODD_EVEN,
                code = """
fun oddEvenSort(arr: IntArray) {
    var sorted = false
    while (!sorted) {
        sorted = true
        for (i in 1 until arr.size - 1 step 2) {
            if (arr[i] > arr[i + 1]) {
                val tmp = arr[i]
                arr[i] = arr[i + 1]
                arr[i + 1] = tmp
                sorted = false
            }
        }
        for (i in 0 until arr.size - 1 step 2) {
            if (arr[i] > arr[i + 1]) {
                val tmp = arr[i]
                arr[i] = arr[i + 1]
                arr[i + 1] = tmp
                sorted = false
            }
        }
    }
}
                """.trimIndent(),
                description = "Odd-Even Sort alternates odd and even indexed passes."
            )
            SortType.BOGO -> AlgorithmImplementation(
                sortType = SortType.BOGO,
                code = """
fun bogoSort(arr: IntArray) {
    while (!isSorted(arr)) {
        arr.shuffle()
    }
}
                """.trimIndent(),
                description = "Bogo Sort shuffles randomly until the array is sorted."
            )
            SortType.BITONIC -> AlgorithmImplementation(
                sortType = SortType.BITONIC,
                code = """
fun bitonicSort(arr: IntArray) {
    // Works best with power-of-two sizes
    bitonicSortRecursive(arr, 0, arr.size, true)
}
                """.trimIndent(),
                description = "Bitonic Sort builds bitonic sequences and merges them."
            )
        }
    }

    fun getExample(sortType: SortType): AlgorithmExample {
        val initialArray = listOf(5, 3, 1, 4, 2)
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmExample(
                sortType = SortType.BUBBLE,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(3, 5, 1, 4, 2), "Compare 5 and 3. 5 > 3, so Swap.", listOf(0, 1), StepModificationType.SWAP),
                    AlgorithmExampleStep(2, listOf(3, 1, 5, 4, 2), "Compare 5 and 1. 5 > 1, so Swap.", listOf(1, 2), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(3, 1, 4, 5, 2), "Compare 5 and 4. 5 > 4, so Swap.", listOf(2, 3), StepModificationType.SWAP),
                    AlgorithmExampleStep(4, listOf(3, 1, 4, 2, 5), "Compare 5 and 2. 5 > 2, so Swap. 5 reaches end.", listOf(3, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(5, listOf(3, 1, 4, 2, 5), "Pass 2: Compare 3 and 1. Swap.", listOf(0, 1), StepModificationType.SWAP),
                    AlgorithmExampleStep(6, listOf(1, 3, 4, 2, 5), "Pass 2: Compare 3 and 4. No swap.", listOf(1, 2), StepModificationType.COMPARE),
                    AlgorithmExampleStep(7, listOf(1, 3, 2, 4, 5), "Pass 2: Compare 4 and 2. Swap.", listOf(2, 3), StepModificationType.SWAP),
                    AlgorithmExampleStep(8, listOf(1, 3, 2, 4, 5), "Pass 3: Compare 1 and 3. No swap.", listOf(0, 1), StepModificationType.COMPARE),
                    AlgorithmExampleStep(9, listOf(1, 2, 3, 4, 5), "Pass 3: Compare 3 and 2. Swap.", listOf(1, 2), StepModificationType.SWAP),
                    AlgorithmExampleStep(10, listOf(1, 2, 3, 4, 5), "Sorted!", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.SELECTION -> AlgorithmExample(
                sortType = SortType.SELECTION,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Start. Min is 5 at index 0.", listOf(0), StepModificationType.COMPARE),
                    AlgorithmExampleStep(2, listOf(5, 3, 1, 4, 2), "Found smaller: 1 at index 2.", listOf(2), StepModificationType.COMPARE),
                    AlgorithmExampleStep(3, listOf(1, 3, 5, 4, 2), "Swap min (1) with first element (5).", listOf(0, 2), StepModificationType.SWAP),
                    AlgorithmExampleStep(4, listOf(1, 3, 5, 4, 2), "Next pass. Min is 3 at index 1.", listOf(1), StepModificationType.COMPARE),
                    AlgorithmExampleStep(5, listOf(1, 3, 5, 4, 2), "Found smaller: 2 at index 4.", listOf(4), StepModificationType.COMPARE),
                    AlgorithmExampleStep(6, listOf(1, 2, 5, 4, 3), "Swap min (2) with index 1 (3).", listOf(1, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(7, listOf(1, 2, 3, 5, 4), "Next pass. 3 is smallest in remaining. Swap 3 and 5.", listOf(2, 2), StepModificationType.SET),
                    AlgorithmExampleStep(8, listOf(1, 2, 3, 4, 5), "Last pass. Swap 4 and 5.", listOf(3, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(9, listOf(1, 2, 3, 4, 5), "Sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.INSERTION -> AlgorithmExample(
                sortType = SortType.INSERTION,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Take 3. 5 > 3, shift 5.", listOf(0, 1), StepModificationType.COMPARE),
                    AlgorithmExampleStep(2, listOf(3, 5, 1, 4, 2), "Insert 3.", listOf(0), StepModificationType.SET),
                    AlgorithmExampleStep(3, listOf(3, 5, 1, 4, 2), "Take 1. 5 > 1, shift 5.", listOf(2), StepModificationType.COMPARE),
                    AlgorithmExampleStep(4, listOf(3, 5, 5, 4, 2), "3 > 1, shift 3.", listOf(1), StepModificationType.COMPARE),
                    AlgorithmExampleStep(5, listOf(1, 3, 5, 4, 2), "Insert 1.", listOf(0), StepModificationType.SET),
                    AlgorithmExampleStep(6, listOf(1, 3, 5, 4, 2), "Take 4. 5 > 4, shift 5.", listOf(2), StepModificationType.COMPARE),
                    AlgorithmExampleStep(7, listOf(1, 3, 4, 5, 2), "3 < 4, insert 4.", listOf(2), StepModificationType.SET),
                    AlgorithmExampleStep(8, listOf(1, 3, 4, 5, 2), "Take 2. Shift 5, 4, 3.", listOf(4), StepModificationType.COMPARE),
                    AlgorithmExampleStep(9, listOf(1, 2, 3, 4, 5), "Insert 2.", listOf(1), StepModificationType.SET),
                    AlgorithmExampleStep(10, listOf(1, 2, 3, 4, 5), "Sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.SHELL -> AlgorithmExample(
                sortType = SortType.SHELL,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Gap = 2. Compare 5 and 1.", listOf(0, 2), StepModificationType.COMPARE),
                    AlgorithmExampleStep(2, listOf(1, 3, 5, 4, 2), "Swap 5 and 1.", listOf(0, 2), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(1, 3, 5, 4, 2), "Gap = 2. Compare 3 and 4. No swap.", listOf(1, 3), StepModificationType.COMPARE),
                    AlgorithmExampleStep(4, listOf(1, 3, 5, 4, 2), "Gap = 2. Compare 5 and 2.", listOf(2, 4), StepModificationType.COMPARE),
                    AlgorithmExampleStep(5, listOf(1, 3, 2, 4, 5), "Swap 5 and 2.", listOf(2, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(6, listOf(1, 3, 2, 4, 5), "Gap = 1 (Insertion Sort).", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(7, listOf(1, 2, 3, 4, 5), "Insertion Sort pass completes. Sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.MERGE -> AlgorithmExample(
                sortType = SortType.MERGE,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Split: [5, 3] and [1, 4, 2]", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(3, 5, 1, 4, 2), "Sort left: [3, 5]", listOf(0, 1), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(3, 5, 1, 2, 4), "Sort right: [1, 2, 4]", listOf(2, 3, 4), StepModificationType.SET),
                    AlgorithmExampleStep(4, listOf(1, 2, 3, 4, 5), "Merge [3, 5] and [1, 2, 4].", emptyList(), StepModificationType.SET)
                )
            )
            SortType.QUICK -> AlgorithmExample(
                sortType = SortType.QUICK,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Pivot = 2 (last).", listOf(4), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(1, 3, 5, 4, 2), "Partition: 1 < 2, move to front.", listOf(2), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(1, 2, 5, 4, 3), "Place pivot 2 after 1.", listOf(1, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(4, listOf(1, 2, 5, 4, 3), "Recursively sort [5, 4, 3]. Pivot = 3.", listOf(4), StepModificationType.NONE),
                    AlgorithmExampleStep(5, listOf(1, 2, 3, 4, 5), "Sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.HEAP -> AlgorithmExample(
                sortType = SortType.HEAP,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 4, 2, 3, 1), "Build Max Heap.", emptyList(), StepModificationType.SET),
                    AlgorithmExampleStep(2, listOf(1, 4, 2, 3, 5), "Swap Max(5) with Last(1). Heap size reduces.", listOf(0, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(4, 3, 2, 1, 5), "Heapify root.", emptyList(), StepModificationType.SET),
                    AlgorithmExampleStep(4, listOf(1, 3, 2, 4, 5), "Swap Max(4) with Last(1).", listOf(0, 3), StepModificationType.SWAP),
                    AlgorithmExampleStep(5, listOf(1, 2, 3, 4, 5), "Continue until sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.COUNTING -> AlgorithmExample(
                sortType = SortType.COUNTING,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Count frequencies for each value.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(1, 3, 1, 4, 2), "Place 1 at index 0.", listOf(0), StepModificationType.SET),
                    AlgorithmExampleStep(3, listOf(1, 2, 1, 4, 2), "Place 2 at index 1.", listOf(1), StepModificationType.SET),
                    AlgorithmExampleStep(4, listOf(1, 2, 3, 4, 2), "Place 3 at index 2.", listOf(2), StepModificationType.SET),
                    AlgorithmExampleStep(5, listOf(1, 2, 3, 4, 2), "Place 4 at index 3.", listOf(3), StepModificationType.SET),
                    AlgorithmExampleStep(6, listOf(1, 2, 3, 4, 5), "Place 5 at index 4. Sorted.", listOf(4), StepModificationType.SET)
                )
            )
            SortType.RADIX -> AlgorithmExample(
                sortType = SortType.RADIX,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Sort by ones digit (LSD).", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(1, 2, 3, 4, 5), "Ones digit pass completes.", emptyList(), StepModificationType.SET),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "No more digits. Sorted.", emptyList(), StepModificationType.NONE)
                )
            )
            SortType.BUCKET -> AlgorithmExample(
                sortType = SortType.BUCKET,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Distribute elements into buckets.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(5, 3, 1, 4, 2), "Sort each bucket individually.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Concatenate buckets. Sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.TIM -> AlgorithmExample(
                sortType = SortType.TIM,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Detect runs and sort small runs with insertion sort.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(1, 3, 5, 2, 4), "Merge adjacent runs.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Final merge completes. Sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.COMB -> AlgorithmExample(
                sortType = SortType.COMB,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Gap = 3, compare 5 and 4.", listOf(0, 3), StepModificationType.COMPARE),
                    AlgorithmExampleStep(2, listOf(4, 3, 1, 5, 2), "Swap 5 and 4.", listOf(0, 3), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Gap shrinks to 1. Finish with bubble pass.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.COCKTAIL -> AlgorithmExample(
                sortType = SortType.COCKTAIL,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(3, 1, 4, 2, 5), "Forward pass moves 5 to the end.", listOf(3, 4), StepModificationType.SWAP),
                    AlgorithmExampleStep(2, listOf(1, 3, 2, 4, 5), "Backward pass moves 1 to the start.", listOf(0, 1), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Continue until sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.GNOME -> AlgorithmExample(
                sortType = SortType.GNOME,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Swap 5 and 3, step back.", listOf(0, 1), StepModificationType.SWAP),
                    AlgorithmExampleStep(2, listOf(3, 5, 1, 4, 2), "Swap 5 and 1, step back.", listOf(1, 2), StepModificationType.SWAP),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Repeat until sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.ODD_EVEN -> AlgorithmExample(
                sortType = SortType.ODD_EVEN,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Odd phase compares (1,2) and (3,4).", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(3, 5, 1, 2, 4), "Even phase compares (0,1) and (2,3).", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Repeat phases until sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.BOGO -> AlgorithmExample(
                sortType = SortType.BOGO,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Shuffle the array randomly.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(3, 2, 5, 1, 4), "Shuffle again...", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Eventually becomes sorted.", emptyList(), StepModificationType.SET)
                )
            )
            SortType.BITONIC -> AlgorithmExample(
                sortType = SortType.BITONIC,
                initialArray = initialArray,
                steps = listOf(
                    AlgorithmExampleStep(1, listOf(5, 3, 1, 4, 2), "Form bitonic sequences.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(2, listOf(3, 5, 1, 2, 4), "Bitonic merge with compare-and-swap.", emptyList(), StepModificationType.NONE),
                    AlgorithmExampleStep(3, listOf(1, 2, 3, 4, 5), "Final merge completes. Sorted.", emptyList(), StepModificationType.SET)
                )
            )
        }
    }
}
