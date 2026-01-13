# UseCase ガイド

このガイドでは、UseCase の設計と実装規則を定義します。

---

## 概念

UseCase は**ビジネスロジックの単位**であり、Presentation層から呼び出される。

```
ViewModel → UseCase → Algorithm / Repository
```

---

## 構造

### 単一責任

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
    fun validateInput() { }  // ❌ 別の UseCase に
}
```

---

## 命名規則

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

## 実装パターン

### 基本形

```kotlin
/**
 * ソートアルゴリズムを実行する。
 */
class ExecuteSortUseCase {
    
    /**
     * 指定されたアルゴリズムでリストをソートする。
     *
     * @param type ソートアルゴリズムの種類
     * @param input ソート対象のリスト
     * @return ソート結果
     */
    fun execute(type: SortType, input: List<Int>): SortResult {
        val algorithm = SortAlgorithmFactory.create(type)
        return algorithm.sort(input)
    }
}
```

### 依存注入あり

```kotlin
class ExecuteSortUseCase(
    private val algorithmFactory: SortAlgorithmFactory  // 注入
) {
    fun execute(type: SortType, input: List<Int>): SortResult {
        val algorithm = algorithmFactory.create(type)
        return algorithm.sort(input)
    }
}
```

### suspend 関数 (非同期)

```kotlin
class LoadHistoryUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend fun execute(): List<HistoryItem> {
        return historyRepository.getAll()
    }
}
```

---

## メソッド名

### ルール

| パターン | メソッド名 | 例 |
|----------|------------|-----|
| **単一操作** | `execute()` | `useCase.execute(type, input)` |
| **パラメータなし** | `invoke()` (operator) | `useCase()` |
| **複数操作** | 動詞で命名 | `start()`, `stop()`, `reset()` |

### operator invoke

```kotlin
class ExecuteSortUseCase {
    // operator invoke で () で呼び出し可能
    operator fun invoke(type: SortType, input: List<Int>): SortResult {
        // ...
    }
}

// 使用側
val result = executeSortUseCase(SortType.BUBBLE, input)
```

---

## DI 登録

### Koin

```kotlin
val domainModule = module {
    // singleton
    single { ExecuteSortUseCase(get()) }
    
    // factory (毎回新規)
    factory { ValidateInputUseCase() }
}
```

### ルール

| 種類 | Koin スコープ |
|------|--------------|
| **ステートレス UseCase** | `single` |
| **ステートフル UseCase** | `factory` |

---

## 検証ロジック

### UseCase 内での検証

```kotlin
class ExecuteSortUseCase {
    fun execute(type: SortType, input: List<Int>): SortResult {
        // 早期検証
        require(input.isNotEmpty()) { "Input must not be empty" }
        require(input.size <= MAX_SIZE) { "Input size exceeds $MAX_SIZE" }
        
        val algorithm = SortAlgorithmFactory.create(type)
        return algorithm.sort(input)
    }
    
    companion object {
        private const val MAX_SIZE = 1000
    }
}
```

### 別 UseCase での検証

```kotlin
// 検証専用 UseCase
class ValidateInputUseCase {
    fun execute(input: List<Int>): ValidationResult {
        return when {
            input.isEmpty() -> ValidationResult.Error("Empty input")
            input.size > MAX_SIZE -> ValidationResult.Error("Too large")
            else -> ValidationResult.Valid
        }
    }
}

// 実行 UseCase は検証済みを前提
class ExecuteSortUseCase {
    fun execute(type: SortType, input: List<Int>): SortResult {
        // 検証は呼び出し側 (ViewModel) で行われている前提
        val algorithm = SortAlgorithmFactory.create(type)
        return algorithm.sort(input)
    }
}
```

---

## 禁止事項

```kotlin
// ❌ 禁止 - UI関連のロジック
class BadUseCase {
    fun execute(): String {
        return "表示用テキスト"  // ❌ UI フォーマットは Presentation層で
    }
}

// ❌ 禁止 - ViewModel への依存
class BadUseCase(
    private val viewModel: SortViewModel  // ❌ 逆方向の依存
) { }

// ❌ 禁止 - 複数責務
class BadUseCase {
    fun sortAndFormat() { }  // ❌ 分離する
}
```

---

## チェックリスト

新しい UseCase を追加する場合:

- [ ] 命名: `{動詞}{対象}UseCase`
- [ ] 単一責任を確認
- [ ] メソッド: `execute()` または `operator invoke()`
- [ ] 検証ロジックを含める (または別 UseCase)
- [ ] Koin モジュールに登録
- [ ] KDoc を追加
- [ ] テストを作成

---

## 参考リンク

| トピック | リンク |
|----------|--------|
| **Domain Layer** | [Android Domain Layer](https://developer.android.com/topic/architecture/domain-layer) |
| **Use Cases** | [Android Use Cases](https://developer.android.com/topic/architecture/domain-layer#use-cases) |
| **Clean Architecture** | [Uncle Bob - Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) |
