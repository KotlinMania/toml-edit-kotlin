// port-lint: tests encode.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EncodeTest {
    @Test
    fun testEncodeScalarValues() {
        val s = Value.from("hello world")
        val sBuf = StringBuilder()
        encodeValue(s, sBuf)
        assertEquals("\"hello world\"", sBuf.toString())

        val i = Value.from(12345L)
        val iBuf = StringBuilder()
        encodeValue(i, iBuf)
        assertEquals("12345", iBuf.toString())

        val b = Value.from(true)
        val bBuf = StringBuilder()
        encodeValue(b, bBuf)
        assertEquals("true", bBuf.toString())
    }

    @Test
    fun testEncodeArray() {
        val arr = Array()
        arr.push(1L)
        arr.push(2L)
        arr.push(3L)

        val buf = StringBuilder()
        encodeArray(arr, buf)
        assertEquals("[1, 2, 3]", buf.toString())
    }

    @Test
    fun testEncodeInlineTable() {
        val inline = InlineTable()
        inline["x"] = Item.value(1L)
        inline["y"] = Item.value(2L)

        val buf = StringBuilder()
        encodeInlineTable(inline, buf)
        assertEquals("{ x = 1, y = 2 }", buf.toString())
    }

    @Test
    fun testEncodeDocument() {
        val doc = Document.new()
        doc["key"] = Item.value("value")

        val table = Table()
        table["nested_key"] = Item.value(42L)
        doc["section"] = Item.TableItem(table)

        val toml = toTomlString(doc)
        assertTrue(toml.contains("key = \"value\""))
        assertTrue(toml.contains("[section]"))
        assertTrue(toml.contains("nested_key = 42"))
    }
}
