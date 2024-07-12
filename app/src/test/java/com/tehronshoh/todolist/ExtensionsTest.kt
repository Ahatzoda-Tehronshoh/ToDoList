package com.tehronshoh.todolist

import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {
    @Test
    fun `Calendar getDateString`() {
        val x = 10.0
        val y = 20.0
        val expectedValue = 30.0
        val actualValue = x + y
        assertEquals(expectedValue, actualValue)
    }
}