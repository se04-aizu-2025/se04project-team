package dotnet.sort.designsystem.components.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.AnimationTokens
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソート可視化用の単一バーコンポーネント (Atom)。
 *
 * 配列の各要素を棒グラフとして表示します。
 * バーの高さは値に比例し、状態に応じて色が変化します。
 *
 * @param value バーが表す値
 * @param maxValue 配列内の最大値（高さ計算に使用）
 * @param state バーの現在の状態 [BarState]
 * @param modifier Modifier
 * @param accessibilityLabel アクセシビリティ用のラベル（スクリーンリーダー対応）
 */
@Composable
fun SortBar(
    value: Int,
    maxValue: Int,
    state: BarState = BarState.Default,
    modifier: Modifier = Modifier,
    accessibilityLabel: String? = null,
) {
    val colorScheme = SortTheme.colorScheme

    val targetColor = when (state) {
        BarState.Default -> colorScheme.barDefault
        BarState.Comparing -> colorScheme.barComparing
        BarState.Swapping -> colorScheme.barSwapping
        BarState.Sorted -> colorScheme.barSorted
        BarState.Pivot -> colorScheme.barPivot
        BarState.Selected -> colorScheme.barSelected
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = AnimationTokens.BarColorAnimationDuration),
        label = "barColor"
    )

    // heightFraction: 0.0 ~ 1.0
    val heightFraction = if (maxValue > 0) {
        (value.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    // アクセシビリティ用の状態説明を生成
    val stateDescription = when (state) {
        BarState.Default -> "デフォルト状態"
        BarState.Comparing -> "比較中"
        BarState.Swapping -> "交換中"
        BarState.Sorted -> "ソート済み"
        BarState.Pivot -> "ピボット"
        BarState.Selected -> "選択中"
    }

    val defaultAccessibilityLabel = "値 $value, $stateDescription"

    Box(
        modifier = modifier
            .fillMaxHeight(heightFraction)
            .clip(RoundedCornerShape(topStart = SpacingTokens.XS, topEnd = SpacingTokens.XS))
            .background(animatedColor)
            .semantics {
                contentDescription = accessibilityLabel ?: defaultAccessibilityLabel
            }
    )
}

@Preview
@Composable
private fun SortBarPreview() {
    SortTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.XS),
            modifier = Modifier.height(SpacingTokens.VisualizerHeightCompact)
        ) {
            SortBar(value = 30, maxValue = 100, state = BarState.Default)
            SortBar(value = 60, maxValue = 100, state = BarState.Comparing)
            SortBar(value = 90, maxValue = 100, state = BarState.Sorted)
        }
    }
}
