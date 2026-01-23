---
name: pr
description: Assist with pull request creation and management
compatibility: opencode
metadata:
  audience: developers
  workflow: github
---

## What I do
- Analyze current branch and changes for PR creation
- Check if branch follows naming conventions
- Generate PR title and description based on changes
- Validate that changes align with project conventions
- Suggest appropriate reviewers and labels
- Ensure PR follows project template format

## When to use me
Use this skill when preparing to create a pull request, need help with PR content, or want to validate your changes before submitting.

## Output format
Return a comprehensive PR preparation summary in the following format:

```markdown
# Pull Request Preparation

## Branch Analysis
- **Current Branch**: [branch name]
- **Naming Convention**: [compliant/non-compliant]
- **Base Branch**: [target branch, usually main]

## Changes Summary
- **Files Changed**: [count and types]
- **Lines Added/Removed**: [statistics]
- **Primary Changes**: [main areas affected]

## PR Content Draft

### Title
[Generated PR title following conventional format]

### Description
[Generated PR description in Japanese following project template]

## Validation Checks
- [ ] Branch naming follows convention
- [ ] Changes align with current development phase
- [ ] Tests are included/updated
- [ ] Documentation is updated
- [ ] Code follows project standards

## Next Steps
1. [Action items for user]
2. [Commands to run if needed]
```

## Required inputs
- Current git branch and status
- Changes to be included in PR
- Project conventions and templates

## Notes
- Always validate branch naming convention first
- Generate content in Japanese as per project standards
- Include specific file paths for easy reference
- Suggest reviewers based on changed areas