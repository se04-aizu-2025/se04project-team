# Koin モジュールを追加する

このガイドでは、新しい Koin DI モジュールを追加するために必要なすべての手順を説明します。

---

## 概要

Koin モジュールは依存性注入の設定単位です:

```
di/
├── DomainModule.kt       # UseCase
├── DataModule.kt         # Repository, Generator
└── PresentationModule.kt # ViewModel (feature ごと)
```

---

## Step 1: モジュール種類を決定

| 種類 | 用途 | 例 |
|------|------|-----|
| **Domain Module** | UseCase | `ExecuteSortUseCase` |
| **Data Module** | Repository, Generator | `SettingsRepository` |
| **Feature Module** | ViewModel (機能別) | `SortViewModel` |

---

## Step 2: モジュールを作成

### Domain Module

```kotlin
// di/DomainModule.kt

val domainModule = module {
    // UseCase は single (ステートレス)
    single { ExecuteSortUseCase() }
    single { GenerateArrayUseCase(get()) }
    single { GetAlgorithmDetailsUseCase() }
    
    // Factory も single
    single { SortAlgorithmFactory }
}
```

### Data Module

```kotlin
// di/DataModule.kt

val dataModule = module {
    // Generator (インターフェースで bind)
    single<ArrayGenerator> { ArrayGeneratorImpl() }
    
    // Repository
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
}
```

### Feature Module

```kotlin
// presentation/feature/sort/di/SortFeatureModule.kt

val sortFeatureModule = module {
    viewModel { SortViewModel(get(), get()) }
}

// presentation/feature/learn/di/LearnFeatureModule.kt

val learnFeatureModule = module {
    viewModel { LearnViewModel(get()) }
}

// presentation/feature/quiz/di/QuizFeatureModule.kt

val quizFeatureModule = module {
    viewModel { QuizViewModel(get(), get()) }
}
```

---

## Step 3: スコープを選択

| スコープ | 用途 | 例 |
|----------|------|-----|
| `single` | シングルトン | UseCase, Repository |
| `factory` | 毎回新規インスタンス | ステートフルなヘルパー |
| `viewModel` | ViewModel 専用 | `SortViewModel` |

```kotlin
// ✅ 正しい使い分け
single { ExecuteSortUseCase() }           // ステートレス
factory { SomeStatefulHelper() }          // ステートフル
viewModel { SortViewModel(get(), get()) } // ViewModel
```

---

## Step 4: StartKoin に登録

```kotlin
// Application.kt または main.kt

fun initKoin() {
    startKoin {
        modules(
            // Domain
            domainModule,
            
            // Data
            dataModule,
            
            // Presentation (feature ごと)
            sortFeatureModule,
            homeFeatureModule,
            settingsFeatureModule,
            learnFeatureModule,
            compareFeatureModule,
            quizFeatureModule  // 追加
        )
    }
}
```

---

## Step 5: 依存の取得

### get() の使用

```kotlin
val myModule = module {
    // 依存を get() で取得
    single { MyUseCase(get(), get()) }
    
    // 名前付き依存
    single(named("api")) { ApiClient() }
    single { MyRepo(get(named("api"))) }
}
```

### ViewModel での取得

```kotlin
@OptIn(KoinExperimentalAPI::class)
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel()
) {
    // ...
}
```

---

## 新規 Feature Module の追加

### 1. ファイル作成

```kotlin
// presentation/feature/{name}/di/{Name}FeatureModule.kt

val {name}FeatureModule = module {
    viewModel { {Name}ViewModel(get()) }
}
```

### 2. StartKoin に追加

```kotlin
startKoin {
    modules(
        // 既存...
        {name}FeatureModule  // 追加
    )
}
```

---

## インターフェースで bind

```kotlin
// ✅ 推奨 - インターフェースで bind
single<ArrayGenerator> { ArrayGeneratorImpl() }

// ❌ 非推奨 - 具象型で bind
single { ArrayGeneratorImpl() }  // テスト時に差し替え困難
```

---

## チェックリスト

- [ ] モジュール種類を決定 (Domain/Data/Feature)
- [ ] 適切なスコープを選択 (`single`/`factory`/`viewModel`)
- [ ] インターフェースで bind
- [ ] 依存は `get()` で取得
- [ ] StartKoin に登録
- [ ] 循環依存がないか確認

---

## 参考

- [reference/DEPENDENCY_INJECTION.md](../reference/DEPENDENCY_INJECTION.md)
- [Koin Documentation](https://insert-koin.io/)
