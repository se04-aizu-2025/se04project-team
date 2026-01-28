---
title: Navigation モジュール
version: 1.0.0
last_updated: 2026-01-16
maintainer: Team
parent: "[📚 ドキュメント一覧](../../doc/README.md)"
---

# Navigation モジュール

アプリケーションのナビゲーション定義を提供するモジュールです。

---

## 目的

- **Screen 定義**: 各画面のルートを型安全に定義
- **NavHost 構築**: 画面遷移グラフを構築
- **Destination 管理**: 各画面への遷移ロジック

---

## 📦 構造

```
navigation/src/commonMain/kotlin/
├── Screen.kt           # @Serializable sealed class
├── AppNavigation.kt    # NavHost 定義
└── {Feature}Destination.kt  # 各画面の Destination
```

---

## 🔑 主要コンポーネント

### Screen.kt

```kotlin
@Serializable
sealed class Screen {
    @Serializable data object Home : Screen()
    @Serializable data object Sort : Screen()
    // ...
}
```

### Destination パターン

```kotlin
fun NavGraphBuilder.sortDestination(onBackClick: () -> Unit) {
    composable<Screen.Sort> {
        val viewModel: SortViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        SortScreen(state = state, onIntent = viewModel::send, ...)
    }
}
```

---

## 関連ドキュメント

| ドキュメント | 説明 |
|--------------|------|
| [ADD_SCREEN.md](../../doc/guide/tasks/ADD_SCREEN.md) | 画面追加手順 |
| [NAVIGATION.md](../../doc/guide/reference/NAVIGATION.md) | ナビゲーション詳細 |
