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
    private val explicit: String? = null,
    public val span: Span? = null,
) {
    public fun asStr(): String? = explicit

    public fun toStr(input: String? = null): String {
        if (explicit != null) return explicit
        if (span != null && input != null && span.start >= 0 && span.end <= input.length && span.start <= span.end) {
            return input.substring(span.start, span.end)
        }
        return ""
    }

    public fun toStrWithDefault(
        input: String?,
        default: String,
    ): String {
        if (explicit != null) return explicit
        if (span != null && input != null && span.start >= 0 && span.end <= input.length && span.start <= span.end) {
            return input.substring(span.start, span.end)
        }
        return default
    }

    public companion object {
        public val EMPTY: RawString = RawString("")

        public fun from(s: String): RawString = RawString(explicit = s)

        public fun withSpan(span: Span): RawString = RawString(span = span)

        public fun withRange(range: IntRange): RawString = RawString(span = Span.fromRange(range))
    }
}
