# データジェネレーターを追加する

このガイドでは、新しいデータ生成パターンを追加するために必要なすべての手順を説明します。

---

## 概要

データジェネレーターは、ソート対象の配列を様々なパターンで生成します:

```
data/
└── generator/
    ├── ArrayGenerator.kt        # インターフェース
    └── ArrayGeneratorImpl.kt    # 実装
```

---

## Step 1: ArrayGeneratorType に追加

```kotlin
// domain/model/ArrayGeneratorType.kt

enum class ArrayGeneratorType(val displayName: String) {
    RANDOM("Random"),
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    PARTIALLY_SORTED("Partially Sorted"),
    FEW_UNIQUE("Few Unique"),
    
    // 🆕 新規追加
    MOUNTAIN("Mountain"),        // 山型 (上昇→下降)
    VALLEY("Valley"),            // 谷型 (下降→上昇)
    ZIGZAG("Zigzag"),            // ジグザグ
    NEARLY_SORTED("Nearly Sorted"), // ほぼソート済み
    REVERSE_SORTED("Reverse Sorted"), // 逆順
    PIPE_ORGAN("Pipe Organ")     // パイプオルガン型
}
```

---

## Step 2: ArrayGeneratorImpl に実装を追加

```kotlin
// data/generator/ArrayGeneratorImpl.kt

class ArrayGeneratorImpl : ArrayGenerator {
    
    override fun generate(size: Int, type: ArrayGeneratorType): List<Int> {
        return when (type) {
            ArrayGeneratorType.RANDOM -> generateRandom(size)
            ArrayGeneratorType.ASCENDING -> generateAscending(size)
            ArrayGeneratorType.DESCENDING -> generateDescending(size)
            ArrayGeneratorType.PARTIALLY_SORTED -> generatePartiallySorted(size)
            ArrayGeneratorType.FEW_UNIQUE -> generateFewUnique(size)
            
            // 🆕 新規追加
            ArrayGeneratorType.MOUNTAIN -> generateMountain(size)
            ArrayGeneratorType.VALLEY -> generateValley(size)
            ArrayGeneratorType.ZIGZAG -> generateZigzag(size)
            ArrayGeneratorType.NEARLY_SORTED -> generateNearlySorted(size)
            ArrayGeneratorType.REVERSE_SORTED -> generateReverseSorted(size)
            ArrayGeneratorType.PIPE_ORGAN -> generatePipeOrgan(size)
        }
    }
    
    // 既存メソッド...
    
    // 🆕 新規メソッド
    
    /**
     * 山型配列を生成 (上昇→下降)
     * [1, 2, 3, 4, 5, 4, 3, 2, 1]
     */
    private fun generateMountain(size: Int): List<Int> {
        val mid = size / 2
        return (1..mid).toList() + (mid - 1 downTo 1).toList()
    }
    
    /**
     * 谷型配列を生成 (下降→上昇)
     * [5, 4, 3, 2, 1, 2, 3, 4, 5]
     */
    private fun generateValley(size: Int): List<Int> {
        val mid = size / 2
        return (mid downTo 1).toList() + (2..mid).toList()
    }
    
    /**
     * ジグザグ配列を生成
     * [1, 10, 2, 9, 3, 8, 4, 7, 5, 6]
     */
    private fun generateZigzag(size: Int): List<Int> {
        val sorted = (1..size).toList()
        return sorted.indices.map { i ->
            if (i % 2 == 0) sorted[i / 2]
            else sorted[size - 1 - i / 2]
        }
    }
    
    /**
     * ほぼソート済み配列を生成 (少数の要素がずれている)
     */
    private fun generateNearlySorted(size: Int): List<Int> {
        val result = (1..size).toMutableList()
        val swapCount = maxOf(1, size / 10)  // 10% をスワップ
        repeat(swapCount) {
            val i = Random.nextInt(size)
            val j = Random.nextInt(size)
            result[i] = result[j].also { result[j] = result[i] }
        }
        return result
    }
    
    /**
     * 逆順ソート済み配列を生成
     */
    private fun generateReverseSorted(size: Int): List<Int> {
        return (size downTo 1).toList()
    }
    
    /**
     * パイプオルガン型配列を生成
     * [1, 3, 5, 7, 9, 10, 8, 6, 4, 2]
     */
    private fun generatePipeOrgan(size: Int): List<Int> {
        val left = (1..size step 2).toList()
        val right = ((size / 2) * 2 downTo 2 step 2).toList()
        return left + right
    }
}
```

---

## Step 3: テストを追加

```kotlin
// commonTest/generator/ArrayGeneratorImplTest.kt

class ArrayGeneratorImplTest {
    private val generator = ArrayGeneratorImpl()
    
    // 既存テスト...
    
    // 🆕 新規テスト
    
    @Test
    fun `GIVEN size 9 WHEN generate MOUNTAIN THEN returns mountain pattern`() {
        val result = generator.generate(9, ArrayGeneratorType.MOUNTAIN)
        
        // 中央が最大
        val mid = result.size / 2
        assertTrue(result[mid] >= result.first())
        assertTrue(result[mid] >= result.last())
    }
    
    @Test
    fun `GIVEN size 10 WHEN generate VALLEY THEN returns valley pattern`() {
        val result = generator.generate(10, ArrayGeneratorType.VALLEY)
        
        // 中央が最小
        val mid = result.size / 2
        assertTrue(result[mid] <= result.first())
        assertTrue(result[mid] <= result.last())
    }
    
    @Test
    fun `GIVEN size 10 WHEN generate NEARLY_SORTED THEN mostly sorted`() {
        val result = generator.generate(10, ArrayGeneratorType.NEARLY_SORTED)
        
        // 大部分は正しい位置にある
        var correctPositions = 0
        val sorted = result.sorted()
        result.forEachIndexed { i, v ->
            if (v == sorted[i]) correctPositions++
        }
        assertTrue(correctPositions >= result.size * 0.8)  // 80%以上
    }
    
    @Test
    fun `GIVEN all generator types WHEN generate THEN returns correct size`() {
        ArrayGeneratorType.entries.forEach { type ->
            val result = generator.generate(10, type)
            assertEquals(10, result.size, "Failed for $type")
        }
    }
}
```

---

## Step 4: UI で選択可能にする (任意)

```kotlin
@Composable
fun GeneratorTypeSelector(
    selectedType: ArrayGeneratorType,
    onTypeSelected: (ArrayGeneratorType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        TextField(
            value = selectedType.displayName,
            onValueChange = { },
            readOnly = true
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ArrayGeneratorType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.displayName) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
```

---

## 命名規則

| パターン | 命名 | 説明 |
|----------|------|------|
| **標準** | `RANDOM`, `ASCENDING`, `DESCENDING` | 基本パターン |
| **部分** | `PARTIALLY_SORTED`, `NEARLY_SORTED` | 部分的な状態 |
| **形状** | `MOUNTAIN`, `VALLEY`, `ZIGZAG` | 配列の形状 |
| **特殊** | `PIPE_ORGAN`, `FEW_UNIQUE` | 特定の用途 |

---

## チェックリスト

- [ ] `ArrayGeneratorType` に enum 追加
- [ ] `ArrayGeneratorImpl` に生成メソッド追加
- [ ] `when` 式に case 追加
- [ ] テストを作成
- [ ] 各パターンでサイズが正しいことを確認
- [ ] KDoc を追加
- [ ] (任意) UI でタイプ選択可能にする

---

## 参考

- [reference/DATA_MODELS.md](../reference/DATA_MODELS.md)
- [reference/TESTING.md](../reference/TESTING.md)
