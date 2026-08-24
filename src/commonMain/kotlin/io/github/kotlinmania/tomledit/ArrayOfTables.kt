// port-lint: source array_of_tables.rs
package io.github.kotlinmania.tomledit

/**
 * A top-level sequence of Tables, each under their own header.
 */
public class ArrayOfTables public constructor(
    public var span: Span? = null,
) {
    internal val values: MutableList<Item> = mutableListOf()

    internal constructor(
        span: Span?,
        values: MutableList<Item>,
    ) : this(span) {
        this.values.addAll(values)
    }

    public fun len(): Int = values.count { !it.isNone() }

    public fun isEmpty(): Boolean = len() == 0

    public fun clear() {
        values.clear()
    }

    public fun despan(input: String) {
        this.span = null
        for (value in values) {
            value.despan(input)
        }
    }

    public fun get(index: Int): Table? {
        val item = values.getOrNull(index) ?: return null
        return item.asTable()
    }

    public fun push(table: Table) {
        values.add(Item.TableItem(table))
    }

    public fun push(item: Item) {
        values.add(item)
    }

    public fun remove(index: Int): Table? {
        if (index < 0 || index >= values.size) return null
        val item = values.removeAt(index)
        return item.asTable()
    }

    public fun iter(): List<Table> = values.mapNotNull { it.asTable() }

    public fun intoArray(): Array {
        val array = Array(span = span)
        for (value in values) {
            val v = value.makeValue()
            array.values.add(Item.ValueItem(v))
        }
        array.fmt()
        return array
    }

    public fun fmt() {
        for (value in values) {
            value.fmt()
        }
    }

    public companion object {
        public fun new(): ArrayOfTables = ArrayOfTables()
    }
}
