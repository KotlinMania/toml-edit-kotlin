// port-lint: tests error.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ErrorTest {
    @Test
    fun customError() {
        val err = TomlError.custom("invalid syntax", 0..5)
        assertEquals("invalid syntax", err.message())
        assertEquals(0..5, err.span())
    }

    @Test
    fun emptySpan() {
        val err = TomlError.custom("unexpected eof")
        assertEquals("unexpected eof", err.message())
        assertNull(err.span())
    }
}
