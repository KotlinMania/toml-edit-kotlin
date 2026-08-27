// port-lint: tests document.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DocumentTest {
    @Test
    fun testDocumentBasic() {
        val doc = Document.new()
        assertNotNull(doc.asTable())
        assertTrue(doc.asTable()?.isEmpty() == true)

        doc["title"] = Item.value("TOML Example")
        doc["owner"] = Item.value("KotlinMania")

        assertEquals("TOML Example", doc["title"]?.asStr())
        assertEquals("KotlinMania", doc["owner"]?.asStr())

        val rendered = doc.toTomlString()
        assertTrue(rendered.contains("title = \"TOML Example\""))
        assertTrue(rendered.contains("owner = \"KotlinMania\""))
    }

    @Test
    fun testDocumentWithTable() {
        val doc = Document.new()
        val databaseTable = Table()
        databaseTable["server"] = Item.value("192.168.1.1")
        databaseTable["port"] = Item.value(5432L)
        doc["database"] = Item.TableItem(databaseTable)

        val rendered = doc.toTomlString()
        assertTrue(rendered.contains("[database]"))
        assertTrue(rendered.contains("server = \"192.168.1.1\""))
        assertTrue(rendered.contains("port = 5432"))
    }

    @Test
    fun defaultRoundtrip() {
        val doc = Document.new()
        assertEquals("", doc.toTomlString())
    }
}

