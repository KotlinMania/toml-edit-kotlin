// port-lint: source visit_mut.rs
package io.github.kotlinmania.tomledit

/**
 * Document tree traversal to mutate a document tree in-place.
 */
public interface VisitMut {
    public fun visitDocumentMut(node: Document) {
        val table = node.asTableMut()
        visitTableMut(table)
    }

    public fun visitItemMut(node: Item) {
        when (node) {
            is Item.None -> {}
            is Item.ValueItem -> visitValueMut(node.value)
            is Item.TableItem -> visitTableMut(node.table)
            is Item.ArrayOfTablesItem -> visitArrayOfTablesMut(node.arrayOfTables)
        }
    }

    public fun visitTableMut(node: Table) {
        for ((key, item) in node.keyEntries()) {
            visitTableLikeKvMut(key.get(), item)
        }
    }

    public fun visitInlineTableMut(node: InlineTable) {
        for ((key, item) in node.keyEntries()) {
            visitTableLikeKvMut(key.get(), item)
        }
    }

    public fun visitTableLikeKvMut(key: String, node: Item) {
        visitItemMut(node)
    }

    public fun visitArrayMut(node: Array) {
        for (value in node.iter()) {
            visitValueMut(value)
        }
    }

    public fun visitArrayOfTablesMut(node: ArrayOfTables) {
        for (table in node.iter()) {
            visitTableMut(table)
        }
    }

    public fun visitValueMut(node: Value) {
        when (node) {
            is Value.StringValue -> visitStringMut(node.formatted)
            is Value.IntegerValue -> visitIntegerMut(node.formatted)
            is Value.FloatValue -> visitFloatMut(node.formatted)
            is Value.BooleanValue -> visitBooleanMut(node.formatted)
            is Value.DatetimeValue -> visitDatetimeMut(node.formatted)
            is Value.ArrayValue -> visitArrayMut(node.array)
            is Value.InlineTableValue -> visitInlineTableMut(node.inlineTable)
        }
    }

    public fun visitBooleanMut(node: FormattedBoolean) {}

    public fun visitDatetimeMut(node: FormattedString) {}

    public fun visitFloatMut(node: FormattedFloat) {}

    public fun visitIntegerMut(node: FormattedInteger) {}

    public fun visitStringMut(node: FormattedString) {}
}
