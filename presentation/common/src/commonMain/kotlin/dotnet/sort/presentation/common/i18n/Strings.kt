package dotnet.sort.presentation.common.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import dotnet.sort.domain.model.Language

/**
 * アプリケーション全体の文字列リソース。
 */
object Strings {
    // Common
    val home: String @Composable @ReadOnlyComposable get() = getString("Home", "ホーム")
    val settings: String @Composable @ReadOnlyComposable get() = getString("Settings", "設定")
    val learn: String @Composable @ReadOnlyComposable get() = getString("Learn", "学習")
    val compare: String @Composable @ReadOnlyComposable get() = getString("Compare", "比較")
    val quiz: String @Composable @ReadOnlyComposable get() = getString("Quiz", "クイズ")
    val sort: String @Composable @ReadOnlyComposable get() = getString("Sort", "ソート")
    val done: String @Composable @ReadOnlyComposable get() = getString("Done", "完了")
    val visualizer: String @Composable @ReadOnlyComposable get() = getString("Visualizer", "可視化")

    // Quiz Screen
    val mode: String @Composable @ReadOnlyComposable get() = getString("Mode", "モード")
    val difficulty: String @Composable @ReadOnlyComposable get() = getString("Difficulty", "難易度")
    val easy: String @Composable @ReadOnlyComposable get() = getString("Easy", "簡単")
    val medium: String @Composable @ReadOnlyComposable get() = getString("Medium", "普通")
    val hard: String @Composable @ReadOnlyComposable get() = getString("Hard", "難しい")
    val status: String @Composable @ReadOnlyComposable get() = getString("Status", "ステータス")
    val score: String @Composable @ReadOnlyComposable get() = getString("Score", "スコア")
    val streak: String @Composable @ReadOnlyComposable get() = getString("Streak", "連続正解")
    val correct: String @Composable @ReadOnlyComposable get() = getString("Correct", "正解")
    val incorrect: String @Composable @ReadOnlyComposable get() = getString("Incorrect", "不正解")
    val question: String @Composable @ReadOnlyComposable get() = getString("Question", "問題")
    val options: String @Composable @ReadOnlyComposable get() = getString("Options", "選択肢")
    val start: String @Composable @ReadOnlyComposable get() = getString("Start", "開始")
    val submit: String @Composable @ReadOnlyComposable get() = getString("Submit", "回答")
    val next: String @Composable @ReadOnlyComposable get() = getString("Next", "次へ")
    val nextQuestion: String @Composable @ReadOnlyComposable get() = getString("Next Question", "次の問題")
    val result: String @Composable @ReadOnlyComposable get() = getString("Result", "結果")
    val summary: String @Composable @ReadOnlyComposable get() = getString("Summary", "サマリー")
    val playAgain: String @Composable @ReadOnlyComposable get() = getString("Play Again", "もう一度プレイ")
    val scoreTrend: String @Composable @ReadOnlyComposable get() = getString("Score Trend", "スコア推移")
    val leaderboard: String @Composable @ReadOnlyComposable get() = getString("Leaderboard", "ランキング")
    val day: String @Composable @ReadOnlyComposable get() = getString("Day", "日")
    val week: String @Composable @ReadOnlyComposable get() = getString("Week", "週")
    val all: String @Composable @ReadOnlyComposable get() = getString("All", "全て")
    val latest: String @Composable @ReadOnlyComposable get() = getString("Latest", "最新")
    val best: String @Composable @ReadOnlyComposable get() = getString("Best", "最高")
    val average: String @Composable @ReadOnlyComposable get() = getString("Average", "平均")
    val newBestScore: String @Composable @ReadOnlyComposable get() = getString("New Best Score!", "新記録!")
    val showHint: String @Composable @ReadOnlyComposable get() = getString("Show Hint", "ヒントを表示")
    val hideHint: String @Composable @ReadOnlyComposable get() = getString("Hide Hint", "ヒントを隠す")
    val hint: String @Composable @ReadOnlyComposable get() = getString("Hint", "ヒント")
    val time: String @Composable @ReadOnlyComposable get() = getString("Time", "時間")
    val longestStreak: String @Composable @ReadOnlyComposable get() = getString("Longest Streak", "最大連続正解")
    val noScoresInPeriod: String @Composable @ReadOnlyComposable get() = getString("No scores in this period", "この期間のスコアはありません")
    val notEnoughData: String @Composable @ReadOnlyComposable get() = getString("Not enough data for trend", "トレンド表示に十分なデータがありません")
    val points: String @Composable @ReadOnlyComposable get() = getString("points", "ポイント")
    val comparison: String @Composable @ReadOnlyComposable get() = getString("Comparison", "比較")
    val bestScore: String @Composable @ReadOnlyComposable get() = getString("Best Score", "最高スコア")
    val averageScore: String @Composable @ReadOnlyComposable get() = getString("Average Score", "平均スコア")
    val winner: String @Composable @ReadOnlyComposable get() = getString("Winner", "勝者")
    val tie: String @Composable @ReadOnlyComposable get() = getString("Tie", "引き分け")

    // Learn Screen
    val algorithmHistory: String @Composable @ReadOnlyComposable get() = getString("History", "歴史")
    val algorithmConcepts: String @Composable @ReadOnlyComposable get() = getString("Concepts", "概念")
    val algorithmComplexity: String @Composable @ReadOnlyComposable get() = getString("Complexity", "計算量")
    val algorithmUseCases: String @Composable @ReadOnlyComposable get() = getString("Use Cases", "使用例")
    val algorithmCode: String @Composable @ReadOnlyComposable get() = getString("Code", "コード")
    val timeComplexity: String @Composable @ReadOnlyComposable get() = getString("Time Complexity", "時間計算量")
    val spaceComplexity: String @Composable @ReadOnlyComposable get() = getString("Space Complexity", "空間計算量")
    val bestCase: String @Composable @ReadOnlyComposable get() = getString("Best Case", "最良ケース")
    val averageCase: String @Composable @ReadOnlyComposable get() = getString("Average Case", "平均ケース")
    val worstCase: String @Composable @ReadOnlyComposable get() = getString("Worst Case", "最悪ケース")

    // Sort Screen
    val algorithm: String @Composable @ReadOnlyComposable get() = getString("Algorithm", "アルゴリズム")
    val arraySize: String @Composable @ReadOnlyComposable get() = getString("Array Size", "配列サイズ")
    val speed: String @Composable @ReadOnlyComposable get() = getString("Speed", "速度")
    val comparisons: String @Composable @ReadOnlyComposable get() = getString("Comparisons", "比較回数")
    val swaps: String @Composable @ReadOnlyComposable get() = getString("Swaps", "交換回数")
    val reset: String @Composable @ReadOnlyComposable get() = getString("Reset", "リセット")
    val shuffle: String @Composable @ReadOnlyComposable get() = getString("Shuffle", "シャッフル")
    val play: String @Composable @ReadOnlyComposable get() = getString("Play", "再生")
    val pause: String @Composable @ReadOnlyComposable get() = getString("Pause", "一時停止")
    val controlsSettings: String @Composable @ReadOnlyComposable get() = getString("Controls & Settings", "コントロール & 設定")
    val comparisonControls: String @Composable @ReadOnlyComposable get() = getString("Comparison Controls", "比較設定")
    val metricsComparison: String @Composable @ReadOnlyComposable get() = getString("Metrics Comparison", "メトリクス比較")
    val algorithms: String @Composable @ReadOnlyComposable get() = getString("Algorithms", "アルゴリズム")
    val left: String @Composable @ReadOnlyComposable get() = getString("Left", "左")
    val right: String @Composable @ReadOnlyComposable get() = getString("Right", "右")
    val inputPattern: String @Composable @ReadOnlyComposable get() = getString("Input pattern", "入力パターン")
    val pattern: String @Composable @ReadOnlyComposable get() = getString("Pattern", "パターン")

    // Settings Screen
    val theme: String @Composable @ReadOnlyComposable get() = getString("Theme", "テーマ")
    val darkMode: String @Composable @ReadOnlyComposable get() = getString("Dark Mode", "ダークモード")
    val language: String @Composable @ReadOnlyComposable get() = getString("Language", "言語")
    val sound: String @Composable @ReadOnlyComposable get() = getString("Sound", "サウンド")
    val volume: String @Composable @ReadOnlyComposable get() = getString("Volume", "音量")
    val barColorTheme: String @Composable @ReadOnlyComposable get() = getString("Color Theme", "カラーテーマ")
    val soundEffects: String @Composable @ReadOnlyComposable get() = getString("Sound Effects", "効果音")
    val appInfo: String @Composable @ReadOnlyComposable get() = getString("App Info", "アプリ情報")
    val version: String @Composable @ReadOnlyComposable get() = getString("Version", "バージョン")

    // Home Screen
    val learningProgress: String @Composable @ReadOnlyComposable get() = getString("Learning Progress", "学習進捗")
    val totalLearningTime: String @Composable @ReadOnlyComposable get() = getString("Total Learning Time", "総学習時間")
    val totalSessions: String @Composable @ReadOnlyComposable get() = getString("Total Sessions", "総セッション数")
    val algorithmProficiency: String @Composable @ReadOnlyComposable get() = getString("Algorithm Proficiency", "アルゴリズム習熟度")
    val quizSummary: String @Composable @ReadOnlyComposable get() = getString("Quiz Summary", "クイズサマリー")
    val attempts: String @Composable @ReadOnlyComposable get() = getString("Attempts", "試行回数")

    @Composable
    @ReadOnlyComposable
    private fun getString(english: String, japanese: String): String {
        return when (LocalAppLanguage.current) {
            Language.ENGLISH -> english
            Language.JAPANESE -> japanese
        }
    }
}
