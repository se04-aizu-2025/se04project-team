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

## 概念: DDD + Clean + Hex

- **DDD**: ドメインモデル・Repositoryインターフェース・UseCaseを中心に設計。
- **Clean**: 依存は内側（Domain）へ向け、外部詳細はDataで吸収。
- **Hex**: DomainはPort（Repository）、DataはAdapter（実装）として分離。

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
    private val queries = database.algorithm_historyQueries
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
        queries.insertEvent(
            algorithm_type = algorithmType?.toDbValue(),
            event_type = eventType.toDbValue(),
            metadata = metadata,
            created_at_millis = createdAtMillis,
        )
    }

    fun observeRecent(limit: Int): Flow<List<AlgorithmHistoryEntry>> =
        flow {
            ensureDatabaseReady()
            emitAll(
                queries
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

---

## チェックリスト

- [ ] Port (Repository) は Domain 側にある
- [ ] Data 側は Adapter (実装) と Provider に分離されている
- [ ] SQLDelight のスキーマが `sqldelight/` に集約されている
- [ ] UseCase から Repository を経由して利用している

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
