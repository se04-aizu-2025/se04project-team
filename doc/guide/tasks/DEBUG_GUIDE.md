# デバッグガイド

このガイドでは、よくあるエラーとその解決策を説明します。

---

## ビルドエラー

### Unresolved reference エラー

```
e: Unresolved reference: SortViewModel
```

**原因**: 依存が不足している

**解決策**:
```kotlin
// build.gradle.kts に依存を追加
commonMain.dependencies {
    implementation(projects.presentation.feature.sort)
}
```

### Gradle Sync 失敗

```
Could not resolve project :presentation:feature:quiz
```

**原因**: settings.gradle.kts に include されていない

**解決策**:
```kotlin
// settings.gradle.kts
include(":presentation:feature:quiz")  // 追加
```

---

## ランタイムエラー

### Koin: No definition found

```
org.koin.core.error.NoBeanDefFoundException: 
No definition found for class:'SortViewModel'
```

**原因**: Koin モジュールに登録されていない

**解決策**:
```kotlin
// 1. Feature Module を作成
val sortFeatureModule = module {
    viewModel { SortViewModel(get(), get()) }
}

// 2. StartKoin に追加
startKoin {
    modules(sortFeatureModule)
}
```

### Koin: Circular dependency

```
Circular dependency detected
```

**原因**: A → B → A のような循環依存

**解決策**:
- 依存関係を見直す
- 共通部分を別クラスに抽出
- interface で抽象化

---

## Compose エラー

### @Composable invocations can only happen...

```
@Composable invocations can only happen from the context of a @Composable function
```

**原因**: Composable を非 Composable から呼び出している

**解決策**:
```kotlin
// ❌ 間違い
fun loadData() {
    MyComposable()  // 非 Composable から呼び出し
}

// ✅ 正解
@Composable
fun MyScreen() {
    MyComposable()  // Composable から呼び出し
}
```

### State が更新されない

**原因**: State を直接変更している

**解決策**:
```kotlin
// ❌ 間違い - State は不変
state.value.isLoading = true

// ✅ 正解 - copy() で新しいインスタンス
updateState { copy(isLoading = true) }
```

---

## Navigation エラー

### IllegalArgumentException: route not found

```
java.lang.IllegalArgumentException: 
Navigation destination that matches request NavDeepLinkRequest cannot be found
```

**原因**: NavHost に destination が登録されていない

**解決策**:
```kotlin
// AppNavigation.kt
NavHost(...) {
    // destination を追加
    sortDestination(onBackClick = { navController.popBackStack() })
    learnDestination(onBackClick = { navController.popBackStack() })
}
```

### popBackStack が効かない

**原因**: バックスタックが空

**解決策**:
```kotlin
val popped = navController.popBackStack()
if (!popped) {
    // バックスタックが空の場合の処理
    // 例: アプリを終了、ホームに戻る等
}
```

---

## テストエラー

### runTest タイムアウト

```
kotlinx.coroutines.test.UncompletedCoroutinesError
```

**原因**: テスト中のコルーチンが完了していない

**解決策**:
```kotlin
@Test
fun myTest() = runTest {
    // advanceUntilIdle() で全てのコルーチンを完了
    advanceUntilIdle()
    
    // または明示的にキャンセル
    testJob.cancel()
}
```

### MockK: No answer found

```
io.mockk.MockKException: no answer found for: ArrayGenerator.generate(10, RANDOM)
```

**原因**: モックの振る舞いが定義されていない

**解決策**:
```kotlin
@BeforeTest
fun setup() {
    every { mockGenerator.generate(any(), any()) } returns listOf(1, 2, 3)
}
```

---

## パフォーマンス問題

### UI がフリーズする

**原因**: メインスレッドで重い処理

**解決策**:
```kotlin
// ❌ 間違い - メインスレッドでブロック
val result = heavyCalculation()

// ✅ 正解 - バックグラウンドで実行
viewModelScope.launch {
    val result = withContext(Dispatchers.Default) {
        heavyCalculation()
    }
    updateState { copy(result = result) }
}
```

### 再レンダリングが多すぎる

**原因**: State が頻繁に更新されている

**解決策**:
```kotlin
// remember で不要な再計算を防ぐ
val sortedList = remember(list) { list.sorted() }

// derivedStateOf で派生状態を最適化
val isButtonEnabled by remember {
    derivedStateOf { state.isValid && !state.isLoading }
}
```

---

## デバッグテクニック

### ログ出力

```kotlin
// 状態変化をログ
println("State: ${state.value}")

// Intent をログ
override fun send(intent: SortIntent) {
    println("Received intent: $intent")
    when (intent) { ... }
}
```

### Compose デバッグ

```kotlin
// 再コンポジションを確認
@Composable
fun MyComponent() {
    SideEffect {
        println("MyComponent recomposed")
    }
}
```

---

## よくある間違い

| 間違い | 正解 |
|--------|------|
| `var` in State | `val` + `copy()` |
| `MutableList` in State | `List` |
| Screen に NavController | コールバックで抽象化 |
| viewModel.method() | viewModel.send(Intent) |
| GlobalScope | viewModelScope |

---

## 参考

- [Koin Troubleshooting](https://insert-koin.io/docs/reference/koin-core/dsl-update)
- [Compose Debugging](https://developer.android.com/jetpack/compose/tooling)
- [Coroutines Debugging](https://kotlinlang.org/docs/debug-coroutines-with-idea.html)
