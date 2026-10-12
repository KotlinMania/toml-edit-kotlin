// port-lint: source raw_string.rs
package io.github.kotlinmania.tomledit

/**
 * Span of characters in the original input.
 */
public data class Span(
    public val start: Int,
    public val end: Int,
) {
    public fun toRange(): IntRange = start until end

    public companion object {
        public fun of(
            start: Int,
            end: Int,
        ): Span = Span(start, end)

        public fun fromRange(range: IntRange): Span = Span(range.first, range.last + 1)
    }
}

/**
 * Opaque string storage for raw TOML.
 */
public class RawString(
    private var explicit: String? = null,
    public var span: Span? = null,
) {
    public fun asStr(): String? = explicit

    public fun toStr(input: String? = null): String {
        if (explicit != null) return explicit!!
        val s = span
        if (s != null && input != null && s.start >= 0 && s.end <= input.length && s.start <= s.end) {
            return input.substring(s.start, s.end)
        }
        return ""
    }

    public fun toStrWithDefault(
        input: String?,
        default: String,
    ): String {
        if (explicit != null) return explicit!!
        val s = span
        if (s != null && input != null && s.start >= 0 && s.end <= input.length && s.start <= s.end) {
            return input.substring(s.start, s.end)
        }
        return default
    }

    public fun despan(input: String) {
        val s = span
        if (explicit == null && s != null) {
            explicit =
                if (s.start == s.end) {
                    ""
                } else if (s.start in 0..input.length && s.end in 0..input.length && s.start <= s.end) {
                    input.substring(s.start, s.end)
                } else {
                    ""
                }
            span = null
        }
    }

    public fun clone(): RawString = RawString(explicit, span)

    public fun encode(buf: StringBuilder, input: String) {
        val raw = toStr(input)
        for (part in raw.split('\r')) {
            buf.append(part)
        }
    }

    public fun encodeWithDefault(buf: StringBuilder, input: String?, default: String) {
        val raw = toStrWithDefault(input, default)
        for (part in raw.split('\r')) {
            buf.append(part)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RawString) return false
        return explicit == other.explicit && span == other.span
    }

    override fun hashCode(): Int = (explicit?.hashCode() ?: 0) * 31 + (span?.hashCode() ?: 0)

    override fun toString(): String = explicit ?: (span?.let { "span($it)" } ?: "")

    public companion object {
        public val EMPTY: RawString = RawString("")

        public fun from(s: String): RawString = RawString(explicit = s)

        public fun withSpan(span: Span): RawString = RawString(span = span)

        public fun withRange(range: IntRange): RawString = RawString(span = Span.fromRange(range))
    }
}
