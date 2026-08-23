// port-lint: source repr.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML value encoded representation.
 */
public class Repr(
    public val rawValue: RawString,
) {
    public fun asRaw(): RawString = rawValue

    public fun span(): IntRange? = rawValue.span()

    public companion object {
        public fun newUnchecked(raw: RawString): Repr = Repr(raw)

        public fun fromString(raw: String): Repr = Repr(RawString.from(raw))
    }
}

/**
 * Prefix and suffix decor around TOML items.
 */
public class Decor(
    private var prefixValue: RawString? = null,
    private var suffixValue: RawString? = null,
) {
    public fun clear() {
        prefixValue = null
        suffixValue = null
    }

    public fun prefix(): RawString? = prefixValue

    public fun suffix(): RawString? = suffixValue

    public fun setPrefix(prefix: RawString) {
        this.prefixValue = prefix
    }

    public fun setSuffix(suffix: RawString) {
        this.suffixValue = suffix
    }

    public companion object {
        public fun new(
            prefix: RawString?,
            suffix: RawString?,
        ): Decor = Decor(prefix, suffix)
    }
}

/**
 * A scalar TOML value's logical value and its representation.
 */
public class Formatted<T>(
    public var value: T,
    public var repr: Repr? = null,
    public var decor: Decor = Decor(),
) {
    public fun value(): T = value

    public fun asRepr(): Repr? = repr

    public fun decor(): Decor = decor

    public fun decorMut(): Decor = decor

    public fun fmt() {
        repr = null
    }

    public fun span(): IntRange? = repr?.span()

    public companion object {
        public fun <T> new(value: T): Formatted<T> = Formatted(value)
    }
}
