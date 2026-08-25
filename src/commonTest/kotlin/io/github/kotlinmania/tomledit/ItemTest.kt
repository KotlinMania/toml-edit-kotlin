// port-lint: tests item.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ItemTest {
    @Test
    fun downcasting() {
        val none = Item.None
        assertTrue(none.isNone())
        assertEquals("none", none.typeName())

        val v = Item.value("foo")
        assertTrue(v.isValue())
        assertEquals("foo", v.asStr())

        val t = Item.table() as Item.TableItem
        t.table["bar"] = Item.value(42L)
        assertEquals("table", t.typeName())
        assertEquals(42L, t["bar"]?.asInteger())
    }

    @Test
    fun stringRoundtrip() {
        val v = Item.value("hello")
        val rendered = v.toTomlString()
        assertEquals("\"hello\"", rendered)
    }
}

