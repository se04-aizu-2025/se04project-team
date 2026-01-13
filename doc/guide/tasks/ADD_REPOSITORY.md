# Repository を追加する

このガイドでは、新しい Repository を追加するために必要なすべての手順を説明します。

---

## 概要

Repository はデータアクセスを抽象化するパターンです:

```
domain/repository/       # インターフェース (Domain層)
data/repository/         # 実装 (Data層)
```

---

## Step 1: インターフェースを定義 (Domain層)

```kotlin
// domain/repository/{Name}Repository.kt

/**
 * クイズ履歴のデータアクセスを担当するリポジトリ。
 */
interface HistoryRepository {
    
    /**
     * クイズ結果を保存する。
     */
    suspend fun saveQuizResult(result: QuizResult)
    
    /**
     * 直近の結果を取得する。
     *
     * @param limit 取得件数
     * @return クイズ結果のリスト
     */
    suspend fun getRecentResults(limit: Int): List<QuizResult>
    
    /**
     * ハイスコアを取得する。
     */
    suspend fun getHighScore(): Int
    
    /**
     * 履歴をクリアする。
     */
    suspend fun clearHistory()
}
```

### ルール

| ルール | 詳細 |
|--------|------|
| **Domain層に配置** | ビジネスロジックから参照 |
| **interface で定義** | 実装と分離 |
| **suspend 関数** | I/O操作は非同期 |
| **KDoc 必須** | 各メソッドに説明 |

---

## Step 2: 実装クラスを作成 (Data層)

```kotlin
// data/repository/{Name}RepositoryImpl.kt

class HistoryRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : HistoryRepository {

    override suspend fun saveQuizResult(result: QuizResult) {
        dataStore.edit { prefs ->
            val existing = prefs[HISTORY_KEY]?.let { 
                Json.decodeFromString<List<QuizResult>>(it) 
            } ?: emptyList()
            
            val updated = (listOf(result) + existing).take(MAX_HISTORY)
            prefs[HISTORY_KEY] = Json.encodeToString(updated)
        }
    }

    override suspend fun getRecentResults(limit: Int): List<QuizResult> {
        return dataStore.data
            .map { prefs ->
                prefs[HISTORY_KEY]?.let {
                    Json.decodeFromString<List<QuizResult>>(it).take(limit)
                } ?: emptyList()
            }
            .first()
    }

    override suspend fun getHighScore(): Int {
        return getRecentResults(Int.MAX_VALUE)
            .maxOfOrNull { it.score } ?: 0
    }

    override suspend fun clearHistory() {
        dataStore.edit { prefs ->
            prefs.remove(HISTORY_KEY)
        }
    }

    companion object {
        private val HISTORY_KEY = stringPreferencesKey("quiz_history")
        private const val MAX_HISTORY = 100
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
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }  // 追加
}
```

### ルール

- **インターフェースで bind**: `single<HistoryRepository> { HistoryRepositoryImpl(get()) }`
- テスト時にモック差し替え可能

---

## Step 4: UseCase から使用

```kotlin
// domain/usecase/SaveQuizResultUseCase.kt

class SaveQuizResultUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend fun execute(result: QuizResult) {
        historyRepository.saveQuizResult(result)
    }
}
```

---

## Step 5: テストを作成

```kotlin
class HistoryRepositoryImplTest {
    private lateinit var repository: HistoryRepository
    private lateinit var testDataStore: DataStore<Preferences>

    @BeforeTest
    fun setup() {
        testDataStore = PreferenceDataStoreFactory.create { ... }
        repository = HistoryRepositoryImpl(testDataStore)
    }

    @Test
    fun `GIVEN result WHEN save THEN can retrieve`() = runTest {
        // Given
        val result = QuizResult(score = 100, totalQuestions = 10)
        
        // When
        repository.saveQuizResult(result)
        
        // Then
        val recent = repository.getRecentResults(10)
        assertEquals(1, recent.size)
        assertEquals(100, recent.first().score)
    }
}
```

---

## 命名規則

| 種類 | 規則 | 例 |
|------|------|-----|
| **インターフェース** | `{Name}Repository` | `HistoryRepository` |
| **実装** | `{Name}RepositoryImpl` | `HistoryRepositoryImpl` |
| **ファイル (Domain)** | `domain/repository/{Name}Repository.kt` | |
| **ファイル (Data)** | `data/repository/{Name}RepositoryImpl.kt` | |

---

## チェックリスト

- [ ] Domain層に interface を作成
- [ ] Data層に Impl を作成
- [ ] 全メソッド `suspend` 関数
- [ ] Koin にインターフェースで bind
- [ ] UseCase から使用
- [ ] KDoc を追加
- [ ] テストを作成

---

## 参考

- [reference/DEPENDENCY_INJECTION.md](../reference/DEPENDENCY_INJECTION.md)
- [Android Repository Pattern](https://developer.android.com/topic/architecture/data-layer)
