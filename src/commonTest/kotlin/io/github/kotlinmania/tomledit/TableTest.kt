// port-lint: source table.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TableTest {
    @Test
    fun testTableCreationAndOperations() {
        val table = Table()
        assertTrue(table.isEmpty())
        assertEquals(0, table.len())

        table["key1"] = Item.value("value1")
        table["key2"] = Item.value(42L)

        assertFalse(table.isEmpty())
        assertEquals(2, table.len())
        assertTrue(table.containsKey("key1"))
        assertTrue(table.containsKey("key2"))
        assertFalse(table.containsKey("key3"))

        val val1 = table["key1"]
        assertNotNull(val1)
        assertEquals("value1", val1.asStr())

        val val2 = table["key2"]
        assertNotNull(val2)
        assertEquals(42L, val2.asInteger())

        val removed = table.remove("key1")
        assertNotNull(removed)
        assertEquals("value1", removed.asStr())
        assertEquals(1, table.len())
        assertFalse(table.containsKey("key1"))

        table.clear()
        assertTrue(table.isEmpty())
        assertEquals(0, table.len())
    }

    @Test
    fun testTableImplicitAndDottedFlags() {
        val table = Table()
        assertFalse(table.implicit)
        assertFalse(table.dotted)

        table.implicit = true
        assertTrue(table.implicit)

        table.dotted = true
        assertTrue(table.dotted)
    }

    @Test
    fun testIntoInlineTable() {
        val table = Table()
        table["a"] = Item.value("alpha")
        table["b"] = Item.value(123L)

        val inline = table.intoInlineTable()
        assertEquals(2, inline.len())
        assertEquals("alpha", inline["a"]?.asStr())
        assertEquals(123L, inline["b"]?.asInteger())
    }
}
