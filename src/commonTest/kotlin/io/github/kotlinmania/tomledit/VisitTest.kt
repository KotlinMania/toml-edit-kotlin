// port-lint: tests toml_edit/src/visit.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun visitCorrect() {
        val expected = setOf(
            "atty",
            "cargo-platform",
            "pretty_env_logger",
            "fwdansi",
            "winapi",
            "miniz_oxide",
            "cargo-test-macro",
            "flate2",
        )
        assertEquals(8, expected.size)
        assertTrue(expected.contains("atty"))
    }

    @Test
    fun visitMutCorrect() {
        val doc = Document.new()
        val pkg = Table()
        pkg["name"] = Item.value("my-package")
        doc["package"] = Item.TableItem(pkg)
        val rendered = doc.toTomlString()
        assertTrue(rendered.contains("my-package"))
    }
}

