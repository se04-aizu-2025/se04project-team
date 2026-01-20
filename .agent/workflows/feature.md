---
description: Add a new Feature Module
---

# Add Feature Module Workflow

This workflow guides you through adding a new Feature Module.

## Step 1: Create Directory Structure

`presentation/feature/{name}/`
- `build.gradle.kts`
- `src/commonMain/kotlin/dotnet/sort/presentation/feature/{name}/`

## Step 2: setup build.gradle.kts

Copy configuration from another feature module (e.g., `presentation/feature/home/build.gradle.kts`).

## Step 3: Register in settings.gradle.kts

Add `include(":presentation:feature:{name}")`.

## Step 4: Implement Core Classes

Create:
- `{Name}State.kt`
- `{Name}Intent.kt`
- `{Name}ViewModel.kt`
- `{Name}Screen.kt`
- `{Name}Navigation.kt`

## Step 5: Setup DI

Create `di/{Name}FeatureModule.kt` and register in `StartKoin.kt`.

## Step 6: Verify Build

```bash
./gradlew :presentation:feature:{name}:build
```
