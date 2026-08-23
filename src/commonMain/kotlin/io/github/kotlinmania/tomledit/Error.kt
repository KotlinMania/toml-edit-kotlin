// port-lint: source error.rs
package io.github.kotlinmania.tomledit

/**
 * A TOML parse error.
 */
public class TomlError(
    override val message: String,
    public val span: IntRange? = null,
    public val keys: List<String> = emptyList(),
) : Exception(message) {
    public fun message(): String = message

    public fun span(): IntRange? = span

    public companion object {
        public fun custom(
            message: String,
            span: IntRange? = null,
        ): TomlError = TomlError(message, span)
    }
}
