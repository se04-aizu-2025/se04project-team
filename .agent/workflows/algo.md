---
description: Add a new sort algorithm
---

# Add Algorithm Workflow

This workflow guides you through adding a new sort algorithm to the project.

## Step 1: Update SortType enum

1. Open `domain/src/commonMain/kotlin/dotnet/sort/domain/model/SortType.kt`.
2. Add a new enum entry for the algorithm.
   - Specify `displayName`, `timeComplexity`, and `spaceComplexity`.

```kotlin
// Example:
COUNTING("Counting Sort", "O(n + k)", "O(k)"),
```

## Step 2: Create Algorithm Implementation

1. Create a new file `domain/src/commonMain/kotlin/dotnet/sort/domain/algorithm/{Name}SortAlgorithm.kt`.
2. Implement the class extending `BaseSortAlgorithm`.
3. Override `type` and implement `doSort`.

```kotlin
class CountingSortAlgorithm : BaseSortAlgorithm() {
    override val type = SortType.COUNTING
    
    override fun doSort(array: MutableList<Int>) {
        // Implementation
        // Use addSnapshot() for visualization
    }
}
```

## Step 3: Register in Factory

1. Open `domain/src/commonMain/kotlin/dotnet/sort/domain/algorithm/SortAlgorithmFactory.kt`.
2. Add the new case to the `when` expression in `create` method.

```kotlin
SortType.COUNTING -> CountingSortAlgorithm()
```

## Step 4: Create Test

1. Create `domain/src/commonTest/kotlin/dotnet/sort/domain/algorithm/{Name}SortAlgorithmTest.kt`.
2. Extend `BaseSortAlgorithmTest`.

```kotlin
class CountingSortAlgorithmTest : BaseSortAlgorithmTest() {
    override fun createAlgorithm() = CountingSortAlgorithm()
    
    // Add specific edge case tests
}
```

## Step 5: Verify

Run tests to ensure correctness.

```bash
./gradlew :domain:test --tests "*{Name}SortAlgorithmTest*"
```
