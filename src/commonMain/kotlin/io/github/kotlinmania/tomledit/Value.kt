// port-lint: source toml_edit/src/value.rs
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

    public open fun asArray(): Array? = null

    public open fun isArray(): Boolean = asArray() != null

    public open fun asInlineTable(): InlineTable? = null

    public open fun isInlineTable(): Boolean = asInlineTable() != null

    public open fun decor(): Decor =
        when (this) {
            is StringValue -> formatted.decor
            is IntegerValue -> formatted.decor
            is FloatValue -> formatted.decor
            is BooleanValue -> formatted.decor
            is DatetimeValue -> formatted.decor
            is ArrayValue -> array.decor
            is InlineTableValue -> inlineTable.decor
        }

    public open fun decorMut(): Decor = decor()

    public open fun fmt() {
        when (this) {
            is StringValue -> formatted.fmt()
            is IntegerValue -> formatted.fmt()
            is FloatValue -> formatted.fmt()
            is BooleanValue -> formatted.fmt()
            is DatetimeValue -> formatted.fmt()
            is ArrayValue -> array.fmt()
            is InlineTableValue -> inlineTable.fmt()
        }
    }

    public open val span: Span?
        get() =
            when (this) {
                is StringValue -> formatted.span
                is IntegerValue -> formatted.span
                is FloatValue -> formatted.span
                is BooleanValue -> formatted.span
                is DatetimeValue -> formatted.span
                is ArrayValue -> array.span
                is InlineTableValue -> inlineTable.span
            }

    public open fun despan(input: String) {
        when (this) {
            is StringValue -> formatted.repr = null
            is IntegerValue -> formatted.repr = null
            is FloatValue -> formatted.repr = null
            is BooleanValue -> formatted.repr = null
            is DatetimeValue -> formatted.repr = null
            is ArrayValue -> array.despan(input)
            is InlineTableValue -> inlineTable.despan(input)
        }
    }

    public class StringValue(
        public val formatted: FormattedString,
    ) : Value() {
        override fun typeName(): String = "string"

        override fun asStr(): String = formatted.value
    }

    public class IntegerValue(
        public val formatted: FormattedInteger,
    ) : Value() {
        override fun typeName(): String = "integer"

        override fun asInteger(): Long = formatted.value
    }

    public class FloatValue(
        public val formatted: FormattedFloat,
    ) : Value() {
        override fun typeName(): String = "float"

        override fun asFloat(): Double = formatted.value
    }

    public class BooleanValue(
        public val formatted: FormattedBoolean,
    ) : Value() {
        override fun typeName(): String = "boolean"

        override fun asBoolean(): Boolean = formatted.value
    }

    public class DatetimeValue(
        public val formatted: FormattedString,
    ) : Value() {
        override fun typeName(): String = "datetime"

        override fun asStr(): String = formatted.value
    }

    public class ArrayValue(
        public val array: Array,
    ) : Value() {
        override fun typeName(): String = "array"

        override fun asArray(): Array = array
    }

    public class InlineTableValue(
        public val inlineTable: InlineTable,
    ) : Value() {
        override fun typeName(): String = "inline table"

        override fun asInlineTable(): InlineTable = inlineTable
    }

    public companion object {
        public const val DEFAULT_VALUE_DECOR: String = " "
        public const val DEFAULT_TRAILING_VALUE_DECOR: String = " "
        public const val DEFAULT_LEADING_VALUE_DECOR: String = " "

        public fun from(s: String): Value = StringValue(FormattedString.new(s))

        public fun from(i: Long): Value = IntegerValue(FormattedInteger.new(i))

        public fun from(d: Double): Value = FloatValue(FormattedFloat.new(d))

        public fun from(b: Boolean): Value = BooleanValue(FormattedBoolean.new(b))

        public fun from(a: Array): Value = ArrayValue(a)

        public fun from(t: InlineTable): Value = InlineTableValue(t)
    }
}
