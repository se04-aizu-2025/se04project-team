package dotnet.sort.presentation.feature.learn.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dotnet.sort.designsystem.components.atoms.SortText
import dotnet.sort.designsystem.components.atoms.SortTitle
import dotnet.sort.designsystem.components.molecules.SortSectionCard
import dotnet.sort.designsystem.theme.SortTheme
import dotnet.sort.designsystem.tokens.SpacingTokens
import dotnet.sort.presentation.feature.learn.model.AlgorithmHistory
import dotnet.sort.designsystem.generated.resources.Res
import dotnet.sort.designsystem.generated.resources.*
import org.jetbrains.compose.resources.stringResource

/**
 * アルゴリズムの歴史・概要を表示するコンポーネント。
 *
 * @param history アルゴリズムの歴史情報
 * @param modifier Modifier
 */
@Composable
fun AlgorithmOverview(
    history: AlgorithmHistory,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(SpacingTokens.M)
    ) {
        // Title Card
        SortSectionCard(title = stringResource(Res.string.learn_history_origin)) {
            SortText(
                text = stringResource(Res.string.learn_discover_origins, history.sortType.displayName),
                style = SortTheme.typography.bodyMedium,
                color = SortTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Details
        SortSectionCard(title = stringResource(Res.string.learn_details)) {
            InfoRow(label = stringResource(Res.string.learn_label_inventor), value = history.inventor)
            Spacer(modifier = Modifier.height(SpacingTokens.S))
            InfoRow(label = stringResource(Res.string.learn_label_year), value = history.originYear)
        }

        Spacer(modifier = Modifier.height(SpacingTokens.M))

        // Background
        SortSectionCard(title = stringResource(Res.string.learn_background)) {
            SortText(
                text = history.description,
                style = SortTheme.typography.bodyLarge,
                color = SortTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = SpacingTokens.XS)) {
        SortText(
            text = label,
            style = SortTheme.typography.labelMedium,
            color = SortTheme.colorScheme.onSurfaceVariant
        )
        SortText(
            text = value,
            style = SortTheme.typography.bodyLarge,
            color = SortTheme.colorScheme.onSurface
        )
    }
}
