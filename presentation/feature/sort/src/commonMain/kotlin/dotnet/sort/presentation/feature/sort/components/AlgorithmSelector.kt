package dotnet.sort.presentation.feature.sort.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.domain.model.SortType

/**
 * アルゴリズム選択コンポーネント。
 *
 * @param selectedAlgorithm 現在選択されているアルゴリズム
 * @param onAlgorithmSelected アルゴリズム選択時のコールバック
 * @param enabled 有効/無効状態
 * @param modifier Modifier
 */
@Composable
fun AlgorithmSelector(
    selectedAlgorithm: SortType,
    onAlgorithmSelected: (SortType) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    SortDropdown(
        label = "Algorithm",
        selectedItem = selectedAlgorithm,
        items = SortType.entries.toList(),
        onItemSelected = onAlgorithmSelected,
        itemLabel = { it.displayName },
        enabled = enabled,
        modifier = modifier
    )
}
