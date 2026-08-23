// port-lint: source item.rs
package io.github.kotlinmania.tomledit

/**
 * Type representing either a value, a table, an array of tables, or none.
 */
public sealed class Item {
    public open fun typeName(): String = "none"

    public open fun isNone(): Boolean = this is None

    public open fun isValue(): Boolean = this is ValueItem

    public open fun asValue(): Value? = (this as? ValueItem)?.value

    public open fun asStr(): String? = asValue()?.asStr()

    public open fun asInteger(): Long? = asValue()?.asInteger()

    public open fun asFloat(): Double? = asValue()?.asFloat()

    public open fun asBoolean(): Boolean? = asValue()?.asBoolean()

    public object None : Item() {
        override fun typeName(): String = "none"
    }

    public class ValueItem(
        public val value: Value,
    ) : Item() {
        override fun typeName(): String = value.typeName()
    }

    public class TableItem(
        public val entries: MutableMap<String, Item> = mutableMapOf(),
    ) : Item() {
        override fun typeName(): String = "table"

        public operator fun get(key: String): Item? = entries[key]

        public operator fun set(
            key: String,
            item: Item,
        ) {
            entries[key] = item
        }
    }

    public class ArrayOfTablesItem(
        public val tables: MutableList<TableItem> = mutableListOf(),
    ) : Item() {
        override fun typeName(): String = "array of tables"
    }

    public companion object {
        public fun value(s: String): Item = ValueItem(Value.from(s))

        public fun value(i: Long): Item = ValueItem(Value.from(i))

        public fun value(d: Double): Item = ValueItem(Value.from(d))

        public fun value(b: Boolean): Item = ValueItem(Value.from(b))

        public fun value(v: Value): Item = ValueItem(v)

        public fun table(): Item = TableItem()
    }
}
