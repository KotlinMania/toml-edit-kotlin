// port-lint: source repr.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML value encoded representation.
 */
public class Repr(
    public val rawValue: RawString,
) {
    public fun asRaw(): RawString = rawValue

    public val span: Span?
        get() = rawValue.span

    public fun despan(input: String) {
        rawValue.despan(input)
    }

    public fun clone(): Repr = Repr(rawValue.clone())

    public fun encode(buf: StringBuilder, input: String) {
        rawValue.encode(buf, input)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Repr) return false
        return rawValue == other.rawValue
    }

    override fun hashCode(): Int = rawValue.hashCode()

    override fun toString(): String = rawValue.toString()

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

    public fun despan(input: String) {
        prefixValue?.despan(input)
        suffixValue?.despan(input)
    }

    public fun clone(): Decor = Decor(prefixValue?.clone(), suffixValue?.clone())

    public fun prefixEncode(buf: StringBuilder, input: String?, default: String) {
        val p = prefixValue
        if (p != null) {
            p.encodeWithDefault(buf, input, default)
        } else {
            buf.append(default)
        }
    }

    public fun suffixEncode(buf: StringBuilder, input: String?, default: String) {
        val s = suffixValue
        if (s != null) {
            s.encodeWithDefault(buf, input, default)
        } else {
            buf.append(default)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Decor) return false
        return prefixValue == other.prefixValue && suffixValue == other.suffixValue
    }

    override fun hashCode(): Int = (prefixValue?.hashCode() ?: 0) * 31 + (suffixValue?.hashCode() ?: 0)

    override fun toString(): String = "Decor(prefix=$prefixValue, suffix=$suffixValue)"

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
internal class Formatted<T>(
    var value: T,
    var repr: Repr? = null,
    var decor: Decor = Decor(),
) {
    fun fmt() {
        repr = null
    }

    val span: Span?
        get() = repr?.span

    companion object {
        fun <T> new(value: T): Formatted<T> = Formatted(value)
    }
}

public class FormattedString(
    public var value: String,
    public var repr: Repr? = null,
    public var decor: Decor = Decor(),
) {
    public fun fmt() {
        repr = null
    }

    public val span: Span?
        get() = repr?.span

    public companion object {
        public fun new(value: String): FormattedString = FormattedString(value)
    }
}

public class FormattedInteger(
    public var value: Long,
    public var repr: Repr? = null,
    public var decor: Decor = Decor(),
) {
    public fun fmt() {
        repr = null
    }

    public val span: Span?
        get() = repr?.span

    public companion object {
        public fun new(value: Long): FormattedInteger = FormattedInteger(value)
    }
}

public class FormattedFloat(
    public var value: Double,
    public var repr: Repr? = null,
    public var decor: Decor = Decor(),
) {
    public fun fmt() {
        repr = null
    }

    public val span: Span?
        get() = repr?.span

    public companion object {
        public fun new(value: Double): FormattedFloat = FormattedFloat(value)
    }
}

public class FormattedBoolean(
    public var value: Boolean,
    public var repr: Repr? = null,
    public var decor: Decor = Decor(),
) {
    public fun fmt() {
        repr = null
    }

    public val span: Span?
        get() = repr?.span

    public companion object {
        public fun new(value: Boolean): FormattedBoolean = FormattedBoolean(value)
    }
}
