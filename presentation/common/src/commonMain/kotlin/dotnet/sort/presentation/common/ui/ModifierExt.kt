package dotnet.sort.presentation.common.ui

import androidx.compose.ui.Modifier

/**
 * 条件に基づいて Modifier を適用します。
 *
 * @param condition チェックする条件（trueの場合に適用）。
 * @param modifier [condition] が true の場合に適用する Modifier。
 * @return 変更された Modifier。
 */
fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}
