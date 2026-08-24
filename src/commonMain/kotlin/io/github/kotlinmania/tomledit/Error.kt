// port-lint: source error.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML parse error.
 */
public class TomlError(
    override val message: String,
    public val span: Span? = null,
    public val keys: List<String> = emptyList(),
) : Exception(message) {
    public companion object {
        public fun custom(
            message: String,
            span: Span? = null,
        ): TomlError = TomlError(message, span)
    }
}
