// port-lint: source toml_edit/src/index.rs
package io.github.kotlinmania.tomledit

/**
 * Trait for indexing into a TOML array or map.
 */
public interface Index {
    public fun index(item: Item): Item?

    public fun indexMut(item: Item): Item?
}

public class StringIndex(
    public val key: String,
) : Index {
    override fun index(item: Item): Item? = item.get(key)

    override fun indexMut(item: Item): Item? = item.get(key)
}

public class IntegerIndex(
    public val index: Int,
) : Index {
    override fun index(item: Item): Item? = item.get(index)

    override fun indexMut(item: Item): Item? = item.get(index)
}
