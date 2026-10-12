// port-lint: source key.rs
package io.github.kotlinmania.tomledit

/**
 * Key representation in TOML documents.
 */
public class Key(
    public var key: String,
    public var repr: Repr? = null,
    public var leafDecor: Decor = Decor(),
    public var dottedDecor: Decor = Decor(),
) : Comparable<Key> {
    public fun get(): String = key

    public fun asRepr(): Repr? = repr

    public val span: Span?
        get() = repr?.span

    public fun withReprUnchecked(repr: Repr): Key {
        this.repr = repr
        return this
    }

    public fun withLeafDecor(decor: Decor): Key {
        this.leafDecor = decor
        return this
    }

    public fun withDottedDecor(decor: Decor): Key {
        this.dottedDecor = decor
        return this
    }

    public fun asMut(): KeyMut = KeyMut(this)

    public fun defaultRepr(): Repr = Repr.fromString(key)

    public fun displayRepr(): String = repr?.asRaw()?.asStr() ?: key

    public fun leafDecorMut(): Decor = leafDecor

    public fun dottedDecorMut(): Decor = dottedDecor

    public fun despan(input: String) {
        leafDecor.despan(input)
        dottedDecor.despan(input)
        repr?.despan(input)
    }

    public fun clone(): Key = Key(key, repr?.clone(), leafDecor.clone(), dottedDecor.clone())

    public fun fmt() {
        repr = null
        leafDecor.clear()
        dottedDecor.clear()
    }

    override fun compareTo(other: Key): Int = key.compareTo(other.key)

    override fun equals(other: Any?): Boolean =
        when (other) {
            is Key -> key == other.key
            is String -> key == other
            else -> false
        }

    override fun hashCode(): Int = key.hashCode()

    override fun toString(): String = key

    public companion object {
        public fun new(key: String): Key = Key(key)

        public fun parse(repr: String): List<Key> {
            val parts = repr.split('.').map { it.trim() }
            return parts.map { Key.new(it) }
        }
    }
}

/**
 * A mutable reference proxy to a [Key]'s formatting.
 */
public class KeyMut(
    private val key: Key,
) : Comparable<KeyMut> {
    public fun get(): String = key.get()

    public fun asRepr(): Repr? = key.asRepr()

    public fun defaultRepr(): Repr = key.defaultRepr()

    public fun displayRepr(): String = key.displayRepr()

    public fun leafDecorMut(): Decor = key.leafDecorMut()

    public fun dottedDecorMut(): Decor = key.dottedDecorMut()

    public fun leafDecor(): Decor = key.leafDecor

    public fun dottedDecor(): Decor = key.dottedDecor

    public fun fmt() {
        key.fmt()
    }

    public fun key(): Key = key

    override fun compareTo(other: KeyMut): Int = get().compareTo(other.get())

    override fun equals(other: Any?): Boolean =
        when (other) {
            is KeyMut -> get() == other.get()
            is Key -> get() == other.get()
            is String -> get() == other
            else -> false
        }

    override fun hashCode(): Int = get().hashCode()

    override fun toString(): String = get()
}
