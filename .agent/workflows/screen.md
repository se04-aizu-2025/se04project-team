---
description: Add a new screen (Feature)
---

# Add Screen Workflow

This workflow guides you through adding a new screen (Feature) to the project.

## Step 1: Create Feature Directory

Create the directory structure:
`presentation/feature/src/commonMain/kotlin/dotnet/sort/presentation/feature/{name}/`

## Step 2: Define State

Create `{Name}State.kt`:
- Data class implementing `UiState`
- All properties must be `val` and have default values.

```kotlin
data class LearnState(
    val isLoading: Boolean = false,
    // ...
) : UiState
```

## Step 3: Define Intent

Create `{Name}Intent.kt`:
- Sealed class implementing `Intent`
- Create `data object` for no-arg intents, `data class` for intents with data.

```kotlin
sealed class LearnIntent : Intent {
    data object Load : LearnIntent()
    // ...
}
```

## Step 4: Implement ViewModel

Create `{Name}ViewModel.kt`:
- Extend `BaseViewModel<{Name}State, {Name}Intent>`.
- Implement `send(intent)` method.

## Step 5: Implement Screen

Create `{Name}Screen.kt`:
- Create a stateless `Content` composable and a stateful `Screen` composable.
- Use `SortScaffold` or standard layout.

## Step 6: Setup Navigation

1. Create `{Name}Destination.kt` in `presentation/navigation/...`.
2. Define a Serializable object in `Screen.kt`.
3. Implement `NavGraphBuilder.{name}Destination` extension.
4. Add to `AppNavigation.kt`.

## Step 7: Register DI Module

1. Create `{Name}FeatureModule.kt`.
2. Define Koin module.
3. Add to `startKoin` in `composeApp`.

## Step 8: Verify

Run the application and verify navigation.

```bash
./gradlew :composeApp:run
```
