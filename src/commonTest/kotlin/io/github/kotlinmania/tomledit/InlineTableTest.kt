// port-lint: tests inline_table.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InlineTableTest {
    @Test
    fun testInlineTableCreationAndOperations() {
        val table = InlineTable()
        assertTrue(table.isEmpty())
        assertEquals(0, table.len())

        table["name"] = Item.value("Kotlin")
        table["version"] = Item.value(2L)

        assertFalse(table.isEmpty())
        assertEquals(2, table.len())
        assertTrue(table.containsKey("name"))
        assertTrue(table.containsKey("version"))

        assertEquals("Kotlin", table["name"]?.asStr())
        assertEquals(2L, table["version"]?.asInteger())

        val removed = table.remove("name")
        assertNotNull(removed)
        assertEquals("Kotlin", removed.asStr())
        assertEquals(1, table.len())

        table.clear()
        assertTrue(table.isEmpty())
    }

    @Test
    fun testIntoTable() {
        val inline = InlineTable()
        inline["x"] = Item.value(10L)
        inline["y"] = Item.value(20L)

        val table = inline.intoTable()
        assertEquals(2, table.len())
        assertEquals(10L, table["x"]?.asInteger())
        assertEquals(20L, table["y"]?.asInteger())
    }
}
