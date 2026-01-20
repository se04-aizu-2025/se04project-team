---
description: Prepare for a new release
---

# Prepare Release Workflow

This workflow guides you through preparing a version release.

## Step 1: Check Development Plan

Read `doc/DEVELOPMENT_PLAN.md` to confirm the current phase and release content.

## Step 2: Update Version

1. Check `libs.versions.toml` or `build.gradle.kts` for version definitions.
2. Increment `versionName` or `versionCode`.

## Step 3: Update Changelog

1. Create or update `CHANGELOG.md`.
2. List all PRs merged since the last release.

## Step 4: Create Release Branch

Suggest creating a `release/vX.X.X` branch.

## Step 5: Verify Build

Run a full clean build and test suite.

```bash
./gradlew clean build test
```
