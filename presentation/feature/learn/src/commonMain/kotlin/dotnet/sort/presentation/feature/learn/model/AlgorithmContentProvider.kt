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
}
