---
description: Add a new Koin Module
---

# Add Koin Module Workflow

This workflow guides you through adding a new Koin Module.

## Step 1: Create Module File

Create `{Name}Module.kt` in the appropriate `di` package.

```kotlin
val myModule = module {
    single { MyService() }
    viewModel { MyViewModel(get()) }
}
```

## Step 2: Register in StartKoin

Open the Koin initialization file (e.g., `composeApp/src/commonMain/kotlin/dotnet/sort/composeApp/di/AppModule.kt` or `StartKoin.kt`).
Add the module to the list.

```kotlin
startKoin {
    modules(
        myModule,
        // ...
    )
}
```
