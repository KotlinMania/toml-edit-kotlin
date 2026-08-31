// port-lint: tests toml_edit/src/value.rs
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

    @Test
    fun fromIterFormatting() {
        val arr = Array()
        arr.push("node")
        arr.push("mouth")
        val v = Value.ArrayValue(arr)
        assertEquals("[\"node\", \"mouth\"]", v.toTomlString())
    }

    @Test
    fun stringRoundtrip() {
        val v = Value.from("hello")
        assertEquals("\"hello\"", v.toTomlString())
    }
}
