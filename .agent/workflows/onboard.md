---
description: Onboard a new developer
---

# Onboard Developer Workflow

This workflow guides a new developer through the project structure and setup.

## Step 1: Architecture Overview

Read `doc/guide/ONBOARDING.md` and explain:
- **Architecture**: Clean Architecture + DDD + MVI.
- **Modules**: `presentation`, `domain`, `data`.

## Step 2: Setup Environment

Reference `doc/GETTING_STARTED.md`.
- Verify JDK version.
- Explain Gradle setup.

## Step 3: Key Rules Explanation

Reference `doc/guide/FUNDAMENTALS.md`.
- **Naming**: `{Feature}ViewModel`, `SortType` enum.
- **MVI**: `Intent` -> `ViewModel` -> `State`.
- **Formatting**: `ktlint`, `detekt`.

## Step 4: Run Demo

Instruct to run the app to verify setup.

```bash
./gradlew :composeApp:run
```
