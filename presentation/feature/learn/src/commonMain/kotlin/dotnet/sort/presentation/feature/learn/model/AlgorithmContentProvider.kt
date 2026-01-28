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
            SortType.COUNTING -> AlgorithmHistory(
                sortType = SortType.COUNTING,
                originYear = "1954",
                inventor = "Harold H. Seward",
                description = "Developed by Harold H. Seward in 1954. It is a non-comparison sort that works by counting occurrences of each element, making it extremely efficient for datasets with a limited range of values."
            )
            SortType.RADIX -> AlgorithmHistory(
                sortType = SortType.RADIX,
                originYear = "1887",
                inventor = "Herman Hollerith",
                description = "The concept dates back to Herman Hollerith's work with punch cards in 1887. Modern radix sort processes numbers digit by digit, often using counting sort as a subroutine."
            )
            SortType.BUCKET -> AlgorithmHistory(
                sortType = SortType.BUCKET,
                originYear = "1956",
                inventor = "W. W. Peterson",
                description = "Introduced by W. W. Peterson in 1956. It distributes elements into buckets based on their values, sorts each bucket individually, and then concatenates the results."
            )
            SortType.TIM -> AlgorithmHistory(
                sortType = SortType.TIM,
                originYear = "2002",
                inventor = "Tim Peters",
                description = "Created by Tim Peters in 2002 for Python's standard library. It is a hybrid stable sorting algorithm derived from merge sort and insertion sort, optimized for real-world data patterns."
            )
            SortType.COMB -> AlgorithmHistory(
                sortType = SortType.COMB,
                originYear = "1980",
                inventor = "Włodzimierz Dobosiewicz",
                description = "Developed by Włodzimierz Dobosiewicz in 1980, and later rediscovered by Stephen Lacey and Richard Box in 1991. It improves on bubble sort by using a shrinking gap to eliminate small values at the end of the list."
            )
            SortType.COCKTAIL -> AlgorithmHistory(
                sortType = SortType.COCKTAIL,
                originYear = "Unknown",
                inventor = "Unknown",
                description = "Also known as bidirectional bubble sort or shaker sort. It improves on bubble sort by sorting in both directions on each pass through the list, which can be more efficient for certain data patterns."
            )
            SortType.GNOME -> AlgorithmHistory(
                sortType = SortType.GNOME,
                originYear = "2000",
                inventor = "Hamid Sarbazi-Azad",
                description = "Named by Dick Grune in 2000, originally called 'Stupid Sort' by Hamid Sarbazi-Azad. It is similar to insertion sort but moves elements by a series of swaps like bubble sort."
            )
            SortType.ODD_EVEN -> AlgorithmHistory(
                sortType = SortType.ODD_EVEN,
                originYear = "1972",
                inventor = "Nico Habermann",
                description = "Also known as brick sort, developed by Nico Habermann in 1972. It alternates between comparing odd/even indexed pairs and even/odd indexed pairs, making it suitable for parallel processing."
            )
            SortType.BOGO -> AlgorithmHistory(
                sortType = SortType.BOGO,
                originYear = "Unknown",
                inventor = "Unknown",
                description = "A highly inefficient sorting algorithm based on the generate-and-test paradigm. It randomly shuffles the list until it happens to be sorted. Also known as permutation sort or stupid sort."
            )
            SortType.BITONIC -> AlgorithmHistory(
                sortType = SortType.BITONIC,
                originYear = "1968",
                inventor = "Ken Batcher",
                description = "Invented by Ken Batcher in 1968. It is a parallel algorithm for sorting, designed to run efficiently on parallel hardware. It creates bitonic sequences and merges them recursively."
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
                howItWorks = "Counting Sort counts the number of objects having distinct key values, then uses arithmetic to calculate the position of each object in the output sequence.",
                keyIdea = "Count occurrences of each element and use the counts to place elements directly in their sorted positions.",
                steps = listOf(
                    "Find the range of input values (min and max)",
                    "Create a count array of size (max - min + 1)",
                    "Count occurrences of each element",
                    "Compute cumulative counts",
                    "Place elements in output array using counts",
                    "Copy the output array to the original array"
                )
            )
            SortType.RADIX -> AlgorithmConcept(
                sortType = SortType.RADIX,
                howItWorks = "Radix Sort processes the integer keys digit by digit, from least significant to most significant (LSD) or vice versa (MSD), using a stable sort like counting sort for each digit.",
                keyIdea = "Sort numbers by processing individual digits, leveraging the stability of counting sort to maintain order.",
                steps = listOf(
                    "Find the maximum number to know the number of digits",
                    "Start from the least significant digit",
                    "Apply counting sort based on the current digit",
                    "Move to the next significant digit",
                    "Repeat until all digits are processed",
                    "The array is now sorted"
                )
            )
            SortType.BUCKET -> AlgorithmConcept(
                sortType = SortType.BUCKET,
                howItWorks = "Bucket Sort distributes elements into a number of buckets, sorts each bucket individually (often with another sorting algorithm), and then concatenates the sorted buckets.",
                keyIdea = "Divide elements into buckets based on their range, sort each bucket, and combine.",
                steps = listOf(
                    "Create empty buckets (usually based on value ranges)",
                    "Distribute elements into appropriate buckets",
                    "Sort each individual bucket (e.g., using insertion sort)",
                    "Concatenate all sorted buckets in order",
                    "The result is the sorted array"
                )
            )
            SortType.TIM -> AlgorithmConcept(
                sortType = SortType.TIM,
                howItWorks = "Tim Sort divides the array into small pieces called 'runs', sorts them using insertion sort, and then merges them using a merge sort technique with optimizations.",
                keyIdea = "Combine the efficiency of insertion sort for small arrays with merge sort's divide-and-conquer approach, exploiting natural runs in the data.",
                steps = listOf(
                    "Divide the array into 'runs' (typically 32-64 elements)",
                    "Sort each run using insertion sort",
                    "Identify naturally occurring sorted subsequences",
                    "Merge runs using an optimized merge procedure",
                    "Use galloping mode for faster merging when appropriate",
                    "Continue until the entire array is merged"
                )
            )
            SortType.COMB -> AlgorithmConcept(
                sortType = SortType.COMB,
                howItWorks = "Comb Sort improves on bubble sort by using a gap sequence. It starts with a large gap and shrinks it by a factor (typically 1.3) until it reaches 1, eliminating 'turtles' early.",
                keyIdea = "Use larger gaps early to move small elements near the end quickly to the front, reducing the work for later passes.",
                steps = listOf(
                    "Initialize gap to the array length",
                    "Shrink gap by a shrink factor (commonly 1.3)",
                    "Compare and swap elements that are 'gap' apart",
                    "Continue until gap is 1 and no swaps occur",
                    "Final pass is equivalent to bubble sort"
                )
            )
            SortType.COCKTAIL -> AlgorithmConcept(
                sortType = SortType.COCKTAIL,
                howItWorks = "Cocktail Sort extends bubble sort by sorting in both directions. It alternates between bubbling the largest element to the end and the smallest to the beginning.",
                keyIdea = "Bidirectional passes help move elements more efficiently, especially 'turtles' (small values at the end).",
                steps = listOf(
                    "Start with a forward pass (like bubble sort)",
                    "Move the largest unsorted element to the end",
                    "Then do a backward pass",
                    "Move the smallest unsorted element to the beginning",
                    "Shrink the unsorted range from both ends",
                    "Repeat until no swaps are needed"
                )
            )
            SortType.GNOME -> AlgorithmConcept(
                sortType = SortType.GNOME,
                howItWorks = "Gnome Sort moves forward when elements are in order and backward when a swap is needed, similar to how a garden gnome sorts flower pots.",
                keyIdea = "Move forward through the array, swapping and stepping back when elements are out of order, until you reach the end.",
                steps = listOf(
                    "Start at position 1",
                    "If current element >= previous, move forward",
                    "If current < previous, swap them and move back",
                    "If at position 0, move forward",
                    "Continue until reaching the end of the array"
                )
            )
            SortType.ODD_EVEN -> AlgorithmConcept(
                sortType = SortType.ODD_EVEN,
                howItWorks = "Odd-Even Sort alternates between two phases: comparing/swapping odd-indexed pairs (1-2, 3-4, ...) and even-indexed pairs (0-1, 2-3, ...).",
                keyIdea = "By alternating between odd and even indexed comparisons, the algorithm can be easily parallelized.",
                steps = listOf(
                    "Perform the odd phase: compare pairs (1,2), (3,4), (5,6)...",
                    "Swap if out of order",
                    "Perform the even phase: compare pairs (0,1), (2,3), (4,5)...",
                    "Swap if out of order",
                    "Repeat until no swaps occur in a complete pass"
                )
            )
            SortType.BOGO -> AlgorithmConcept(
                sortType = SortType.BOGO,
                howItWorks = "Bogo Sort randomly shuffles the array and checks if it is sorted. If not, it shuffles again. This continues until the array happens to be sorted.",
                keyIdea = "Pure randomness - keep trying random permutations until you get lucky. Extremely inefficient!",
                steps = listOf(
                    "Check if the array is sorted",
                    "If sorted, we're done!",
                    "If not sorted, randomly shuffle all elements",
                    "Go back to step 1",
                    "Average case: O((n+1)!) shuffles needed"
                )
            )
            SortType.BITONIC -> AlgorithmConcept(
                sortType = SortType.BITONIC,
                howItWorks = "Bitonic Sort builds a bitonic sequence (first increasing, then decreasing) and then uses a bitonic merge to sort it. Works best with power-of-2 sized arrays.",
                keyIdea = "Create bitonic sequences recursively and merge them using comparator networks designed for parallel execution.",
                steps = listOf(
                    "If array size is 1, it's already bitonic",
                    "Recursively sort the first half in ascending order",
                    "Recursively sort the second half in descending order",
                    "This creates a bitonic sequence",
                    "Apply bitonic merge to sort the entire sequence",
                    "Bitonic merge compares elements at distance n/2 and recurses"
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
                timeComplexityExplanation = "Where k is the range of input values. Linear in the size of the input plus the range of values.",
                spaceComplexityExplanation = "Requires auxiliary space proportional to the range of values (k).",
                intuition = "By counting occurrences directly, we avoid comparisons entirely. Best when k is small relative to n."
            )
            SortType.RADIX -> AlgorithmComplexity(
                sortType = SortType.RADIX,
                timeComplexityBest = "O(nk)",
                timeComplexityAverage = "O(nk)",
                timeComplexityWorst = "O(nk)",
                spaceComplexity = "O(n + k)",
                timeComplexityExplanation = "Where k is the number of digits in the maximum number. Processes each digit in linear time.",
                spaceComplexityExplanation = "Needs space for the counting sort auxiliary arrays and the output array.",
                intuition = "Processing d digits with n numbers means d passes of O(n) work each. Very efficient for fixed-width integers."
            )
            SortType.BUCKET -> AlgorithmComplexity(
                sortType = SortType.BUCKET,
                timeComplexityBest = "O(n + k)",
                timeComplexityAverage = "O(n + k)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(n + k)",
                timeComplexityExplanation = "Best/average when elements are uniformly distributed. Worst case when all elements fall into one bucket.",
                spaceComplexityExplanation = "Requires space for n elements across k buckets.",
                intuition = "If elements distribute evenly, each bucket has few elements to sort. Poor distribution degrades to insertion sort's worst case."
            )
            SortType.TIM -> AlgorithmComplexity(
                sortType = SortType.TIM,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n log n)",
                timeComplexityWorst = "O(n log n)",
                spaceComplexity = "O(n)",
                timeComplexityExplanation = "Best case O(n) for already sorted data. Guaranteed O(n log n) for worst case due to merge sort properties.",
                spaceComplexityExplanation = "Requires O(n) auxiliary space for merging runs.",
                intuition = "Exploits natural runs in real data. Already sorted or nearly sorted data is handled in linear time."
            )
            SortType.COMB -> AlgorithmComplexity(
                sortType = SortType.COMB,
                timeComplexityBest = "O(n log n)",
                timeComplexityAverage = "O(n²/2^p)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Where p is the number of gap reductions. Much faster than bubble sort in practice due to early elimination of small values at the end.",
                spaceComplexityExplanation = "In-place sorting with constant extra space.",
                intuition = "Large gaps move elements long distances early. The shrink factor of 1.3 is empirically optimal."
            )
            SortType.COCKTAIL -> AlgorithmComplexity(
                sortType = SortType.COCKTAIL,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Best case when already sorted. Marginally better than bubble sort for certain data patterns.",
                spaceComplexityExplanation = "In-place sorting.",
                intuition = "Bidirectional passes help with 'turtle' elements (small values at the end) that bubble sort handles poorly."
            )
            SortType.GNOME -> AlgorithmComplexity(
                sortType = SortType.GNOME,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Linear for already sorted arrays, quadratic otherwise. Similar performance to insertion sort.",
                spaceComplexityExplanation = "In-place sorting with constant extra space.",
                intuition = "Like insertion sort but with a simpler mechanism - just swap and step back until order is restored."
            )
            SortType.ODD_EVEN -> AlgorithmComplexity(
                sortType = SortType.ODD_EVEN,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O(n²)",
                timeComplexityWorst = "O(n²)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Best case for sorted arrays. Quadratic in general, but all comparisons in each phase can be done in parallel.",
                spaceComplexityExplanation = "In-place sorting.",
                intuition = "Designed for parallel execution - all odd or all even indexed pairs can be compared simultaneously."
            )
            SortType.BOGO -> AlgorithmComplexity(
                sortType = SortType.BOGO,
                timeComplexityBest = "O(n)",
                timeComplexityAverage = "O((n+1)!)",
                timeComplexityWorst = "O(∞)",
                spaceComplexity = "O(1)",
                timeComplexityExplanation = "Best case: the array is already sorted. Average: (n+1)! shuffles. Worst case: unbounded (may never terminate).",
                spaceComplexityExplanation = "In-place shuffling.",
                intuition = "Pure chaos! With n! possible permutations, we randomly try until we get lucky. NEVER use in production."
            )
            SortType.BITONIC -> AlgorithmComplexity(
                sortType = SortType.BITONIC,
                timeComplexityBest = "O(n log² n)",
                timeComplexityAverage = "O(n log² n)",
                timeComplexityWorst = "O(n log² n)",
                spaceComplexity = "O(n log² n)",
                timeComplexityExplanation = "log n stages, each with log n comparator steps, totaling O(n log² n) comparisons. Consistent regardless of input.",
                spaceComplexityExplanation = "Parallel implementations may require additional space for communication.",
                intuition = "Built for parallelism - the comparison network structure allows massive parallel execution on GPUs or SIMD."
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
                    "When the range of values (k) is small relative to n",
                    "Sorting integers or objects with integer keys",
                    "When a stable sort is needed"
                ),
                notRecommended = listOf(
                    "Large value ranges (high memory usage)",
                    "Floating point numbers",
                    "Negative numbers (without modification)"
                ),
                realWorldExamples = listOf(
                    "Sorting exam scores (0-100 range)",
                    "Histogram computation",
                    "As a subroutine in Radix Sort"
                )
            )
            SortType.RADIX -> AlgorithmUseCase(
                sortType = SortType.RADIX,
                bestUseCases = listOf(
                    "Sorting large sets of integers with fixed number of digits",
                    "Strings of fixed length",
                    "When comparison-based sorts are too slow"
                ),
                notRecommended = listOf(
                    "Variable-length keys without padding",
                    "Floating point numbers (complex representation)",
                    "Small datasets (overhead not worthwhile)"
                ),
                realWorldExamples = listOf(
                    "Sorting phone numbers",
                    "Processing fixed-length record keys",
                    "Large-scale integer sorting in databases"
                )
            )
            SortType.BUCKET -> AlgorithmUseCase(
                sortType = SortType.BUCKET,
                bestUseCases = listOf(
                    "Uniformly distributed floating-point numbers in [0, 1)",
                    "When data is known to be evenly distributed",
                    "When additional memory is available"
                ),
                notRecommended = listOf(
                    "Skewed or clustered data distributions",
                    "Unknown data distribution",
                    "Memory-constrained environments"
                ),
                realWorldExamples = listOf(
                    "Sorting percentages or probabilities",
                    "Geographic data processing",
                    "Scientific data with known distributions"
                )
            )
            SortType.TIM -> AlgorithmUseCase(
                sortType = SortType.TIM,
                bestUseCases = listOf(
                    "General-purpose sorting (Python/Java standard)",
                    "Real-world data with natural runs",
                    "When stability is required"
                ),
                notRecommended = listOf(
                    "Memory-critical embedded systems",
                    "When you need a simple implementation"
                ),
                realWorldExamples = listOf(
                    "Python's list.sort() and sorted()",
                    "Java's Arrays.sort() for objects",
                    "Android's internal sorting"
                )
            )
            SortType.COMB -> AlgorithmUseCase(
                sortType = SortType.COMB,
                bestUseCases = listOf(
                    "When you need a simple improvement over bubble sort",
                    "Educational purposes (demonstrating gap optimization)",
                    "When recursion is not available"
                ),
                notRecommended = listOf(
                    "Production systems (better alternatives exist)",
                    "Large datasets",
                    "When stability is required"
                ),
                realWorldExamples = listOf(
                    "Legacy systems maintaining backward compatibility",
                    "Teaching algorithm optimization concepts"
                )
            )
            SortType.COCKTAIL -> AlgorithmUseCase(
                sortType = SortType.COCKTAIL,
                bestUseCases = listOf(
                    "Nearly sorted data with elements at both ends out of place",
                    "Educational demonstration of bubble sort optimization",
                    "Small datasets"
                ),
                notRecommended = listOf(
                    "Large datasets",
                    "Random data",
                    "Production systems"
                ),
                realWorldExamples = listOf(
                    "Animation of bidirectional sorting",
                    "Teaching sorting concepts"
                )
            )
            SortType.GNOME -> AlgorithmUseCase(
                sortType = SortType.GNOME,
                bestUseCases = listOf(
                    "Educational purposes (simplest sort to code)",
                    "Embedded systems with extreme code size constraints",
                    "Nearly sorted small arrays"
                ),
                notRecommended = listOf(
                    "Any production use",
                    "Large datasets",
                    "Performance-sensitive applications"
                ),
                realWorldExamples = listOf(
                    "Teaching basic sorting concepts",
                    "Code golf competitions"
                )
            )
            SortType.ODD_EVEN -> AlgorithmUseCase(
                sortType = SortType.ODD_EVEN,
                bestUseCases = listOf(
                    "Parallel processing environments",
                    "SIMD implementations",
                    "GPU sorting of small arrays"
                ),
                notRecommended = listOf(
                    "Sequential processing (use other algorithms)",
                    "Large datasets without parallel hardware",
                    "When cache efficiency matters"
                ),
                realWorldExamples = listOf(
                    "Parallel computing education",
                    "GPU-based sorting demonstrations",
                    "Network sorting implementations"
                )
            )
            SortType.BOGO -> AlgorithmUseCase(
                sortType = SortType.BOGO,
                bestUseCases = listOf(
                    "Educational demonstration of worst-case complexity",
                    "Humor in computer science",
                    "Testing random number generators (sort of)"
                ),
                notRecommended = listOf(
                    "Any practical application",
                    "Any array larger than 5 elements",
                    "Anything where you want it to finish"
                ),
                realWorldExamples = listOf(
                    "Computer science jokes",
                    "Teaching what NOT to do",
                    "Demonstrating average vs worst case"
                )
            )
            SortType.BITONIC -> AlgorithmUseCase(
                sortType = SortType.BITONIC,
                bestUseCases = listOf(
                    "GPU sorting (highly parallelizable)",
                    "FPGA implementations",
                    "Hardware sorting networks"
                ),
                notRecommended = listOf(
                    "Sequential CPU processing",
                    "Non-power-of-2 array sizes (padding needed)",
                    "When simplicity is important"
                ),
                realWorldExamples = listOf(
                    "CUDA sorting implementations",
                    "Network switch packet ordering",
                    "FPGA-based sorting accelerators"
                )
            )
        }
    }

    fun getImplementation(sortType: SortType): AlgorithmImplementation {
        return when (sortType) {
            SortType.BUBBLE -> AlgorithmImplementation(
                sortType = SortType.BUBBLE,
                code = "" +
                    "fun bubbleSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    for (i in 0 until n - 1) {\n" +
                    "        var swapped = false\n" +
                    "        for (j in 0 until n - i - 1) {\n" +
                    "            if (arr[j] > arr[j + 1]) {\n" +
                    "                // Swap arr[j] and arr[j+1]\n" +
                    "                val temp = arr[j]\n" +
                    "                arr[j] = arr[j + 1]\n" +
                    "                arr[j + 1] = temp\n" +
                    "                swapped = true\n" +
                    "            }\n" +
                    "        }\n" +
                    "        // If no two elements were swapped by inner loop, then break\n" +
                    "        if (!swapped) break\n" +
                    "    }\n" +
                    "}",
                description = "Standard optimized Bubble Sort with early exit flag."
            )
            SortType.SELECTION -> AlgorithmImplementation(
                sortType = SortType.SELECTION,
                code = "" +
                    "fun selectionSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    for (i in 0 until n - 1) {\n" +
                    "        var minIdx = i\n" +
                    "        for (j in i + 1 until n) {\n" +
                    "            if (arr[j] < arr[minIdx]) {\n" +
                    "                minIdx = j\n" +
                    "            }\n" +
                    "        }\n" +
                    "        // Swap the found minimum element with the first element\n" +
                    "        val temp = arr[minIdx]\n" +
                    "        arr[minIdx] = arr[i]\n" +
                    "        arr[i] = temp\n" +
                    "    }\n" +
                    "}",
                description = "Basic Selection Sort implementation."
            )
            SortType.INSERTION -> AlgorithmImplementation(
                sortType = SortType.INSERTION,
                code = "" +
                    "fun insertionSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    for (i in 1 until n) {\n" +
                    "        val key = arr[i]\n" +
                    "        var j = i - 1\n" +
                    "\n" +
                    "        // Move elements of arr[0..i-1], that are greater than key,\n" +
                    "        // to one position ahead of their current position\n" +
                    "        while (j >= 0 && arr[j] > key) {\n" +
                    "            arr[j + 1] = arr[j]\n" +
                    "            j = j - 1\n" +
                    "        }\n" +
                    "        arr[j + 1] = key\n" +
                    "    }\n" +
                    "}",
                description = "Standard Insertion Sort."
            )
            SortType.SHELL -> AlgorithmImplementation(
                sortType = SortType.SHELL,
                code = "" +
                    "fun shellSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    // Start with a big gap, then reduce the gap\n" +
                    "    var gap = n / 2\n" +
                    "    while (gap > 0) {\n" +
                    "        for (i in gap until n) {\n" +
                    "            val temp = arr[i]\n" +
                    "            var j = i\n" +
                    "            while (j >= gap && arr[j - gap] > temp) {\n" +
                    "                arr[j] = arr[j - gap]\n" +
                    "                j -= gap\n" +
                    "            }\n" +
                    "            arr[j] = temp\n" +
                    "        }\n" +
                    "        gap /= 2\n" +
                    "    }\n" +
                    "}",
                description = "Shell Sort using the original shell sequence (n/2, n/4, ...)."
            )
            SortType.MERGE -> AlgorithmImplementation(
                sortType = SortType.MERGE,
                code = "" +
                    "fun mergeSort(arr: IntArray) {\n" +
                    "    if (arr.size < 2) return\n" +
                    "    val mid = arr.size / 2\n" +
                    "    val left = arr.copyOfRange(0, mid)\n" +
                    "    val right = arr.copyOfRange(mid, arr.size)\n" +
                    "\n" +
                    "    mergeSort(left)\n" +
                    "    mergeSort(right)\n" +
                    "\n" +
                    "    merge(arr, left, right)\n" +
                    "}\n" +
                    "\n" +
                    "fun merge(arr: IntArray, left: IntArray, right: IntArray) {\n" +
                    "    var i = 0; var j = 0; var k = 0\n" +
                    "    while (i < left.size && j < right.size) {\n" +
                    "        if (left[i] <= right[j]) {\n" +
                    "            arr[k++] = left[i++]\n" +
                    "        } else {\n" +
                    "            arr[k++] = right[j++]\n" +
                    "        }\n" +
                    "    }\n" +
                    "    while (i < left.size) arr[k++] = left[i++]\n" +
                    "    while (j < right.size) arr[k++] = right[j++]\n" +
                    "}",
                description = "Recursive Merge Sort implementation."
            )
            SortType.QUICK -> AlgorithmImplementation(
                sortType = SortType.QUICK,
                code = "" +
                    "fun quickSort(arr: IntArray, low: Int, high: Int) {\n" +
                    "    if (low < high) {\n" +
                    "        val pi = partition(arr, low, high)\n" +
                    "        quickSort(arr, low, pi - 1)\n" +
                    "        quickSort(arr, pi + 1, high)\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "fun partition(arr: IntArray, low: Int, high: Int): Int {\n" +
                    "    val pivot = arr[high]\n" +
                    "    var i = (low - 1)\n" +
                    "    for (j in low until high) {\n" +
                    "        if (arr[j] < pivot) {\n" +
                    "            i++\n" +
                    "            val temp = arr[i]\n" +
                    "            arr[i] = arr[j]\n" +
                    "            arr[j] = temp\n" +
                    "        }\n" +
                    "    }\n" +
                    "    val temp = arr[i + 1]\n" +
                    "    arr[i + 1] = arr[high]\n" +
                    "    arr[high] = temp\n" +
                    "    return i + 1\n" +
                    "}",
                description = "Standard Quick Sort using Lomuto partition scheme."
            )
            SortType.HEAP -> AlgorithmImplementation(
                sortType = SortType.HEAP,
                code = "" +
                    "fun heapSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    // Build heap (rearrange array)\n" +
                    "    for (i in n / 2 - 1 downTo 0)\n" +
                    "        heapify(arr, n, i)\n" +
                    "\n" +
                    "    // One by one extract an element from heap\n" +
                    "    for (i in n - 1 downTo 0) {\n" +
                    "        // Move current root to end\n" +
                    "        val temp = arr[0]\n" +
                    "        arr[0] = arr[i]\n" +
                    "        arr[i] = temp\n" +
                    "\n" +
                    "        // call max heapify on the reduced heap\n" +
                    "        heapify(arr, i, 0)\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "fun heapify(arr: IntArray, n: Int, i: Int) {\n" +
                    "    var largest = i\n" +
                    "    val l = 2 * i + 1\n" +
                    "    val r = 2 * i + 2\n" +
                    "\n" +
                    "    if (l < n && arr[l] > arr[largest])\n" +
                    "        largest = l\n" +
                    "\n" +
                    "    if (r < n && arr[r] > arr[largest])\n" +
                    "        largest = r\n" +
                    "\n" +
                    "    if (largest != i) {\n" +
                    "        val swap = arr[i]\n" +
                    "        arr[i] = arr[largest]\n" +
                    "        arr[largest] = swap\n" +
                    "\n" +
                    "        heapify(arr, n, largest)\n" +
                    "    }\n" +
                    "}",
                description = "Heap Sort implementation."
            )
            SortType.COUNTING -> AlgorithmImplementation(
                sortType = SortType.COUNTING,
                code = "" +
                    "fun countingSort(arr: IntArray) {\n" +
                    "    if (arr.isEmpty()) return\n" +
                    "    val max = arr.maxOrNull()!!\n" +
                    "    val min = arr.minOrNull()!!\n" +
                    "    val range = max - min + 1\n" +
                    "    val count = IntArray(range)\n" +
                    "    val output = IntArray(arr.size)\n" +
                    "\n" +
                    "    // Count occurrences\n" +
                    "    for (num in arr) count[num - min]++\n" +
                    "\n" +
                    "    // Compute cumulative count\n" +
                    "    for (i in 1 until range) count[i] += count[i - 1]\n" +
                    "\n" +
                    "    // Build output array (traverse in reverse for stability)\n" +
                    "    for (i in arr.lastIndex downTo 0) {\n" +
                    "        output[count[arr[i] - min] - 1] = arr[i]\n" +
                    "        count[arr[i] - min]--\n" +
                    "    }\n" +
                    "\n" +
                    "    // Copy output to original array\n" +
                    "    output.copyInto(arr)\n" +
                    "}",
                description = "Counting Sort with support for negative numbers."
            )
            SortType.RADIX -> AlgorithmImplementation(
                sortType = SortType.RADIX,
                code = "" +
                    "fun radixSort(arr: IntArray) {\n" +
                    "    if (arr.isEmpty()) return\n" +
                    "    val max = arr.maxOrNull()!!\n" +
                    "    var exp = 1\n" +
                    "\n" +
                    "    while (max / exp > 0) {\n" +
                    "        countingSortByDigit(arr, exp)\n" +
                    "        exp *= 10\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "fun countingSortByDigit(arr: IntArray, exp: Int) {\n" +
                    "    val n = arr.size\n" +
                    "    val output = IntArray(n)\n" +
                    "    val count = IntArray(10)\n" +
                    "\n" +
                    "    for (num in arr) count[(num / exp) % 10]++\n" +
                    "    for (i in 1 until 10) count[i] += count[i - 1]\n" +
                    "\n" +
                    "    for (i in n - 1 downTo 0) {\n" +
                    "        val digit = (arr[i] / exp) % 10\n" +
                    "        output[count[digit] - 1] = arr[i]\n" +
                    "        count[digit]--\n" +
                    "    }\n" +
                    "    output.copyInto(arr)\n" +
                    "}",
                description = "LSD Radix Sort for non-negative integers."
            )
            SortType.BUCKET -> AlgorithmImplementation(
                sortType = SortType.BUCKET,
                code = "" +
                    "fun bucketSort(arr: FloatArray) {\n" +
                    "    val n = arr.size\n" +
                    "    if (n <= 0) return\n" +
                    "\n" +
                    "    // Create empty buckets\n" +
                    "    val buckets = Array(n) { mutableListOf<Float>() }\n" +
                    "\n" +
                    "    // Put elements in different buckets\n" +
                    "    for (value in arr) {\n" +
                    "        val idx = (n * value).toInt().coerceIn(0, n - 1)\n" +
                    "        buckets[idx].add(value)\n" +
                    "    }\n" +
                    "\n" +
                    "    // Sort individual buckets\n" +
                    "    for (bucket in buckets) bucket.sort()\n" +
                    "\n" +
                    "    // Concatenate all buckets into arr\n" +
                    "    var index = 0\n" +
                    "    for (bucket in buckets) {\n" +
                    "        for (value in bucket) arr[index++] = value\n" +
                    "    }\n" +
                    "}",
                description = "Bucket Sort for floats in range [0, 1)."
            )
            SortType.TIM -> AlgorithmImplementation(
                sortType = SortType.TIM,
                code = "" +
                    "const val RUN = 32\n" +
                    "\n" +
                    "fun timSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "\n" +
                    "    // Sort individual runs using insertion sort\n" +
                    "    for (i in 0 until n step RUN) {\n" +
                    "        insertionSort(arr, i, minOf(i + RUN - 1, n - 1))\n" +
                    "    }\n" +
                    "\n" +
                    "    // Merge runs\n" +
                    "    var size = RUN\n" +
                    "    while (size < n) {\n" +
                    "        for (left in 0 until n step 2 * size) {\n" +
                    "            val mid = left + size - 1\n" +
                    "            val right = minOf(left + 2 * size - 1, n - 1)\n" +
                    "            if (mid < right) merge(arr, left, mid, right)\n" +
                    "        }\n" +
                    "        size *= 2\n" +
                    "    }\n" +
                    "}",
                description = "Simplified Tim Sort (Python/Java standard library sort)."
            )
            SortType.COMB -> AlgorithmImplementation(
                sortType = SortType.COMB,
                code = "" +
                    "fun combSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    var gap = n\n" +
                    "    val shrink = 1.3\n" +
                    "    var sorted = false\n" +
                    "\n" +
                    "    while (!sorted) {\n" +
                    "        gap = (gap / shrink).toInt().coerceAtLeast(1)\n" +
                    "        sorted = (gap == 1)\n" +
                    "\n" +
                    "        for (i in 0 until n - gap) {\n" +
                    "            if (arr[i] > arr[i + gap]) {\n" +
                    "                val temp = arr[i]\n" +
                    "                arr[i] = arr[i + gap]\n" +
                    "                arr[i + gap] = temp\n" +
                    "                sorted = false\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
                description = "Comb Sort with shrink factor 1.3."
            )
            SortType.COCKTAIL -> AlgorithmImplementation(
                sortType = SortType.COCKTAIL,
                code = "" +
                    "fun cocktailSort(arr: IntArray) {\n" +
                    "    var swapped = true\n" +
                    "    var start = 0\n" +
                    "    var end = arr.size - 1\n" +
                    "\n" +
                    "    while (swapped) {\n" +
                    "        swapped = false\n" +
                    "        // Forward pass\n" +
                    "        for (i in start until end) {\n" +
                    "            if (arr[i] > arr[i + 1]) {\n" +
                    "                arr[i] = arr[i + 1].also { arr[i + 1] = arr[i] }\n" +
                    "                swapped = true\n" +
                    "            }\n" +
                    "        }\n" +
                    "        if (!swapped) break\n" +
                    "        swapped = false\n" +
                    "        end--\n" +
                    "        // Backward pass\n" +
                    "        for (i in end - 1 downTo start) {\n" +
                    "            if (arr[i] > arr[i + 1]) {\n" +
                    "                arr[i] = arr[i + 1].also { arr[i + 1] = arr[i] }\n" +
                    "                swapped = true\n" +
                    "            }\n" +
                    "        }\n" +
                    "        start++\n" +
                    "    }\n" +
                    "}",
                description = "Cocktail (Shaker) Sort - bidirectional bubble sort."
            )
            SortType.GNOME -> AlgorithmImplementation(
                sortType = SortType.GNOME,
                code = "" +
                    "fun gnomeSort(arr: IntArray) {\n" +
                    "    var pos = 0\n" +
                    "    while (pos < arr.size) {\n" +
                    "        if (pos == 0 || arr[pos] >= arr[pos - 1]) {\n" +
                    "            pos++\n" +
                    "        } else {\n" +
                    "            // Swap and move back\n" +
                    "            val temp = arr[pos]\n" +
                    "            arr[pos] = arr[pos - 1]\n" +
                    "            arr[pos - 1] = temp\n" +
                    "            pos--\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
                description = "Gnome Sort - the simplest sorting algorithm."
            )
            SortType.ODD_EVEN -> AlgorithmImplementation(
                sortType = SortType.ODD_EVEN,
                code = "" +
                    "fun oddEvenSort(arr: IntArray) {\n" +
                    "    val n = arr.size\n" +
                    "    var sorted = false\n" +
                    "\n" +
                    "    while (!sorted) {\n" +
                    "        sorted = true\n" +
                    "        // Odd phase\n" +
                    "        for (i in 1 until n - 1 step 2) {\n" +
                    "            if (arr[i] > arr[i + 1]) {\n" +
                    "                arr[i] = arr[i + 1].also { arr[i + 1] = arr[i] }\n" +
                    "                sorted = false\n" +
                    "            }\n" +
                    "        }\n" +
                    "        // Even phase\n" +
                    "        for (i in 0 until n - 1 step 2) {\n" +
                    "            if (arr[i] > arr[i + 1]) {\n" +
                    "                arr[i] = arr[i + 1].also { arr[i + 1] = arr[i] }\n" +
                    "                sorted = false\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
                description = "Odd-Even Sort - parallelizable sorting."
            )
            SortType.BOGO -> AlgorithmImplementation(
                sortType = SortType.BOGO,
                code = "" +
                    "fun bogoSort(arr: IntArray) {\n" +
                    "    while (!isSorted(arr)) {\n" +
                    "        arr.shuffle()\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "fun isSorted(arr: IntArray): Boolean {\n" +
                    "    for (i in 0 until arr.size - 1) {\n" +
                    "        if (arr[i] > arr[i + 1]) return false\n" +
                    "    }\n" +
                    "    return true\n" +
                    "}\n" +
                    "\n" +
                    "// WARNING: Average complexity O((n+1)!)\n" +
                    "// Do NOT use for n > 10",
                description = "Bogo Sort - the worst sorting algorithm. For educational purposes only!"
            )
            SortType.BITONIC -> AlgorithmImplementation(
                sortType = SortType.BITONIC,
                code = "" +
                    "fun bitonicSort(arr: IntArray, up: Boolean = true) {\n" +
                    "    bitonicSort(arr, 0, arr.size, up)\n" +
                    "}\n" +
                    "\n" +
                    "fun bitonicSort(arr: IntArray, low: Int, cnt: Int, up: Boolean) {\n" +
                    "    if (cnt > 1) {\n" +
                    "        val k = cnt / 2\n" +
                    "        bitonicSort(arr, low, k, true)      // ascending\n" +
                    "        bitonicSort(arr, low + k, k, false) // descending\n" +
                    "        bitonicMerge(arr, low, cnt, up)\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "fun bitonicMerge(arr: IntArray, low: Int, cnt: Int, up: Boolean) {\n" +
                    "    if (cnt > 1) {\n" +
                    "        val k = cnt / 2\n" +
                    "        for (i in low until low + k) {\n" +
                    "            if ((arr[i] > arr[i + k]) == up) {\n" +
                    "                arr[i] = arr[i + k].also { arr[i + k] = arr[i] }\n" +
                    "            }\n" +
                    "        }\n" +
                    "        bitonicMerge(arr, low, k, up)\n" +
                    "        bitonicMerge(arr, low + k, k, up)\n" +
                    "    }\n" +
                    "}",
                description = "Bitonic Sort - parallel sorting network (requires power-of-2 size)."
            )
        }
    }
}
