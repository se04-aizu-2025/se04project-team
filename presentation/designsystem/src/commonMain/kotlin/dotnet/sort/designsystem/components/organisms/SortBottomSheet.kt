package dotnet.sort.designsystem.components.organisms

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dotnet.sort.designsystem.theme.SortTheme

/**
 * ソートアプリ用ボトムシート (Organism).
 *
 * Material3 ModalBottomSheet のラッパー。テーマに準拠したスタイルを提供します。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    shape: Shape = MaterialTheme.shapes.large,
    containerColor: Color = SortTheme.colorScheme.surface,
    contentColor: Color = SortTheme.colorScheme.onSurface,
    tonalElevation: Dp = 6.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.32f),
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
        content = content,
    )
}
