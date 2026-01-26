---
description: Create a Pull Request Draft
---

# Create Pull Request Workflow

This workflow guides you through creating a Pull Request (PR) draft conforming to the project standards.

## Step 1: Analyze Changes

1. Run `git diff --stat` to see changed files.
2. Run `git log` to see recent commits on the feature branch.

## Step 2: Read Template

Read `.github/pull_request_template.md` to understand the required format.

## Step 3: Generate Content

Generate the PR description strictly following the format in `.github/pull_request_template.md`:
- **Title**: `[Type] Title` (e.g., `feat: Add Sort Algorithm`)
- **Body**: Fill in the Japanese sections (`## 概要`, `## 変更内容`, etc.) exactly as defined in the template. Do not use English headers.

## Step 4: Verify Checklist

Ensure all items in the "PR Checklist" are met (Tests, Lint, Doc).

## Step 5: Output Draft

Present the generated PR Markdown to the user **inside a markdown code block** so they can easily copy and paste it.
