# 依存性注入 (Koin) ガイド

このガイドでは、Koin を使った依存性注入の規則を定義します。

---

## モジュール構成

```
di/
├── DomainModule.kt     # Domain 層の DI
├── DataModule.kt       # Data 層の DI
└── PresentationModule.kt # Presentation 層の DI (feature ごと)
```

---

## モジュール定義

### Domain Module

```kotlin
val domainModule = module {
    // UseCase は single (ステートレス)
    single { ExecuteSortUseCase() }
    single { GenerateArrayUseCase(get()) }
    
    // Factory は single
    single { SortAlgorithmFactory }
}
```

### Data Module

```kotlin
val dataModule = module {
    // インターフェースで bind
    single<ArrayGenerator> { ArrayGeneratorImpl() }
    
    // 将来: Repository
    // single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}
```

### Feature Module

```kotlin
// 各 feature で 1 モジュール
val sortFeatureModule = module {
    viewModel { SortViewModel(get(), get()) }
}

val homeFeatureModule = module {
    viewModel { HomeViewModel() }
}

val settingsFeatureModule = module {
    viewModel { SettingsViewModel(get()) }
}
```

---

## スコープ

| スコープ | 用途 | 例 |
|----------|------|-----|
| `single` | シングルトン | UseCase, Repository |
| `factory` | 毎回新規インスタンス | ステートフルなオブジェクト |
| `viewModel` | ViewModel 専用 | `viewModel { MyViewModel(get()) }` |

### ルール

```kotlin
// ✅ ステートレス → single
single { ExecuteSortUseCase() }

// ✅ ステートフル → factory
factory { SomeStatefulHelper() }

// ✅ ViewModel → viewModel
viewModel { SortViewModel(get(), get()) }
```

---

## 依存の取得

### get() の使い方

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
// Screen で koinViewModel() を使用
@OptIn(KoinExperimentalAPI::class)
@Composable
fun SortScreen(
    viewModel: SortViewModel = koinViewModel()  // ✅
) {
    // ...
}
```

---

## インターフェース bind

### 必須パターン

```kotlin
// ✅ インターフェースで bind
single<ArrayGenerator> { ArrayGeneratorImpl() }

// ❌ 禁止 - 具象型で bind
single { ArrayGeneratorImpl() }  // テスト時に差し替え困難
```

### 理由

- テスト時にモック/スタブに差し替え可能
- 依存の向きが抽象に向く (DIP)

---

## モジュール統合

### Application での登録

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
            compareFeatureModule
        )
    }
}
```

---

## テスト

### モックの差し替え

```kotlin
@Test
fun `test with mock`() {
    val mockGenerator = mockk<ArrayGenerator>()
    every { mockGenerator.generate(any(), any()) } returns listOf(1, 2, 3)
    
    // テスト用モジュールで差し替え
    loadKoinModules(
        module {
            single<ArrayGenerator> { mockGenerator }
        }
    )
    
    // テスト実行
    // ...
}
```

---

## 禁止事項

```kotlin
// ❌ 禁止 - 循環依存
val badModule = module {
    single { A(get<B>()) }
    single { B(get<A>()) }  // 循環
}

// ❌ 禁止 - Composable 内で get()
@Composable
fun BadScreen() {
    val useCase = get<MyUseCase>()  // ❌ koinViewModel() を使う
}

// ❌ 禁止 - Context を必要以上に注入
single { MyClass(androidContext()) }  // 本当に必要か確認
```

---

## チェックリスト

新しい依存を追加する場合:

- [ ] 適切なモジュールに追加 (Domain/Data/Feature)
- [ ] スコープを選択 (`single` / `factory` / `viewModel`)
- [ ] インターフェースで bind
- [ ] 依存は `get()` で取得
- [ ] 循環依存がないか確認

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Koin** | [Koin Official](https://insert-koin.io/) |
| **Koin Compose** | [Koin Compose](https://insert-koin.io/docs/reference/koin-compose/compose/) |
| **Android DI** | [Android DI Guide](https://developer.android.com/training/dependency-injection) |
