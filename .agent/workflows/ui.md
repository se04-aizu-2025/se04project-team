---
description: Add a new UI Component (Atom, Molecule, etc.)
---

# Add UI Component Workflow

This workflow guides you through adding a new UI Component.

## Step 1: Determine Classification

Decide if the component is:
- **Atom**: `presentation/designsystem/src/commonMain/kotlin/dotnet/sort/presentation/designsystem/components/atoms/`
- **Molecule**: `presentation/designsystem/src/commonMain/kotlin/dotnet/sort/presentation/designsystem/components/molecules/`
- **Organism**: `presentation/feature/{feature}/src/commonMain/kotlin/dotnet/sort/presentation/feature/{feature}/components/`

## Step 2: Create Component File

Create `{Name}.kt` in the appropriate directory.

```kotlin
@Composable
fun SortBar(
    value: Int,
    modifier: Modifier = Modifier
) {
    // Implementation
}
```

**Rules:**
- Use `SortTheme` tokens.
- `modifier` should be the last optional parameter.
- Keep it stateless if possible.

## Step 3: Add Preview

Add a private preview function.

```kotlin
@Preview
@Composable
private fun SortBarPreview() {
    SortTheme {
        SortBar(value = 50)
    }
}
```

## Step 4: Verify

Check the preview in Android Studio or run the app.
