// port-lint: source toml_edit/src/key.rs
package io.github.kotlinmania.tomledit

/**
 * Key representation in TOML documents.
 */
public class Key(
    public var key: String,
    public var repr: Repr? = null,
    public var leafDecor: Decor = Decor(),
    public var dottedDecor: Decor = Decor(),
) {
    public fun get(): String = key

    public fun asRepr(): Repr? = repr

    public val span: Span?
        get() = repr?.span

    public fun fmt() {
        repr = null
    }

    public companion object {
        public fun new(key: String): Key = Key(key)

        public fun parse(repr: String): List<Key> {
            val parts = repr.split('.').map { it.trim() }
            return parts.map { Key.new(it) }
        }
    }
}
