package com.segnities007.ui

import kotlin.test.Test
import kotlin.test.assertTrue

class UiCommonTest {
    @Test
    fun testPlatformIsNotEmpty() {
        assertTrue(platform().isNotEmpty())
    }
}
