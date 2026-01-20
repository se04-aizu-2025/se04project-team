---
description: Add a new UseCase
---

# Add UseCase Workflow

This workflow guides you through adding a new UseCase.

## Step 1: Create UseCase Class

Create `{Verb}{Noun}UseCase.kt` in `domain/src/commonMain/kotlin/dotnet/sort/domain/usecase/`.

```kotlin
class ExecuteSortUseCase(
    private val repository: SortRepository
) {
    suspend fun execute(param: Param): Result {
        // Implementation
    }
}
```

**Naming**: `{Verb}{Noun}UseCase` (e.g., `GetSortAlgorithmsUseCase`)

## Step 2: Register in DI

Open `di/src/commonMain/kotlin/dotnet/sort/di/DomainModule.kt`.

```kotlin
single { ExecuteSortUseCase(get()) }
```

## Step 3: Create Test

Create `domain/src/commonTest/kotlin/dotnet/sort/domain/usecase/{Name}UseCaseTest.kt`.

```kotlin
class ExecuteSortUseCaseTest {
    @Test
    fun `GIVEN condition WHEN execute THEN result`() {
        // Test logic
    }
}
```
