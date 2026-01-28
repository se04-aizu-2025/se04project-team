package dotnet.sort.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dotnet.sort.domain.model.BarColorTheme

/**
 * カスタムカラースキームへのアクセス用CompositionLocal。
 */
val LocalSortColorScheme = staticCompositionLocalOf { LightColorScheme }

/**
 * カスタムタイポグラフィへのアクセス用CompositionLocal。
 */
val LocalSortTypography = staticCompositionLocalOf { DefaultTypography }

/**
 * DNSortアプリのテーマ。
 *
 * Material Theme 3をベースに、Kotlinブランドカラーを適用したカスタムテーマを提供します。
 *
 * @param darkTheme ダークテーマを使用するかどうか
 * @param content テーマを適用するコンテンツ
 */
@Composable
fun SortTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    barTheme: BarColorTheme = BarColorTheme.KOTLIN,
    content: @Composable () -> Unit
) {
    val sortColorScheme = (if (darkTheme) DarkColorScheme else LightColorScheme).withBarTheme(barTheme)

    val materialColorScheme = if (darkTheme) {
        darkColorScheme(
            primary = sortColorScheme.primary,
            secondary = sortColorScheme.secondary,
            background = sortColorScheme.background,
            surface = sortColorScheme.surface,
            error = sortColorScheme.error,
            onPrimary = sortColorScheme.onPrimary,
            onSecondary = sortColorScheme.onSecondary,
            onBackground = sortColorScheme.onBackground,
            onSurface = sortColorScheme.onSurface,
            onError = sortColorScheme.onError,
            surfaceVariant = sortColorScheme.surfaceVariant,
            onSurfaceVariant = sortColorScheme.onSurfaceVariant,
            primaryContainer = sortColorScheme.primaryContainer,
            onPrimaryContainer = sortColorScheme.onPrimaryContainer,
        )
    } else {
        lightColorScheme(
            primary = sortColorScheme.primary,
            secondary = sortColorScheme.secondary,
            background = sortColorScheme.background,
            surface = sortColorScheme.surface,
            error = sortColorScheme.error,
            onPrimary = sortColorScheme.onPrimary,
            onSecondary = sortColorScheme.onSecondary,
            onBackground = sortColorScheme.onBackground,
            onSurface = sortColorScheme.onSurface,
            onError = sortColorScheme.onError,
            surfaceVariant = sortColorScheme.surfaceVariant,
            onSurfaceVariant = sortColorScheme.onSurfaceVariant,
            primaryContainer = sortColorScheme.primaryContainer,
            onPrimaryContainer = sortColorScheme.onPrimaryContainer,
        )
    }

    CompositionLocalProvider(
        LocalSortColorScheme provides sortColorScheme,
        LocalSortTypography provides DefaultTypography,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}

/**
 * テーマへのアクセス用オブジェクト。
 */
object SortTheme {
    /**
     * 現在のカラースキームを取得します。
     */
    val colorScheme: SortColorScheme
        @Composable
        get() = LocalSortColorScheme.current

    /**
     * 現在のタイポグラフィを取得します。
     */
    val typography: SortTypography
        @Composable
        get() = LocalSortTypography.current
}
