---
description: Add a new Design Token
---

# Add Design Token Workflow

This workflow guides you through adding a new Design Token.

## Step 1: Add Token

Choose the appropriate file in `presentation/designsystem/src/commonMain/kotlin/dotnet/sort/presentation/designsystem/tokens/`:
- `ColorTokens.kt`
- `SpacingTokens.kt`
- `AnimationTokens.kt`

Add the new token constant.

## Step 2: Update Theme (If Color)

If adding a color:
1. Open `presentation/designsystem/src/commonMain/kotlin/dotnet/sort/presentation/designsystem/theme/SortColorScheme.kt`.
2. Add property to `SortColorScheme` data class.
3. Update `LightColorScheme` and `DarkColorScheme`.

## Step 3: Usage

Use the token in your components.

```kotlin
// Example
Modifier.padding(SpacingTokens.CardPadding)
```
