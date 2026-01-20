# Feature Module を追加する

このガイドでは、新しい Feature Module を追加するために必要なすべての手順を説明します。

---

## 概要

Feature Module は画面単位の Gradle モジュールです:

```
presentation/feature/
├── home/       # ホーム画面
├── sort/       # ソート画面
├── learn/      # 学習画面
├── compare/    # 比較画面
├── settings/   # 設定画面
└── quiz/       # 🆕 クイズ画面 (新規追加)
```

---

## Step 1: ディレクトリ構造を作成

```
presentation/feature/{name}/
├── build.gradle.kts
└── src/commonMain/kotlin/dotnet/sort/presentation/feature/{name}/
    ├── {Name}Screen.kt
    ├── {Name}ViewModel.kt
    ├── {Name}Intent.kt
    └── di/
        └── {Name}FeatureModule.kt
```

---

## Step 2: build.gradle.kts を作成

```kotlin
// presentation/feature/{name}/build.gradle.kts

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            
            implementation(compose.ui)
            
            // Project modules
            implementation(projects.domain)
            implementation(projects.presentation.common)
            implementation(projects.presentation.designsystem) // UIはここから取得
            
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
        }
    }
}
```

---

## Step 3: settings.gradle.kts に追加

```kotlin
// settings.gradle.kts

include(":presentation:feature:home")
include(":presentation:feature:sort")
include(":presentation:feature:learn")
include(":presentation:feature:compare")
include(":presentation:feature:settings")
include(":presentation:feature:{name}")  // 追加
```

---

## Step 4: State / Intent を定義

```kotlin
// {Name}Intent.kt

sealed class QuizIntent : Intent {
    data object StartQuiz : QuizIntent()
    data object NextQuestion : QuizIntent()
    data class SubmitAnswer(val answer: Int) : QuizIntent()
    data object FinishQuiz : QuizIntent()
}
```

```kotlin
// {Name}ViewModel.kt (State 部分)

data class QuizState(
    val currentQuestion: Int = 0,
    val totalQuestions: Int = 10,
    val score: Int = 0,
    val isLoading: Boolean = false,
    val isFinished: Boolean = false
) : UiState
```

---

## Step 5: ViewModel を実装

```kotlin
// {Name}ViewModel.kt

class QuizViewModel(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase
) : BaseViewModel<QuizState, QuizIntent>(QuizState()) {

    override fun send(intent: QuizIntent) {
        when (intent) {
            QuizIntent.StartQuiz -> startQuiz()
            QuizIntent.NextQuestion -> nextQuestion()
            is QuizIntent.SubmitAnswer -> submitAnswer(intent.answer)
            QuizIntent.FinishQuiz -> finishQuiz()
        }
    }
    
    private fun startQuiz() { /* ... */ }
    private fun nextQuestion() { /* ... */ }
    private fun submitAnswer(answer: Int) { /* ... */ }
    private fun finishQuiz() { /* ... */ }
}
```

---

## Step 6: Screen を実装

```kotlin
// {Name}Screen.kt

@OptIn(KoinExperimentalAPI::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    
    SortScaffold(
        modifier = modifier,
        topBar = {
            SortTopBar(
                title = "Quiz",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        QuizContent(
            state = state,
            onIntent = { viewModel.send(it) },
            modifier = Modifier.padding(padding)
        )
    }
}
```

---

## Step 7: DI Module を作成

```kotlin
// di/{Name}FeatureModule.kt

val quizFeatureModule = module {
    viewModel { QuizViewModel(get()) }
}
```

---

## Step 8: Navigation を追加

```kotlin
// {Name}Navigation.kt

fun NavGraphBuilder.quizDestination(
    onBackClick: () -> Unit
) {
    composable<Screen.Quiz> {
        QuizScreen(onBackClick = onBackClick)
    }
}

fun NavController.navigateToQuiz() {
    navigate(Screen.Quiz)
}
```

### Screen.kt に追加

```kotlin
@Serializable
sealed class Screen {
    // 既存...
    @Serializable data object Quiz : Screen()  // 追加
}
```

### AppNavigation に追加

```kotlin
quizDestination(
    onBackClick = { navController.popBackStack() }
)
```

---

## Step 9: StartKoin に登録

```kotlin
startKoin {
    modules(
        // 既存...
        quizFeatureModule  // 追加
    )
}
```

---

## Step 10: navigation モジュールに依存追加

```kotlin
// presentation/navigation/build.gradle.kts

commonMain.dependencies {
    // 既存...
    implementation(projects.presentation.feature.quiz)  // 追加
}
```

---

## チェックリスト

- [ ] ディレクトリ `presentation/feature/{name}/` 作成
- [ ] `build.gradle.kts` 作成
- [ ] `settings.gradle.kts` に include 追加
- [ ] `{Name}State`, `{Name}Intent` 作成
- [ ] `{Name}ViewModel` 作成
- [ ] `{Name}Screen.kt` 作成
- [ ] `{Name}FeatureModule.kt` 作成
- [ ] `{Name}Navigation.kt` 作成
- [ ] `Screen` に route 追加
- [ ] `AppNavigation` に destination 追加
- [ ] `StartKoin` にモジュール追加
- [ ] `navigation/build.gradle.kts` に依存追加
- [ ] Gradle Sync

---

## 参考

- [tasks/ADD_SCREEN.md](./ADD_SCREEN.md)
- [tasks/ADD_KOIN_MODULE.md](./ADD_KOIN_MODULE.md)
