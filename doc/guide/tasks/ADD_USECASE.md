# UseCase を追加する

このガイドでは、新しい UseCase を追加するために必要なすべての手順を説明します。

---

## 概要

UseCase は**ビジネスロジックの単位**であり、Domain層に配置します。

```
domain/
└── src/commonMain/kotlin/dotnet/sort/usecase/
    └── {Verb}{Noun}UseCase.kt
```

---

## Step 1: UseCase クラスを作成

```kotlin
// domain/usecase/{Verb}{Noun}UseCase.kt

/**
 * アルゴリズムの詳細情報を取得する。
 */
class GetAlgorithmDetailsUseCase {
    
    /**
     * 指定されたアルゴリズムの詳細情報を返す。
     *
     * @param type アルゴリズムの種類
     * @return アルゴリズムの詳細情報
     */
    fun execute(type: SortType): AlgorithmDetails {
        return AlgorithmDetails(
            type = type,
            displayName = type.displayName,
            timeComplexity = type.timeComplexity,
            spaceComplexity = type.spaceComplexity,
            description = getDescription(type),
            useCases = getUseCases(type)
        )
    }
    
    private fun getDescription(type: SortType): String {
        return when (type) {
            SortType.BUBBLE -> "隣接する要素を比較・交換してソートする単純なアルゴリズム"
            SortType.QUICK -> "分割統治法を用いた高速なソートアルゴリズム"
            // ...
        }
    }
    
    private fun getUseCases(type: SortType): List<String> {
        return when (type) {
            SortType.BUBBLE -> listOf("小さい配列", "ほぼソート済みの配列")
            SortType.QUICK -> listOf("大きい配列", "メモリ制約がある場合")
            // ...
        }
    }
}
```

---

## Step 2: 命名規則

### パターン

```
{動詞}{対象}UseCase
```

| 動詞 | 用途 | 例 |
|------|------|-----|
| **Execute** | 処理を実行 | `ExecuteSortUseCase` |
| **Get** | データを取得 | `GetAlgorithmDetailsUseCase` |
| **Generate** | データを生成 | `GenerateArrayUseCase` |
| **Validate** | 検証 | `ValidateInputUseCase` |
| **Save** | 保存 | `SaveSettingsUseCase` |
| **Load** | 読み込み | `LoadHistoryUseCase` |
| **Calculate** | 計算 | `CalculateMetricsUseCase` |
| **Compare** | 比較 | `CompareAlgorithmsUseCase` |

---

## Step 3: 依存の注入 (必要な場合)

```kotlin
// 依存がある場合
class SaveQuizResultUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend fun execute(result: QuizResult) {
        historyRepository.saveQuizResult(result)
    }
}

// 依存がない場合
class ValidateArraySizeUseCase {
    fun execute(size: Int): ValidationResult {
        // ...
    }
}
```

---

## Step 4: Koin に登録

```kotlin
// di/DomainModule.kt

val domainModule = module {
    // 既存...
    single { ExecuteSortUseCase() }
    single { GenerateArrayUseCase(get()) }
    
    // 新規追加
    single { GetAlgorithmDetailsUseCase() }
    single { SaveQuizResultUseCase(get()) }
}
```

---

## Step 5: ViewModel から使用

```kotlin
class LearnViewModel(
    private val getAlgorithmDetailsUseCase: GetAlgorithmDetailsUseCase
) : BaseViewModel<LearnState, LearnIntent>(LearnState()) {

    private fun loadAlgorithmDetails(type: SortType) {
        val details = getAlgorithmDetailsUseCase.execute(type)
        updateState { copy(algorithmDetails = details) }
    }
}
```

---

## Step 6: テストを作成

```kotlin
class GetAlgorithmDetailsUseCaseTest {
    private val useCase = GetAlgorithmDetailsUseCase()
    
    @Test
    fun `GIVEN bubble sort WHEN execute THEN returns correct details`() {
        // Given
        val type = SortType.BUBBLE
        
        // When
        val result = useCase.execute(type)
        
        // Then
        assertEquals("Bubble Sort", result.displayName)
        assertEquals("O(n²)", result.timeComplexity)
    }
    
    @Test
    fun `GIVEN all sort types WHEN execute THEN returns valid details for each`() {
        SortType.entries.forEach { type ->
            val result = useCase.execute(type)
            assertNotNull(result.description)
            assertTrue(result.useCases.isNotEmpty())
        }
    }
}
```

---

## 単一責任の原則

### ルール

**1 UseCase = 1 責務**

```kotlin
// ✅ 単一責任
class ExecuteSortUseCase { ... }
class GenerateArrayUseCase { ... }
class ValidateInputUseCase { ... }

// ❌ 禁止 - 複数責務
class SortUseCase {
    fun executeSort() { }
    fun generateArray() { }  // ❌ 別の UseCase に
}
```

---

## メソッド名

| パターン | メソッド名 |
|----------|------------|
| **標準** | `execute()` |
| **operator** | `operator fun invoke()` → `useCase()` |

```kotlin
// operator invoke を使う場合
class GetAlgorithmDetailsUseCase {
    operator fun invoke(type: SortType): AlgorithmDetails {
        // ...
    }
}

// 呼び出し
val details = getAlgorithmDetailsUseCase(SortType.BUBBLE)
```

---

## チェックリスト

- [ ] クラス名: `{Verb}{Noun}UseCase`
- [ ] ファイル配置: `domain/usecase/`
- [ ] 単一責任を確認
- [ ] `execute()` または `operator invoke()` を実装
- [ ] 依存は constructor injection
- [ ] Koin に登録 (single)
- [ ] KDoc を追加
- [ ] テストを作成

---

## 参考

詳細: [reference/USECASE.md](../reference/USECASE.md)
