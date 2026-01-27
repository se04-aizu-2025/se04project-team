---
description: Add a new CLI Command
---

# Add CLI Command Workflow

This workflow guides you through adding a new CLI Command.

## Step 1: Create Command Class

Create `cli/src/jvmMain/kotlin/dotnet/sort/cli/commands/{Name}Command.kt`.

```kotlin
class BenchmarkCommand(
    private val useCase: UseCase
) {
    fun execute(options: Options): Int {
        // Implementation
        return 0
    }
}
```

## Step 2: Update Argument Parser

Open `cli/src/jvmMain/kotlin/dotnet/sort/cli/ArgumentParser.kt`.
1. Add data class to `CliCommand` sealed class.
2. Update `parseArguments` to handle the new command.
3. Add parsing logic.

## Step 3: Update Main Execution

Open `cli/src/jvmMain/kotlin/dotnet/sort/cli/Main.kt`.
Update `executeCommand` to handle the new command.

## Step 4: Update Help

Update `printHelp` in `cli/src/jvmMain/kotlin/dotnet/sort/cli/Main.kt`.

## Step 5: Add Test

Create `cli/src/jvmTest/kotlin/dotnet/sort/cli/commands/{Name}CommandTest.kt`.
