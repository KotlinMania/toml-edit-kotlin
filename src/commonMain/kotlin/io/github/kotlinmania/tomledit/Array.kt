// port-lint: source array.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML Value that contains a sequence of Values.
 */
public class Array public constructor(
    public var trailing: RawString = RawString.EMPTY,
    public var trailingComma: Boolean = false,
    public var decor: Decor = Decor(),
    public var span: Span? = null,
) {
    internal val values: MutableList<Item> = mutableListOf()

    internal constructor(
        trailing: RawString,
        trailingComma: Boolean,
        decor: Decor,
        span: Span?,
        values: MutableList<Item>,
    ) : this(trailing, trailingComma, decor, span) {
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

    public fun get(index: Int): Value? {
        val item = values.getOrNull(index) ?: return null
        return item.asValue()
    }

    public fun push(value: Value) {
        values.add(Item.ValueItem(value))
    }

    public fun push(value: String) {
        push(Value.from(value))
    }

    public fun push(value: Long) {
        push(Value.from(value))
    }

    public fun push(value: Double) {
        push(Value.from(value))
    }

    public fun push(value: Boolean) {
        push(Value.from(value))
    }

    public fun push(item: Item) {
        item.makeValue()
        values.add(item)
    }

    public fun insert(index: Int, value: Value) {
        values.add(index, Item.ValueItem(value))
    }

    public fun remove(index: Int): Value? {
        if (index < 0 || index >= values.size) return null
        val item = values.removeAt(index)
        return item.asValue()
    }

    public fun iter(): List<Value> = values.mapNotNull { it.asValue() }

    public fun fmt() {
        for (value in values) {
            value.fmt()
        }
    }

    public companion object {
        public fun new(): Array = Array()
    }
}
