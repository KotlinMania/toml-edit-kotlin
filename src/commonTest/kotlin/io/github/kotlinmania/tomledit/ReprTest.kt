// port-lint: tests repr.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ReprTest {
    @Test
    fun basicRepr() {
        val repr = Repr.fromString("  [foo]  ")
        assertEquals("  [foo]  ", repr.asRaw().asStr())
    }

    @Test
    fun decorClear() {
        val decor = Decor.new(RawString.from(" "), RawString.from("\n"))
        assertEquals(" ", decor.prefix()?.asStr())
        assertEquals("\n", decor.suffix()?.asStr())
        decor.clear()
        assertNull(decor.prefix())
        assertNull(decor.suffix())
    }

    @Test
    fun formattedValue() {
        val fmt = Formatted.new(42L)
        assertEquals(42L, fmt.value)
        assertNull(fmt.repr)
        fmt.repr = Repr.fromString("0x2a")
        assertEquals("0x2a", fmt.repr?.asRaw()?.asStr())
        fmt.fmt()
        assertNull(fmt.repr)
    }
}
