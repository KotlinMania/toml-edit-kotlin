// port-lint: source toml_edit/src/error.rs
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

public fun translatePosition(input: ByteArray, index: Int): Pair<Int, Int> {
    if (input.isEmpty()) {
        return Pair(0, index)
    }
    val safeIndex = minOf(index, input.size - 1)
    val columnOffset = index - safeIndex
    val curIndex = safeIndex

    var nlIndex = -1
    for (i in curIndex - 1 downTo 0) {
        if (input[i] == '\n'.code.toByte()) {
            nlIndex = i
            break
        }
    }
    val lineStart = if (nlIndex >= 0) nlIndex + 1 else 0
    var line = 0
    for (i in 0 until lineStart) {
        if (input[i] == '\n'.code.toByte()) {
            line++
        }
    }
    val slice = input.copyOfRange(lineStart, curIndex + 1)
    val sliceStr = slice.decodeToString()
    val column = (sliceStr.length - 1) + columnOffset
    return Pair(line, column)
}

