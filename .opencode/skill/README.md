# Skills

This directory contains skills that extend the capabilities of the agent.

## Available Skills

### [pr-template-summary](skill/pr-template-summary/SKILL.md)
Generates a Pull Request draft aligned with the project's standards.
- **Audience:** Maintainers
- **Usage:** Call to generate a PR description after making changes.
- **Output:** A Japanese PR template with summary, changes, and checklist.

### [read-all-docs](skill/read-all-docs/SKILL.md)
Reads and summarizes all implementation documentation.
- **Audience:** Maintainers
- **Usage:** Call when you need a full context refresh of the project's documentation.
- **Output:** A summary of architecture, rules, and workflows.

## Usage
Skills are automatically discovered and used by the agent when relevant to the user request.
