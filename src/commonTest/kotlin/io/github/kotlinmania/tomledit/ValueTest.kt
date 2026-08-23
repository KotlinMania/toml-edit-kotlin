// port-lint: tests value.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValueTest {
    @Test
    fun downcasting() {
        val s = Value.from("hello")
        assertTrue(s.isStr())
        assertEquals("hello", s.asStr())
        assertEquals("string", s.typeName())

        val i = Value.from(123L)
        assertTrue(i.isInteger())
        assertEquals(123L, i.asInteger())
        assertEquals("integer", i.typeName())

        val b = Value.from(true)
        assertTrue(b.isBoolean())
        assertEquals(true, b.asBoolean())
        assertEquals("boolean", b.typeName())
    }
}
