// port-lint: source raw_string.rs
package io.github.kotlinmania.tomledit

/**
 * Opaque string storage for raw TOML.
 */
public class RawString(
    private val explicit: String? = null,
    private val span: IntRange? = null,
) {
    public fun asStr(): String? = explicit

    public fun span(): IntRange? = span

    public fun toStr(input: String): String {
        if (explicit != null) return explicit
        if (span != null) return input.substring(span.first, span.last + 1)
        return ""
    }

    public fun toStrWithDefault(
        input: String?,
        default: String,
    ): String {
        if (explicit != null) return explicit
        if (span != null && input != null) return input.substring(span.first, span.last + 1)
        return default
    }

    public companion object {
        public val EMPTY: RawString = RawString("")

        public fun from(s: String): RawString = RawString(explicit = s)

        public fun withSpan(span: IntRange): RawString = RawString(span = span)
    }
}
