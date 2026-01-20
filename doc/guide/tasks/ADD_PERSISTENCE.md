# データ永続化を追加する

---
title: データ永続化の追加
version: 1.1.0
last_updated: 2026-01-17
maintainer: Team
---

# データ永続化の追加

このガイドでは、データの永続化機能を追加するために必要なすべての手順を説明します。

---

## 概要

データ永続化を追加するには:

1. Repository インターフェースを Domain 層に定義
2. Repository 実装を Data 層に作成
3. Koin に登録
4. UseCase から使用

---

## 概念: DDD + Clean + Hex + Feature分割

- **DDD**: ドメインモデル・Repositoryインターフェース・UseCaseを中心に設計。
- **Clean**: 依存は内側（Domain）へ向け、外部詳細はDataで吸収。
- **Hex**: DomainはPort（Repository）、DataはAdapter（実装）として分離。
- **Feature分割**: Data層はFeature単位で `adapter/dataSource/mapper/policy` を持つ。

---

## Step 1: Port (Repository) を定義 (Domain 層)

```kotlin
// 📁 domain/src/commonMain/kotlin/dotnet/sort/repository/AlgorithmHistoryRepository.kt (検証済み: 2026-01-17)
interface AlgorithmHistoryRepository {
    suspend fun recordEvent(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    )

    fun observeRecentEvents(limit: Int): Flow<List<AlgorithmHistoryEntry>>
}
```

---

## Step 2: SQLDelight スキーマを定義 (Data 層)

```sql
-- 📁 data/src/commonMain/sqldelight/dotnet/sort/data/algorithm_history.sq (検証済み: 2026-01-17)
CREATE TABLE algorithm_history(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    algorithm_type TEXT,
    event_type TEXT NOT NULL,
    metadata TEXT,
    created_at_millis INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS algorithm_history_created_at ON algorithm_history(created_at_millis);

insertEvent:
INSERT INTO algorithm_history(
    algorithm_type,
    event_type,
    metadata,
    created_at_millis
) VALUES (?, ?, ?, ?);

selectRecent:
SELECT id,
       algorithm_type,
       event_type,
       metadata,
       created_at_millis
FROM algorithm_history
ORDER BY created_at_millis DESC
LIMIT ?;
```

```sql
-- 📁 data/src/commonMain/sqldelight/dotnet/sort/data/quiz_score.sq (検証済み: 2026-01-17)
CREATE TABLE quiz_score(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    correct_count INTEGER NOT NULL,
    incorrect_count INTEGER NOT NULL,
    longest_streak INTEGER NOT NULL,
    score INTEGER NOT NULL,
    duration_millis INTEGER NOT NULL,
    difficulty TEXT NOT NULL,
    algorithm_type TEXT NOT NULL,
    quiz_version TEXT NOT NULL,
    created_at_millis INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS quiz_score_created_at ON quiz_score(created_at_millis);

insertQuizScore:
INSERT INTO quiz_score(
    correct_count,
    incorrect_count,
    longest_streak,
    score,
    duration_millis,
    difficulty,
    algorithm_type,
    quiz_version,
    created_at_millis
) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);

selectQuizScores:
SELECT id,
       correct_count,
       incorrect_count,
       longest_streak,
       score,
       duration_millis,
       difficulty,
       algorithm_type,
       quiz_version,
       created_at_millis
FROM quiz_score
ORDER BY created_at_millis DESC
LIMIT ?;
```

---

## Step 3: Adapter を実装 (Data 層)

```kotlin
// 📁 data/src/commonMain/kotlin/dotnet/sort/repository/AlgorithmHistoryRepositoryImpl.kt (検証済み: 2026-01-17)
@Single
class AlgorithmHistoryRepositoryImpl(
    private val databaseProvider: DnsortDatabaseProvider,
) : AlgorithmHistoryRepository {
    override suspend fun recordEvent(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String?,
    ) {
        databaseProvider.insertHistory(
            algorithmType = algorithmType,
            eventType = eventType,
            metadata = metadata,
            createdAtMillis = Clock.System.now().toEpochMilliseconds(),
        )
    }

    override fun observeRecentEvents(limit: Int): Flow<List<AlgorithmHistoryEntry>> =
        databaseProvider.observeRecent(limit)
}
```

```kotlin
// 📁 data/src/commonMain/kotlin/dotnet/sort/repository/QuizScoreRepositoryImpl.kt (検証済み: 2026-01-17)
@Single
class QuizScoreRepositoryImpl(
    private val databaseProvider: DnsortDatabaseProvider,
) : QuizScoreRepository {
    override suspend fun recordScore(
        score: QuizScore,
    ) {
        databaseProvider.insertQuizScore(
            score = score,
        )
    }

    override fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        databaseProvider.observeRecentScores(limit)
}
```

---

## Step 4: Database Provider を実装 (Data 層)

```kotlin
// 📁 data/src/commonMain/kotlin/dotnet/sort/database/DnsortDatabaseProvider.kt (検証済み: 2026-01-17)
@Single
class DnsortDatabaseProvider(
    driverFactory: DatabaseDriverFactory,
) {
    private val driver = driverFactory.createDriver()
    private val database = DnsortDatabase(driver)
    private val historyQueries = database.algorithm_historyQueries
    private val quizQueries = database.quiz_scoreQueries
    private val databaseReady = CompletableDeferred<Unit>()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            DnsortDatabase.Schema.awaitCreate(driver)
            databaseReady.complete(Unit)
        }
    }

    private suspend fun ensureDatabaseReady() {
        databaseReady.await()
    }

    suspend fun insertHistory(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String?,
        createdAtMillis: Long,
    ) {
        ensureDatabaseReady()
        historyQueries.insertEvent(
            algorithm_type = algorithmType?.toDbValue(),
            event_type = eventType.toDbValue(),
            metadata = metadata,
            created_at_millis = createdAtMillis,
        )
    }

    suspend fun insertQuizScore(score: QuizScore) {
        ensureDatabaseReady()
        quizQueries.insertQuizScore(
            correct_count = score.correctCount,
            incorrect_count = score.incorrectCount,
            longest_streak = score.longestStreak,
            score = score.score,
            duration_millis = score.durationMillis,
            difficulty = score.difficulty,
            algorithm_type = score.algorithmType.toDbValue(),
            quiz_version = score.quizVersion,
            created_at_millis = score.createdAtMillis,
        )
    }

    fun observeRecent(limit: Int): Flow<List<AlgorithmHistoryEntry>> =
        flow {
            ensureDatabaseReady()
            emitAll(
                historyQueries
                    .selectRecent(limit.toLong())
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { rows ->
                        rows.map { row ->
                            AlgorithmHistoryEntry(
                                id = row.id,
                                algorithmType = row.algorithm_type?.let { sortTypeFromDb(it) },
                                eventType = historyEventTypeFromDb(row.event_type),
                                createdAtMillis = row.created_at_millis,
                                metadata = row.metadata,
                            )
                        }
                    },
            )
        }

    fun observeRecentScores(limit: Int): Flow<List<QuizScore>> =
        flow {
            ensureDatabaseReady()
            emitAll(
                quizQueries
                    .selectQuizScores(limit.toLong())
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { rows ->
                        rows.map { row ->
                            QuizScore(
                                id = row.id,
                                correctCount = row.correct_count,
                                incorrectCount = row.incorrect_count,
                                longestStreak = row.longest_streak,
                                score = row.score,
                                durationMillis = row.duration_millis,
                                difficulty = row.difficulty,
                                algorithmType = sortTypeFromDb(row.algorithm_type),
                                quizVersion = row.quiz_version,
                                createdAtMillis = row.created_at_millis,
                            )
                        }
                    },
            )
        }
}
```

---

## Step 5: UseCase から使用 (Domain 層)

```kotlin
// 📁 domain/src/commonMain/kotlin/dotnet/sort/usecase/RecordHistoryEventUseCase.kt (検証済み: 2026-01-17)
@Single
class RecordHistoryEventUseCase(
    private val historyRepository: AlgorithmHistoryRepository,
) {
    suspend operator fun invoke(
        algorithmType: SortType?,
        eventType: HistoryEventType,
        metadata: String? = null,
    ) {
        historyRepository.recordEvent(algorithmType, eventType, metadata)
    }
}
```

```kotlin
// 📁 domain/src/commonMain/kotlin/dotnet/sort/usecase/RecordQuizScoreUseCase.kt (検証済み: 2026-01-17)
@Single
class RecordQuizScoreUseCase(
    private val quizScoreRepository: QuizScoreRepository,
) {
    suspend operator fun invoke(score: QuizScore) {
        quizScoreRepository.recordScore(score)
    }
}
```

---

## マイグレーション運用

- SQLDelight の `Schema` バージョン更新を必須とする
- 各テーブルの変更には Migration SQL を追加する

---

## チェックリスト

- [ ] Port (Repository) は Domain 側にある
- [ ] Data 側は Adapter (実装) と Provider に分離されている
- [ ] Feature単位で Adapter/DataSource/Mapper/Policy が整理されている
- [ ] SQLDelight のスキーマが `sqldelight/` に集約されている
- [ ] UseCase から Repository を経由して利用している
- [ ] マイグレーションを追加した

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
