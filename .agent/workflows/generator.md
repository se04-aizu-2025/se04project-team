---
description: Add a new Array Generator
---

# Add Data Generator Workflow

This workflow guides you through adding a new Array Generator.

## Step 1: Update ArrayGeneratorType

Open `domain/src/commonMain/kotlin/dotnet/sort/domain/model/ArrayGeneratorType.kt`.
Add a new enum entry.

```kotlin
MOUNTAIN("Mountain")
```

## Step 2: Implement Generator Logic

Open `data/src/commonMain/kotlin/dotnet/sort/data/generator/ArrayGeneratorImpl.kt`.
1. Update `generate` method's `when` expression.
2. Add a private method for the generation logic.

```kotlin
private fun generateMountain(size: Int): List<Int> {
    // Logic
}
```

## Step 3: Add Test

Open `data/src/commonTest/kotlin/dotnet/sort/data/generator/ArrayGeneratorImplTest.kt`.
Add a test case for the new type.

```kotlin
@Test
fun `GIVEN mountain type WHEN generate THEN returns mountain pattern`() {
    // Logic
}
```
