// port-lint: tests raw_string.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RawStringTest {
    @Test
    fun explicitString() {
        val s = RawString.from("hello")
        assertEquals("hello", s.asStr())
        assertEquals("hello", s.toStr("anything"))
        assertEquals("hello", s.toStrWithDefault(null, "default"))
    }

    @Test
    fun spannedString() {
        val s = RawString.withSpan(0..4)
        assertNull(s.asStr())
        assertEquals(0..4, s.span())
        assertEquals("hello", s.toStr("hello world"))
        assertEquals("hello", s.toStrWithDefault("hello world", "default"))
    }
}
