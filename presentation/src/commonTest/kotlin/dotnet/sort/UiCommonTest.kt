package dotnet.sort

import kotlin.test.Test
import kotlin.test.assertTrue

class UiCommonTest {
    @Test
    fun testPlatformIsNotEmpty() {
        assertTrue(platform().isNotEmpty())
    }
}
