package dotnet.sort.presentation.feature.learn.model

import dotnet.sort.domain.model.SortType

data class AlgorithmConcept(
    val sortType: SortType,
    val howItWorks: String,
    val keyIdea: String,
    val steps: List<String>
)
