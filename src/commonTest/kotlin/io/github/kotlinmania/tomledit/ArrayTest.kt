// port-lint: tests toml_edit/src/array.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ArrayTest {
    @Test
    fun testArrayOperations() {
        val array = Array()
        assertTrue(array.isEmpty())
        assertEquals(0, array.len())

        array.push("one")
        array.push(2L)
        array.push(3.14)
        array.push(true)

        assertFalse(array.isEmpty())
        assertEquals(4, array.len())

        assertEquals("one", array.get(0)?.asStr())
        assertEquals(2L, array.get(1)?.asInteger())
        assertEquals(3.14, array.get(2)?.asFloat())
        assertEquals(true, array.get(3)?.asBoolean())
        assertNull(array.get(4))

        val removed = array.remove(1)
        assertNotNull(removed)
        assertEquals(2L, removed.asInteger())
        assertEquals(3, array.len())

        array.clear()
        assertTrue(array.isEmpty())
        assertEquals(0, array.len())
    }

    @Test
    fun testArrayIter() {
        val array = Array()
        array.push(10L)
        array.push(20L)
        array.push(30L)

        val values = array.iter().mapNotNull { it.asInteger() }
        assertEquals(listOf(10L, 20L, 30L), values)
    }
}
