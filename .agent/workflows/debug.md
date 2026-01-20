---
description: Debug an issue or error
---

# Debug Issue Workflow

This workflow guides you through debugging issues using the project's troubleshooting guide.

## Step 1: Read Debug Guide

Read `doc/guide/tasks/DEBUG_GUIDE.md` to check for known issues.

## Step 2: Analyze Error

1. Ask user for the error message or stack trace.
2. Categorize the error:
   - **Build Error**: (Unresolved reference, Gradle sync)
   - **Runtime Error**: (Koin definition, Crash)
   - **Logic Error**: (Sort incorrect, UI freeze)

## Step 3: Search codebase

If not in the guide, search for the error keywords in the codebase.

## Step 4: Propose Solution

1. If found in guide, apply the recommended fix.
2. If new, propose a fix based on architecture patterns (e.g., check Koin module registration, check State immutability).

## Step 5: Verification

Ask user to re-run the build or test.
