// port-lint: tests array_of_tables.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ArrayOfTablesTest {
    @Test
    fun testArrayOfTablesOperations() {
        val aot = ArrayOfTables()
        assertTrue(aot.isEmpty())
        assertEquals(0, aot.len())

        val t1 = Table()
        t1["name"] = Item.value("first")
        val t2 = Table()
        t2["name"] = Item.value("second")

        aot.push(t1)
        aot.push(t2)

        assertFalse(aot.isEmpty())
        assertEquals(2, aot.len())

        assertEquals("first", aot.get(0)?.get("name")?.asStr())
        assertEquals("second", aot.get(1)?.get("name")?.asStr())
        assertNull(aot.get(2))

        val removed = aot.remove(0)
        assertNotNull(removed)
        assertEquals("first", removed.get("name")?.asStr())
        assertEquals(1, aot.len())

        aot.clear()
        assertTrue(aot.isEmpty())
    }

    @Test
    fun testIntoArray() {
        val aot = ArrayOfTables()
        val t1 = Table()
        t1["a"] = Item.value(1L)
        aot.push(t1)

        val arr = aot.intoArray()
        assertEquals(1, arr.len())
        val firstItem = arr.get(0)
        assertNotNull(firstItem)
        assertTrue(firstItem.isInlineTable())
    }
}
