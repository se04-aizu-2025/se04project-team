package dotnet.sort.designsystem.components.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens

/**
 * ソートアプリ用のドロップダウンセレクター (Atom)。
 *
 * ラベル付きの汎用ドロップダウンメニューを提供します。
 *
 * @param T 選択アイテムの型
 * @param label ラベルテキスト
 * @param selectedItem 現在選択されているアイテム
 * @param items 選択可能なアイテムリスト
 * @param onItemSelected アイテム選択時のコールバック
 * @param itemLabel アイテムの表示ラベルを返す関数
 * @param enabled 有効/無効状態
 * @param modifier Modifier
 * @param accessibilityDescription アクセシビリティ用の説明
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SortDropdown(
    label: String,
    selectedItem: T,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemLabel: @Composable (T) -> String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    accessibilityDescription: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    // アクセシビリティ用の説明を生成
    val selectedLabel = itemLabel(selectedItem)
    val defaultAccessibilityDescription = "$label: $selectedLabel が選択されています"

    Column(
        modifier = modifier.semantics {
            role = Role.DropdownList
            contentDescription = accessibilityDescription ?: defaultAccessibilityDescription
        },
        verticalArrangement = Arrangement.spacedBy(SpacingTokens.XS)
    ) {
        SortText(
            text = label,
            style = SortTheme.typography.labelMedium,
            color = SortTheme.colorScheme.onSurface
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                    .fillMaxWidth(),
                readOnly = true,
                value = itemLabel(selectedItem),
                onValueChange = {},
                label = null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                enabled = enabled
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { SortText(text = itemLabel(item)) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SortDropdownPreview() {
    SortTheme {
        SortDropdown(
            label = "Algorithm",
            selectedItem = "Bubble Sort",
            items = listOf("Bubble Sort", "Quick Sort", "Merge Sort"),
            onItemSelected = {},
            itemLabel = { it }
        )
    }
}
