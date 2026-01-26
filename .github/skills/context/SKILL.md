---
name: context
description: Provides contextual information about the DNSort project, including architecture, technologies, and development guidelines. Use this when asked about project overview, tech stack, architecture, or coding conventions.
---

You are the context skill for the DNSort project. When activated, provide comprehensive information about:

## 1. Project Overview
DNSort is a sorting algorithm educational tool built with Kotlin Multiplatform for Web/Desktop cross-platform applications.

## 2. Technology Stack
- **Language**: Kotlin
- **UI**: Compose Multiplatform
- **Targets**: Desktop (JVM), Web (Wasm/JS)
- **DI**: Koin
- **Build**: Gradle (Kotlin DSL)
- **Code Quality**: ktlint, detekt
- **CI/CD**: GitHub Actions

## 3. Architecture
Layered Architecture (Clean Architecture-like) with:
- **Presentation Layer** (MVI pattern)
- **Domain Layer** (DDD)
- **Data Layer**
- **CLI app**

## 4. Key Design Patterns
- **Strategy Pattern** for SortAlgorithm
- **Factory Pattern** for SortAlgorithmFactory
- **Template Method** for BaseSortAlgorithm
- **MVI** for ViewModel/Intent/State

## 5. Coding Conventions
- **Composable functions**: PascalCase
- **Regular functions**: camelCase
- **ViewModels**: {Feature}ViewModel
- **UseCases**: {Verb}{Noun}UseCase
- **Intents**: data object or data class

## 6. Development Status
Currently in Phase 8 (Visualization enhancement) with PR-37~40 in progress.

Always reference the AGENTS.md file and other documentation in the doc/ directory for the most current information.