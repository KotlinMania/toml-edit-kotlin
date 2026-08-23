// port-lint: source lib.rs
package io.github.kotlinmania.tomledit

/**
 * Top-level re-exports and utilities for toml-edit.
 */
public object TomlEditLib {
    public fun value(s: String): Item = Item.value(s)

    public fun value(i: Long): Item = Item.value(i)

    public fun value(d: Double): Item = Item.value(d)

    public fun value(b: Boolean): Item = Item.value(b)

    public fun table(): Item = Item.table()
}
