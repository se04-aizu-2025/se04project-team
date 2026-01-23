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
}
