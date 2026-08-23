// port-lint: source value.rs
package io.github.kotlinmania.tomledit

/**
 * For Key/Value pairs in TOML documents.
 */
public sealed class Value {
    public abstract fun typeName(): String

    public open fun asStr(): String? = null

    public open fun isStr(): Boolean = asStr() != null

    public open fun asInteger(): Long? = null

    public open fun isInteger(): Boolean = asInteger() != null

    public open fun asFloat(): Double? = null

    public open fun isFloat(): Boolean = asFloat() != null

    public open fun asBoolean(): Boolean? = null

    public open fun isBoolean(): Boolean = asBoolean() != null

    public class StringValue(
        public val formatted: Formatted<String>,
    ) : Value() {
        override fun typeName(): String = "string"

        override fun asStr(): String = formatted.value()
    }

    public class IntegerValue(
        public val formatted: Formatted<Long>,
    ) : Value() {
        override fun typeName(): String = "integer"

        override fun asInteger(): Long = formatted.value()
    }

    public class FloatValue(
        public val formatted: Formatted<Double>,
    ) : Value() {
        override fun typeName(): String = "float"

        override fun asFloat(): Double = formatted.value()
    }

    public class BooleanValue(
        public val formatted: Formatted<Boolean>,
    ) : Value() {
        override fun typeName(): String = "boolean"

        override fun asBoolean(): Boolean = formatted.value()
    }

    public class ArrayValue(
        public val elements: MutableList<Value> = mutableListOf(),
    ) : Value() {
        override fun typeName(): String = "array"
    }

    public class InlineTableValue(
        public val entries: MutableMap<String, Value> = mutableMapOf(),
    ) : Value() {
        override fun typeName(): String = "inline table"
    }

    public companion object {
        public fun from(s: String): Value = StringValue(Formatted.new(s))

        public fun from(i: Long): Value = IntegerValue(Formatted.new(i))

        public fun from(d: Double): Value = FloatValue(Formatted.new(d))

        public fun from(b: Boolean): Value = BooleanValue(Formatted.new(b))
    }
}
