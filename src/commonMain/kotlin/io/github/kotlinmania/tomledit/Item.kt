// port-lint: source item.rs
package io.github.kotlinmania.tomledit

/**
 * Type representing either a value, a table, an array of tables, or none.
 */
public sealed class Item {
    public open fun typeName(): String = "none"

    public open fun isNone(): Boolean = this is None

    public open fun isValue(): Boolean = this is ValueItem

    public open fun isTable(): Boolean = this is TableItem

    public open fun isArrayOfTables(): Boolean = this is ArrayOfTablesItem

    public open fun asValue(): Value? = (this as? ValueItem)?.value

    public open fun asTable(): Table? = (this as? TableItem)?.table

    public open fun asArrayOfTables(): ArrayOfTables? = (this as? ArrayOfTablesItem)?.arrayOfTables

    public open fun asStr(): String? = asValue()?.asStr()

    public open fun asInteger(): Long? = asValue()?.asInteger()

    public open fun asFloat(): Double? = asValue()?.asFloat()

    public open fun asBoolean(): Boolean? = asValue()?.asBoolean()

    public open fun asArray(): Array? = asValue()?.asArray()

    public open fun asInlineTable(): InlineTable? = asValue()?.asInlineTable()

    public open fun orInsert(item: Item): Item = if (isNone()) item else this

    public open fun makeValue(): Value =
        when (this) {
            is ValueItem -> value
            is TableItem -> {
                val inline = table.intoInlineTable()
                Value.InlineTableValue(inline)
            }
            is ArrayOfTablesItem -> {
                val arr = arrayOfTables.intoArray()
                Value.ArrayValue(arr)
            }
            is None -> Value.from("")
        }

    public open fun fmt() {
        when (this) {
            is ValueItem -> value.fmt()
            is TableItem -> table.fmt()
            is ArrayOfTablesItem -> arrayOfTables.fmt()
            is None -> {}
        }
    }

    public open val span: Span?
        get() =
            when (this) {
                is ValueItem -> value.span
                is TableItem -> table.span
                is ArrayOfTablesItem -> arrayOfTables.span
                is None -> null
            }

    public open fun despan(input: String) {
        when (this) {
            is ValueItem -> value.despan(input)
            is TableItem -> table.despan(input)
            is ArrayOfTablesItem -> arrayOfTables.despan(input)
            is None -> {}
        }
    }

    public open operator fun get(key: String): Item? =
        when (this) {
            is TableItem -> table[key]
            is ValueItem -> value.asInlineTable()?.get(key)
            else -> null
        }

    public open operator fun get(index: Int): Item? =
        when (this) {
            is ArrayOfTablesItem -> arrayOfTables.get(index)?.let { TableItem(it) }
            is ValueItem -> value.asArray()?.get(index)?.let { ValueItem(it) }
            else -> null
        }

    public object None : Item() {
        override fun typeName(): String = "none"
    }

    public class ValueItem(
        public val value: Value,
    ) : Item() {
        override fun typeName(): String = value.typeName()
    }

    public class TableItem(
        public val table: Table = Table(),
    ) : Item() {
        override fun typeName(): String = "table"
    }

    public class ArrayOfTablesItem(
        public val arrayOfTables: ArrayOfTables = ArrayOfTables(),
    ) : Item() {
        override fun typeName(): String = "array of tables"
    }

    public companion object {
        public fun value(s: String): Item = ValueItem(Value.from(s))

        public fun value(i: Long): Item = ValueItem(Value.from(i))

        public fun value(d: Double): Item = ValueItem(Value.from(d))

        public fun value(b: Boolean): Item = ValueItem(Value.from(b))

        public fun value(v: Value): Item = ValueItem(v)

        public fun table(): Item = TableItem(Table.new())

        public fun arrayOfTables(): Item = ArrayOfTablesItem(ArrayOfTables.new())
    }
}
