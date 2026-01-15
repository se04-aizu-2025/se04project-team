# ナビゲーションガイド

このガイドでは、Navigation Compose の実装パターンと制約を定義します。

---

## ファイル構成

```
presentation/
├── navigation/                    # ナビゲーションモジュール
│   └── AppNavigation.kt          # NavHost 定義
│   └── Screen.kt                 # Route 定義
└── feature/{name}/
    └── {Name}Navigation.kt       # 各機能のナビゲーション拡張
```

---

## Route 定義

### Screen sealed class

```kotlin
// Screen.kt
@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()
    
    @Serializable
    data object Sort : Screen()
    
    @Serializable
    data object Learn : Screen()
    
    @Serializable
    data object Compare : Screen()
    
    @Serializable
    data object Settings : Screen()
    
    // 引数付きルート
    @Serializable
    data class AlgorithmDetail(val algorithmId: String) : Screen()
}
```

### ルール

| ルール | 詳細 |
|--------|------|
| **@Serializable 必須** | 型安全ナビゲーションに必須 |
| **sealed class** | 網羅的なwhen式を許可 |
| **引数なし → data object** | シングルトン |
| **引数あり → data class** | 引数をプロパティとして定義 |

**参考**: [Type-safe Navigation](https://developer.android.com/guide/navigation/design/type-safety)

---

## NavGraphBuilder 拡張関数

### 必須パターン

```kotlin
// {Feature}Navigation.kt

/**
 * {Feature} 機能のナビゲーションを NavGraph に登録する。
 *
 * @param onBackClick 戻るボタン押下時のコールバック
 */
fun NavGraphBuilder.{feature}Destination(
    onBackClick: () -> Unit
) {
    composable<Screen.{Feature}> {
        {Feature}Screen(
            onBackClick = onBackClick
        )
    }
}

/**
 * {Feature} 画面へ遷移する。
 */
fun NavController.navigateTo{Feature}() {
    navigate(Screen.{Feature})
}
```

### 命名規則

| 種類 | 命名 | 例 |
|------|------|-----|
| **NavGraphBuilder拡張** | `{feature}Destination()` | `sortDestination()`, `homeDestination()` |
| **NavController拡張** | `navigateTo{Feature}()` | `navigateToSort()` |
| **ファイル名** | `{Feature}Navigation.kt` | `SortNavigation.kt` |

**参考**: [Encapsulate Your Navigation Code](https://developer.android.com/guide/navigation/design/encapsulate)

---

## AppNavigation

### 構造

```kotlin
// AppNavigation.kt
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        // Home (スタート)
        homeDestination(
            onNavigateToSort = { navController.navigateToSort() },
            onNavigateToLearn = { navController.navigateToLearn() },
            onNavigateToCompare = { navController.navigateToCompare() },
            onNavigateToSettings = { navController.navigateToSettings() }
        )

        // Feature destinations
        sortDestination(
            onBackClick = { navController.popBackStack() }
        )

        learnDestination(
            onBackClick = { navController.popBackStack() }
        )

        compareDestination(
            onBackClick = { navController.popBackStack() }
        )

        settingsDestination(
            onBackClick = { navController.popBackStack() }
        )
    }
}
```

---

## ナビゲーションイベント

### 禁止事項

```kotlin
// ❌ 禁止 - Screen内でNavControllerを直接使用
@Composable
fun SortScreen(navController: NavController) {
    Button(onClick = { navController.navigate(Screen.Settings) }) { }
}

// ✅ 正解 - コールバックで抽象化
@Composable
fun SortScreen(onNavigateToSettings: () -> Unit) {
    Button(onClick = onNavigateToSettings) { }
}
```

### ルール

| ルール | 理由 |
|--------|------|
| **Screen に NavController を渡さない** | テスト容易性、再利用性 |
| **コールバック関数で抽象化** | 画面は「どこへ遷移するか」を知らない |
| **Navigation 拡張でルートを隠蔽** | ルート型の変更が他に影響しない |

---

## 戻る操作

### パターン

```kotlin
// ✅ 標準パターン
onBackClick = { navController.popBackStack() }

// ✅ 条件付き戻り
onBackClick = { 
    val popped = navController.popBackStack()
    if (!popped) {
        // バックスタックが空の場合の処理
    }
}

// ✅ 特定画面まで戻る
onBackToHome = { 
    navController.popBackStack(
        route = Screen.Home,
        inclusive = false
    )
}
```

---

## Deep Link (将来対応)

### 定義方法

```kotlin
composable<Screen.AlgorithmDetail>(
    deepLinks = listOf(
        navDeepLink { 
            uriPattern = "dnsort://algorithm/{algorithmId}"
        }
    )
) { backStackEntry ->
    val detail = backStackEntry.toRoute<Screen.AlgorithmDetail>()
    AlgorithmDetailScreen(algorithmId = detail.algorithmId)
}
```

---

## チェックリスト

新しい画面を追加する場合:

- [ ] `Screen` に route を追加 (`@Serializable`)
- [ ] `{Feature}Navigation.kt` を作成
- [ ] `NavGraphBuilder.{feature}Destination()` を実装
- [ ] `NavController.navigateTo{Feature}()` を実装
- [ ] `AppNavigation` に destination を追加
- [ ] 呼び出し元に `onNavigateTo{Feature}` コールバックを追加

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Navigation Compose** | [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) |
| **Type-safe Navigation** | [Type-safe Navigation](https://developer.android.com/guide/navigation/design/type-safety) |
| **Encapsulate Navigation** | [Encapsulate Your Navigation Code](https://developer.android.com/guide/navigation/design/encapsulate) |
| **Deep Links** | [Create Deep Links](https://developer.android.com/guide/navigation/design/deep-link) |
