// port-lint: source toml_edit/src/lib.rs
package io.github.kotlinmania.tomledit

/**
 * Top-level re-exports and utilities for toml-edit.
 */
public object TomlEditLib {
    public fun value(s: String): Item = Item.value(s)

    public fun value(i: Long): Item = Item.value(i)

    public fun value(d: Double): Item = Item.value(d)

    public fun value(b: Boolean): Item = Item.value(b)

    public fun value(v: Value): Item = Item.value(v)

    public fun table(): Item = Item.table()

    public fun arrayOfTables(): Item = Item.arrayOfTables()

    public fun document(): Document = Document.new()
}

public fun value(s: String): Item = Item.value(s)

public fun value(i: Long): Item = Item.value(i)

public fun value(d: Double): Item = Item.value(d)

public fun value(b: Boolean): Item = Item.value(b)

public fun value(v: Value): Item = Item.value(v)

public fun table(): Item = Item.table()
