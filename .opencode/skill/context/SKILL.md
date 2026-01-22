---
name: context
description: Gather comprehensive project context and current development status
compatibility: opencode
metadata:
  audience: developers
  workflow: development
---

## What I do
- Read and summarize AGENTS.md for project overview and architecture
- Analyze current git status and branch information
- Check development plan and current phase
- Provide project structure overview
- Summarize recent commits and changes

## When to use me
Use this skill when you need to understand the current project context, development status, or get oriented with the codebase before starting work.

## Output format
Return a structured summary in the following format:

```markdown
# Project Context Summary

## Project Overview
[Brief description from AGENTS.md]

## Current Development Phase
[Current phase and progress from DEVELOPMENT_PLAN.md]

## Technical Stack
[Key technologies and frameworks]

## Current Status
- **Branch**: [current branch name]
- **Status**: [clean/dirty with changes]
- **Recent Commits**: [last 3 commits summary]

## Key Architecture Points
[Bullet points of important architectural decisions]

## Active Development Areas
[Current focus areas based on development plan]
```

## Required inputs
- Access to AGENTS.md and project documentation
- Current git repository state

## Notes
- Always read AGENTS.md first for project context
- Focus on actionable information for development
- Include relevant file paths for easy navigation