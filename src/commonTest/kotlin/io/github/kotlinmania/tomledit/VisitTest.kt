// port-lint: source visit.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals

class VisitTest {
    @Test
    fun testStringCollectorVisitor() {
        val strings = mutableListOf<String>()
        val visitor =
            object : Visit {
                override fun visitString(node: FormattedString) {
                    strings.add(node.value)
                }
            }

        val doc = Document.new()
        doc["title"] = Item.value("KotlinMania")
        val table = Table()
        table["author"] = Item.value("Sydney")
        doc["meta"] = Item.TableItem(table)

        visitor.visitDocument(doc)
        assertEquals(listOf("KotlinMania", "Sydney"), strings)
    }

    @Test
    fun testVisitMut() {
        val visitor =
            object : VisitMut {
                override fun visitStringMut(node: FormattedString) {
                    node.value = node.value.uppercase()
                }
            }

        val doc = Document.new()
        doc["greet"] = Item.value("hello")
        visitor.visitDocumentMut(doc)

        assertEquals("HELLO", doc["greet"]?.asStr())
    }
}
