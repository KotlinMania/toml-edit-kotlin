// port-lint: source toml_edit/src/document.rs
package io.github.kotlinmania.tomledit

/**
 * The root TOML Table, containing key/value pairs and all other logic tables.
 */
public class Document(
    public var root: Item = Item.TableItem(Table()),
    public var trailing: RawString = RawString.EMPTY,
    public var raw: String = "",
) {
    public fun asItem(): Item = root

    public fun intoItem(): Item = root

    public fun asTable(): Table? = root.asTable()

    public fun asTableMut(): Table {
        val table = root.asTable()
        if (table != null) return table
        val newTable = Table()
        root = Item.TableItem(newTable)
        return newTable
    }

    public fun despan() {
        root.despan(raw)
        trailing.toStrWithDefault(raw, "")
    }

    public operator fun get(key: String): Item? = asTable()?.get(key)

    public operator fun set(key: String, item: Item) {
        asTableMut()[key] = item
    }

    public fun toTomlString(): String = toTomlString(this)

    override fun toString(): String = toTomlString()

    public companion object {
        public fun new(): Document = Document()
    }
}

public typealias ImDocument = Document
