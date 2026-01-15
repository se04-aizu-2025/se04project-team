# 多言語対応を追加する

このガイドでは、アプリケーションに多言語対応（i18n）を追加するために必要なすべての手順を説明します。

---

## 概要

多言語対応を追加するには、以下を実装します:

1. リソースファイルの作成
2. 文字列リソースの定義
3. UI での文字列参照
4. 言語切替ロジック

---

## Step 1: リソースファイル構成

```
commonMain/
└── composeResources/
    ├── values/           # デフォルト (英語)
    │   └── strings.xml
    ├── values-ja/        # 日本語
    │   └── strings.xml
    └── values-zh/        # 中国語 (将来)
        └── strings.xml
```

---

## Step 2: 文字列リソースを定義

### 英語 (デフォルト)

```xml
<!-- values/strings.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- 画面タイトル -->
    <string name="home_title">Home</string>
    <string name="sort_title">Sort Visualizer</string>
    <string name="learn_title">Learn</string>
    <string name="compare_title">Compare</string>
    <string name="settings_title">Settings</string>
    
    <!-- ボタン -->
    <string name="btn_start">Start</string>
    <string name="btn_pause">Pause</string>
    <string name="btn_reset">Reset</string>
    
    <!-- アルゴリズム名 -->
    <string name="algo_bubble">Bubble Sort</string>
    <string name="algo_quick">Quick Sort</string>
    <string name="algo_merge">Merge Sort</string>
    
    <!-- 説明文 -->
    <string name="sort_description">Visualize sorting algorithms step by step</string>
</resources>
```

### 日本語

```xml
<!-- values-ja/strings.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- 画面タイトル -->
    <string name="home_title">ホーム</string>
    <string name="sort_title">ソート可視化</string>
    <string name="learn_title">学習</string>
    <string name="compare_title">比較</string>
    <string name="settings_title">設定</string>
    
    <!-- ボタン -->
    <string name="btn_start">開始</string>
    <string name="btn_pause">一時停止</string>
    <string name="btn_reset">リセット</string>
    
    <!-- アルゴリズム名 -->
    <string name="algo_bubble">バブルソート</string>
    <string name="algo_quick">クイックソート</string>
    <string name="algo_merge">マージソート</string>
    
    <!-- 説明文 -->
    <string name="sort_description">ソートアルゴリズムをステップごとに可視化</string>
</resources>
```

---

## Step 3: UI で文字列を参照

```kotlin
// Compose での使用
@Composable
fun SortScreen() {
    val strings = LocalStrings.current
    
    TopAppBar(
        title = { Text(strings.sortTitle) }
    )
    
    Button(onClick = onStart) {
        Text(strings.btnStart)
    }
}
```

### stringResource の使用

```kotlin
// compose-resources を使用する場合
import org.jetbrains.compose.resources.stringResource

@Composable
fun SortScreen() {
    TopAppBar(
        title = { Text(stringResource(Res.string.sort_title)) }
    )
}
```

---

## Step 4: 言語切替ロジック

### Language enum

```kotlin
enum class Language(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    JAPANESE("ja", "日本語")
}
```

### SettingsRepository

```kotlin
interface SettingsRepository {
    suspend fun getLanguage(): Language
    suspend fun setLanguage(language: Language)
}
```

### 言語切替 UI

```kotlin
@Composable
fun LanguageSelector(
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = currentLanguage.displayName,
            onValueChange = { },
            readOnly = true
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Language.entries.forEach { language ->
                DropdownMenuItem(
                    text = { Text(language.displayName) },
                    onClick = {
                        onLanguageChange(language)
                        expanded = false
                    }
                )
            }
        }
    }
}
```

---

## Step 5: アプリ起動時の言語設定

```kotlin
@Composable
fun App() {
    val settingsRepository = koinInject<SettingsRepository>()
    var language by remember { mutableStateOf(Language.ENGLISH) }
    
    LaunchedEffect(Unit) {
        language = settingsRepository.getLanguage()
    }
    
    CompositionLocalProvider(
        LocalLanguage provides language
    ) {
        AppNavigation()
    }
}
```

---

## 命名規則

### リソース名

| 種類 | 規則 | 例 |
|------|------|-----|
| **画面タイトル** | `{screen}_title` | `home_title`, `sort_title` |
| **ボタン** | `btn_{action}` | `btn_start`, `btn_pause` |
| **アルゴリズム** | `algo_{name}` | `algo_bubble`, `algo_quick` |
| **エラー** | `error_{type}` | `error_network`, `error_empty` |
| **説明** | `{screen}_description` | `sort_description` |

---

## チェックリスト

- [ ] `values/strings.xml` にデフォルト (英語) を追加
- [ ] `values-ja/strings.xml` に日本語を追加
- [ ] UI で `stringResource()` を使用
- [ ] Language enum を定義
- [ ] SettingsRepository に言語設定を追加
- [ ] 言語切替 UI を Settings に追加
- [ ] アプリ起動時に言語を読み込む
- [ ] ハードコード文字列を置換

---

## 参考

- [Compose Resources](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources.html)
- [Android Localization](https://developer.android.com/guide/topics/resources/localization)
