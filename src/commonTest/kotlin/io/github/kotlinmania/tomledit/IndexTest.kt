// port-lint: tests toml_edit/src/index.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class IndexTest {
    @Test
    fun testStringIndex() {
        val idx = StringIndex("key")
        assertEquals("key", idx.key)
        val item = Item.None
        assertNull(idx.index(item))
        assertNull(idx.indexMut(item))
    }

    @Test
    fun testIntegerIndex() {
        val idx = IntegerIndex(0)
        assertEquals(0, idx.index)
        val item = Item.None
        assertNull(idx.index(item))
        assertNull(idx.indexMut(item))
    }
}
