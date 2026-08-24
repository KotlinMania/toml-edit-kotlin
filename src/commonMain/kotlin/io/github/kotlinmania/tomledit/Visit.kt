// port-lint: source visit.rs
package io.github.kotlinmania.tomledit

/**
 * Document tree traversal to walk a document tree.
 */
public interface Visit {
    public fun visitDocument(node: Document) {
        val table = node.asTable()
        if (table != null) {
            visitTable(table)
        }
    }

    public fun visitItem(node: Item) {
        when (node) {
            is Item.None -> {}
            is Item.ValueItem -> visitValue(node.value)
            is Item.TableItem -> visitTable(node.table)
            is Item.ArrayOfTablesItem -> visitArrayOfTables(node.arrayOfTables)
        }
    }

    public fun visitTable(node: Table) {
        for ((key, item) in node.keyEntries()) {
            visitTableLikeKv(key.get(), item)
        }
    }

    public fun visitInlineTable(node: InlineTable) {
        for ((key, item) in node.keyEntries()) {
            visitTableLikeKv(key.get(), item)
        }
    }

    public fun visitTableLikeKv(key: String, node: Item) {
        visitItem(node)
    }

    public fun visitArray(node: Array) {
        for (value in node.iter()) {
            visitValue(value)
        }
    }

    public fun visitArrayOfTables(node: ArrayOfTables) {
        for (table in node.iter()) {
            visitTable(table)
        }
    }

    public fun visitValue(node: Value) {
        when (node) {
            is Value.StringValue -> visitString(node.formatted)
            is Value.IntegerValue -> visitInteger(node.formatted)
            is Value.FloatValue -> visitFloat(node.formatted)
            is Value.BooleanValue -> visitBoolean(node.formatted)
            is Value.DatetimeValue -> visitDatetime(node.formatted)
            is Value.ArrayValue -> visitArray(node.array)
            is Value.InlineTableValue -> visitInlineTable(node.inlineTable)
        }
    }

    public fun visitBoolean(node: FormattedBoolean) {}

    public fun visitDatetime(node: FormattedString) {}

    public fun visitFloat(node: FormattedFloat) {}

    public fun visitInteger(node: FormattedInteger) {}

    public fun visitString(node: FormattedString) {}
}
