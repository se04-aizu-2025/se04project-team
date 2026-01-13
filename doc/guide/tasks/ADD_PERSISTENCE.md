# データ永続化を追加する

このガイドでは、データの永続化機能を追加するために必要なすべての手順を説明します。

---

## 概要

データ永続化を追加するには:

1. Repository インターフェースを Domain 層に定義
2. Repository 実装を Data 層に作成
3. Koin に登録
4. UseCase から使用

---

## Step 1: Repository インターフェースを定義 (Domain 層)

```kotlin
// domain/repository/SettingsRepository.kt

/**
 * アプリ設定の永続化を担当するリポジトリ。
 */
interface SettingsRepository {
    suspend fun getTheme(): Theme
    suspend fun setTheme(theme: Theme)
    suspend fun getLanguage(): Language
    suspend fun setLanguage(language: Language)
    suspend fun getAnimationSpeed(): Float
    suspend fun setAnimationSpeed(speed: Float)
}
```

```kotlin
// domain/repository/HistoryRepository.kt

/**
 * クイズ履歴の永続化を担当するリポジトリ。
 */
interface HistoryRepository {
    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getRecentResults(limit: Int): List<QuizResult>
    suspend fun getHighScore(): Int
    suspend fun clearHistory()
}
```

---

## Step 2: Repository 実装を作成 (Data 層)

```kotlin
// data/repository/SettingsRepositoryImpl.kt

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override suspend fun getTheme(): Theme {
        return dataStore.data
            .map { prefs -> 
                prefs[THEME_KEY]?.let { Theme.valueOf(it) } ?: Theme.SYSTEM 
            }
            .first()
    }

    override suspend fun setTheme(theme: Theme) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = theme.name
        }
    }

    override suspend fun getLanguage(): Language {
        return dataStore.data
            .map { prefs -> 
                prefs[LANGUAGE_KEY]?.let { Language.valueOf(it) } ?: Language.ENGLISH 
            }
            .first()
    }

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language.name
        }
    }

    override suspend fun getAnimationSpeed(): Float {
        return dataStore.data
            .map { prefs -> prefs[SPEED_KEY] ?: 1.0f }
            .first()
    }

    override suspend fun setAnimationSpeed(speed: Float) {
        dataStore.edit { prefs ->
            prefs[SPEED_KEY] = speed
        }
    }

    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val SPEED_KEY = floatPreferencesKey("animation_speed")
    }
}
```

---

## Step 3: Koin に登録

```kotlin
// di/DataModule.kt

val dataModule = module {
    // DataStore
    single { createDataStore() }
    
    // Repository (インターフェースで bind)
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
}

// プラットフォーム別 DataStore 生成
expect fun createDataStore(): DataStore<Preferences>
```

---

## Step 4: UseCase から使用

```kotlin
// domain/usecase/SaveSettingsUseCase.kt

class SaveSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(theme: Theme, language: Language, speed: Float) {
        settingsRepository.setTheme(theme)
        settingsRepository.setLanguage(language)
        settingsRepository.setAnimationSpeed(speed)
    }
}
```

---

## Step 5: ViewModel から使用

```kotlin
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<SettingsState, SettingsIntent>(SettingsState()) {

    init {
        loadSettings()
    }

    private fun loadSettings() {
        execute {
            val theme = settingsRepository.getTheme()
            val language = settingsRepository.getLanguage()
            val speed = settingsRepository.getAnimationSpeed()
            
            updateState { 
                copy(
                    theme = theme, 
                    language = language, 
                    animationSpeed = speed
                ) 
            }
        }
    }

    private fun saveTheme(theme: Theme) {
        execute {
            settingsRepository.setTheme(theme)
            updateState { copy(theme = theme) }
        }
    }
}
```

---

## データモデル

```kotlin
// domain/model/QuizResult.kt

data class QuizResult(
    val id: String = UUID.randomUUID().toString(),
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val timestamp: Long = System.currentTimeMillis()
)
```

---

## ルール

| ルール | 詳細 |
|--------|------|
| **インターフェースは Domain 層** | 抽象化 |
| **実装は Data 層** | 具象化 |
| **suspend 関数** | I/O 操作は非同期 |
| **インターフェースで bind** | テスト容易性 |

---

## チェックリスト

- [ ] Repository インターフェースを Domain 層に定義
- [ ] Repository 実装を Data 層に作成
- [ ] DataStore または同等の永続化を設定
- [ ] Koin モジュールに登録 (インターフェースで bind)
- [ ] UseCase から Repository を使用
- [ ] ViewModel から UseCase を呼び出し
- [ ] suspend 関数で I/O 操作
- [ ] テストを作成

---

## 参考

- [reference/DEPENDENCY_INJECTION.md](../reference/DEPENDENCY_INJECTION.md)
- [reference/ASYNC_FLOW.md](../reference/ASYNC_FLOW.md)
- [Android DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
