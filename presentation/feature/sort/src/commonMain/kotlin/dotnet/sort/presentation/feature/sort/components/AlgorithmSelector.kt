package dotnet.sort.presentation.feature.sort.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortDropdown
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.sort_algorithm_selector_label
import dotnet.sort.designsystem.utils.toDisplayName
import dotnet.sort.model.SortType
import org.jetbrains.compose.resources.stringResource

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
        label = stringResource(Res.string.sort_algorithm_selector_label),
        selectedItem = selectedAlgorithm,
        items = SortType.entries.toList(),
        onItemSelected = onAlgorithmSelected,
        itemLabel = { stringResource(it.toDisplayName()) },
        enabled = enabled,
        modifier = modifier
    )
}
