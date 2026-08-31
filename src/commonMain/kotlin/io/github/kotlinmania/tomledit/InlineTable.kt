// port-lint: source toml_edit/src/inline_table.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML Value that contains a collection of Key/Value pairs.
 */
public class InlineTable public constructor(
    public var trailing: RawString = RawString.EMPTY,
    public var trailingComma: Boolean = false,
    public var implicit: Boolean = false,
    public var dotted: Boolean = false,
    public var decor: Decor = Decor(),
    public var span: Span? = null,
) {
    internal val items: LinkedHashMap<Key, Item> = LinkedHashMap()

    internal constructor(
        trailing: RawString,
        trailingComma: Boolean,
        implicit: Boolean,
        dotted: Boolean,
        decor: Decor,
        span: Span?,
        items: LinkedHashMap<Key, Item>,
    ) : this(trailing, trailingComma, implicit, dotted, decor, span) {
        this.items.putAll(items)
    }

    public fun despan(input: String) {
        this.span = null
        for ((key, value) in items) {
            value.despan(input)
        }
    }

    public fun len(): Int = items.values.count { !it.isNone() }

    public fun isEmpty(): Boolean = len() == 0

    public fun clear() {
        items.clear()
    }

    public fun containsKey(key: String): Boolean = items.keys.any { it.get() == key }

    public operator fun get(key: String): Item? {
        val entry = items.entries.firstOrNull { it.key.get() == key } ?: return null
        return if (!entry.value.isNone()) entry.value else null
    }

    public operator fun get(key: Key): Item? {
        val entry = items.entries.firstOrNull { it.key.get() == key.get() } ?: return null
        return if (!entry.value.isNone()) entry.value else null
    }

    public operator fun set(key: String, item: Item) {
        val existingKey = items.keys.firstOrNull { it.get() == key }
        if (existingKey != null) {
            items[existingKey] = item
        } else {
            items[Key.new(key)] = item
        }
    }

    public operator fun set(key: Key, item: Item) {
        val existingKey = items.keys.firstOrNull { it.get() == key.get() }
        if (existingKey != null) {
            items[existingKey] = item
        } else {
            items[key] = item
        }
    }

    public fun insert(key: String, item: Item): Item? {
        val existingKey = items.keys.firstOrNull { it.get() == key }
        return if (existingKey != null) {
            val old = items[existingKey]
            items[existingKey] = item
            if (old != null && !old.isNone()) old else null
        } else {
            items[Key.new(key)] = item
            null
        }
    }

    public fun remove(key: String): Item? {
        val existingKey = items.keys.firstOrNull { it.get() == key } ?: return null
        val old = items.remove(existingKey)
        return if (old != null && !old.isNone()) old else null
    }

    public fun keys(): List<String> = items.keys.map { it.get() }

    public fun keyEntries(): List<Pair<Key, Item>> = items.entries.map { Pair(it.key, it.value) }

    public fun intoTable(): Table {
        val table =
            Table(
                decor = decor,
                implicit = implicit,
                dotted = dotted,
                span = span,
            )
        table.items.putAll(items)
        table.fmt()
        return table
    }

    public fun fmt() {
        for ((key, value) in items) {
            key.fmt()
            value.fmt()
        }
    }

    public fun getValues(): List<Pair<List<Key>, Value>> {
        val values = mutableListOf<Pair<List<Key>, Value>>()
        appendValues(emptyList(), values)
        return values
    }

    internal fun appendValues(
        parent: List<Key>,
        values: MutableList<Pair<List<Key>, Value>>,
    ) {
        for ((key, value) in items) {
            val path = parent + key
            if (value is Item.ValueItem) {
                val innerVal = value.value
                if (innerVal is Value.InlineTableValue && innerVal.inlineTable.dotted) {
                    innerVal.inlineTable.appendValues(path, values)
                } else {
                    values.add(Pair(path, innerVal))
                }
            }
        }
    }

    public companion object {
        public fun new(): InlineTable = InlineTable()
    }
}
