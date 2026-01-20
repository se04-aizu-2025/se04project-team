---
description: Add a new Repository
---

# Add Repository Workflow

This workflow guides you through adding a new Repository.

## Step 1: Define Interface (Domain)

Create `{Name}Repository.kt` in `domain/src/commonMain/kotlin/dotnet/sort/domain/repository/`.

```kotlin
interface SortRepository {
    suspend fun getAlgorithms(): List<Algorithm>
}
```

## Step 2: Implement Repository (Data)

Create `{Name}RepositoryImpl.kt` in `data/src/commonMain/kotlin/dotnet/sort/data/repository/`.

```kotlin
class SortRepositoryImpl(
    private val dataSource: DataSource
) : SortRepository {
    override suspend fun getAlgorithms(): List<Algorithm> {
        // Implementation
    }
}
```

## Step 3: Register in DI

Open `di/src/commonMain/kotlin/dotnet/sort/di/DataModule.kt`.

```kotlin
single<SortRepository> { SortRepositoryImpl(get()) }
```

## Step 4: Create Test

Create `data/src/commonTest/kotlin/dotnet/sort/data/repository/{Name}RepositoryImplTest.kt`.
