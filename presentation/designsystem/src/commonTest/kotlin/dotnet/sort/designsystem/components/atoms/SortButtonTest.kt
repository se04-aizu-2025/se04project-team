package dotnet.sort.designsystem.components.atoms

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class SortButtonTest {

    @Test
    fun `GIVEN enabled button WHEN clicked THEN invokes onClick`() = runComposeUiTest {
        var clicked = false
        
        setContent {
            SortButton(
                text = "Click Me",
                onClick = { clicked = true },
                enabled = true
            )
        }

        onNodeWithText("Click Me").performClick()

        assertTrue(clicked, "Button should register click")
    }

    @Test
    fun `GIVEN disabled button WHEN clicked THEN does not invoke onClick`() = runComposeUiTest {
        var clicked = false
        
        setContent {
            SortButton(
                text = "Don't Click",
                onClick = { clicked = true },
                enabled = false
            )
        }

        onNodeWithText("Don't Click").performClick()

        assertTrue(!clicked, "Disabled button should not register click")
    }
}
