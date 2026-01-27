---
description: Add a new test
---

# Add Test Workflow

This workflow guides you through adding a new test.

## Step 1: Identify Test Target

Determine if you are testing:
- **Algorithm**: `domain/src/commonTest/...`
- **UseCase**: `domain/src/commonTest/...`
- **ViewModel**: `presentation/feature/{name}/src/commonTest/...`
- **Generator**: `data/src/commonTest/...`

## Step 2: Create Test Class

Create `{ClassName}Test.kt` in the appropriate source set.

**Naming Rule**: `{TestTarget}Test`

## Step 3: Write Test Cases

Use Given-When-Then pattern.

```kotlin
@Test
fun `GIVEN condition WHEN action THEN expectation`() {
    // Given
    
    // When
    
    // Then
    assertEquals(expected, actual)
}
```

## Step 4: Run Tests

Run the specific test to verify.

```bash
./gradlew :module:test --tests "*{ClassName}Test*"
```
