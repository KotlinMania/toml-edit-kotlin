// port-lint: tests toml_edit/src/key.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals

class KeyTest {
    @Test
    fun parseDotted() {
        val keys = Key.parse("a.b.c")
        assertEquals(3, keys.size)
        assertEquals("a", keys[0].get())
        assertEquals("b", keys[1].get())
        assertEquals("c", keys[2].get())
    }

    @Test
    fun stringRoundtrip() {
        val k = Key.new("hello")
        val parsed = Key.parse(k.toTomlString())
        assertEquals(1, parsed.size)
        assertEquals("hello", parsed[0].get())
    }
}
