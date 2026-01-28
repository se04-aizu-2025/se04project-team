package dotnet.sort.presentation.feature.learn

import androidx.compose.ui.graphics.vector.ImageVector
import dotnet.sort.model.SortType
import org.jetbrains.compose.resources.StringResource

/**
 * Learn画面で使用するアルゴリズムの表示モデル。
 */
data class LearnAlgorithmItem(
    val type: SortType,
    val title: StringResource,
    val description: StringResource,
    val icon: ImageVector,
)
