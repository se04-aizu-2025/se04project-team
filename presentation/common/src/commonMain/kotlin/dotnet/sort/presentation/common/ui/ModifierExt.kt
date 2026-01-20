package dotnet.sort.presentation.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * フローティングトップバー分の余白を追加します。
 *
 * @param padding 追加する上部余白
 */
fun Modifier.floatingTopBarPadding(padding: Dp = 80.dp): Modifier = this.padding(top = padding)

/**
 * フローティングボトムバー分の余白を追加します。
 *
 * @param padding 追加する下部余白
 */
fun Modifier.floatingBottomBarPadding(padding: Dp = 96.dp): Modifier = this.padding(bottom = padding)
