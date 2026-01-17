# 画面を追加する

このガイドでは、新しい画面を追加するために必要なすべての手順を説明します。

---

## 概要

新しい画面を追加するには、以下のファイルを作成します:

```
presentation/feature/{name}/
├── {Name}Screen.kt        # 画面 UI
├── {Name}ViewModel.kt     # 状態管理
├── {Name}Intent.kt        # ユーザーインテント
├── {Name}FeatureModule.kt # DI 登録
└── {Name}Navigation.kt    # ナビゲーション
```

---

## Step 1: State を定義

```kotlin
// {Name}ViewModel.kt 内、または別ファイル

data class LearnState(
    val algorithms: List<AlgorithmInfo> = emptyList(),
    val selectedAlgorithm: AlgorithmInfo? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
) : UiState
```

### ルール
- `data class` で定義
- すべて `val`
- デフォルト値を設定
- `UiState` を実装

---

## Step 2: Intent を定義

```kotlin
// {Name}Intent.kt

sealed class LearnIntent : Intent {
    // データなし
    data object LoadAlgorithms : LearnIntent()
    data object Refresh : LearnIntent()
    
    // データあり
    data class SelectAlgorithm(val algorithm: AlgorithmInfo) : LearnIntent()
}
```

### ルール
- `sealed class` で定義
- データなし → `data object`
- データあり → `data class`
- 動詞で開始

---

## Step 3: ViewModel を実装

```kotlin
// {Name}ViewModel.kt

class LearnViewModel(
    private val getAlgorithmsUseCase: GetAlgorithmsUseCase
) : BaseViewModel<LearnState, LearnIntent>(LearnState()) {

    init {
        loadAlgorithms()
    }

    override fun send(intent: LearnIntent) {
        when (intent) {
            LearnIntent.LoadAlgorithms -> loadAlgorithms()
            LearnIntent.Refresh -> refresh()
            is LearnIntent.SelectAlgorithm -> 
                updateState { copy(selectedAlgorithm = intent.algorithm) }
        }
    }

    private fun loadAlgorithms() {
        execute {
            updateState { copy(isLoading = true) }
            try {
                val algorithms = getAlgorithmsUseCase.execute()
                updateState { copy(isLoading = false, algorithms = algorithms) }
            } catch (e: Exception) {
                updateState { 
                    copy(isLoading = false, hasError = true, errorMessage = e.message) 
                }
            }
        }
    }
    
    private fun refresh() {
        updateState { copy(hasError = false, errorMessage = null) }
        loadAlgorithms()
    }
}
```

### ルール
- `BaseViewModel<State, Intent>` を継承
- `send()` で全 Intent を処理 (網羅的 when)
- `updateState { copy(...) }` で状態更新
- `execute { }` で非同期処理

---

## Step 4: Screen を実装

Screen は**ステートレス**で、`state`, `onIntent`, `onBackClick`, `modifier` を受け取ります。

```kotlin
// {Name}Screen.kt

/**
 * {Name} 画面。
 *
 * @param state 画面の状態
 * @param onIntent ユーザーアクションのコールバック
 * @param onBackClick 戻るボタン押下時のコールバック
 * @param modifier Modifier
 */
@Composable
fun LearnScreen(
    state: LearnState,
    onIntent: (LearnIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SortScaffold(
        modifier = modifier,
        topBar = {
            SortTopBar(
                title = "Learn",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        LearnContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.padding(padding)
        )
    }
}

// ステートレスな Content (Preview 可能)
@Composable
fun LearnContent(
    state: LearnState,
    onIntent: (LearnIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> LoadingIndicator(modifier)
        state.hasError -> ErrorMessage(
            message = state.errorMessage,
            onRetry = { onIntent(LearnIntent.Refresh) },
            modifier = modifier
        )
        else -> AlgorithmList(
            algorithms = state.algorithms,
            onSelect = { onIntent(LearnIntent.SelectAlgorithm(it)) },
            modifier = modifier
        )
    }
}
```

### ルール
- Screen はステートレス
- DI は Destination で行う
- Content を分離 (ステートレス)


---

## Step 5: Navigation を追加

**DI と State 収集は Destination で行います。**

```kotlin
// {Name}Destination.kt (navigation モジュール内)

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.learnDestination(
    onBackClick: () -> Unit
) {
    composable<Screen.Learn> {
        val viewModel: LearnViewModel = koinViewModel()  // DI はここで
        val state by viewModel.state.collectAsState()

        LearnScreen(
            state = state,
            onIntent = viewModel::send,  // メソッド参照形式
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToLearn() {
    navigate(Screen.Learn)
}
```


### Screen.kt に追加

```kotlin
// navigation/Screen.kt
@Serializable
sealed class Screen {
    // 既存...
    
    @Serializable
    data object Learn : Screen()  // 追加
}
```

### AppNavigation.kt に追加

```kotlin
// AppNavigation.kt の NavHost 内
learnDestination(
    onBackClick = { navController.popBackStack() }
)
```

---

## Step 6: DI を登録

```kotlin
// {Name}FeatureModule.kt

val learnFeatureModule = module {
    viewModel { LearnViewModel(get()) }
}
```

### StartKoin に追加

```kotlin
startKoin {
    modules(
        // 既存...
        learnFeatureModule  // 追加
    )
}
```

---

## Step 7: テストを作成

```kotlin
// {Name}ViewModelTest.kt

class LearnViewModelTest {
    private lateinit var viewModel: LearnViewModel
    private val mockUseCase = mockk<GetAlgorithmsUseCase>()

    @BeforeTest
    fun setup() {
        coEvery { mockUseCase.execute() } returns listOf(...)
        viewModel = LearnViewModel(mockUseCase)
    }

    @Test
    fun `GIVEN initial state WHEN LoadAlgorithms THEN updates algorithms`() = runTest {
        viewModel.state.test {
            val initial = awaitItem()
            assertTrue(initial.algorithms.isEmpty())
            
            viewModel.send(LearnIntent.LoadAlgorithms)
            
            val loading = awaitItem()
            assertTrue(loading.isLoading)
            
            val loaded = awaitItem()
            assertFalse(loaded.isLoading)
            assertTrue(loaded.algorithms.isNotEmpty())
        }
    }
}
```

---

## チェックリスト

- [ ] `{Name}State` を定義 (data class + UiState)
- [ ] `{Name}Intent` を定義 (sealed class + Intent)
- [ ] `{Name}ViewModel` を作成 (BaseViewModel 継承)
- [ ] `{Name}Screen.kt` を作成
- [ ] `{Name}Content` を分離 (ステートレス)
- [ ] `Screen` に route を追加 (@Serializable)
- [ ] `{Name}Navigation.kt` を作成
- [ ] `AppNavigation` に destination 追加
- [ ] `{Name}FeatureModule.kt` を作成
- [ ] StartKoin にモジュール追加
- [ ] `{Name}ViewModelTest` を作成
- [ ] KDoc を追加
- [ ] Preview を追加

---

## ✅ 検証コマンド

追加が完了したら、以下のコマンドで動作確認を行ってください：

```bash
# ビルド確認
./gradlew :presentation:feature:{name}:build

# ViewModel テスト
./gradlew :presentation:feature:{name}:test --tests "*{Name}ViewModelTest*"

# デスクトップアプリで画面遷移確認
./gradlew :composeApp:run
```

> [!TIP]
> ナビゲーションエラーが発生する場合は、`Screen` に `@Serializable` が付いているか確認してください。

---

## 参考

詳細が必要な場合は、[reference/](../reference/) 内のガイドを参照してください。
