// port-lint: source encode.rs
package io.github.kotlinmania.tomledit

public fun toTomlString(document: Document): String {
    val buf = StringBuilder()
    encodeDocument(document, buf)
    return buf.toString()
}

public fun encodeDocument(document: Document, buf: StringBuilder) {
    val table = document.asTable() ?: return
    val tables = mutableListOf<TableEntry>()
    var lastPosition = 0
    visitNestedTables(table, mutableListOf(), false) { t, path, isArray ->
        val pos = t.position()
        if (pos != null) {
            lastPosition = pos
        }
        tables.add(TableEntry(lastPosition, t, path.toList(), isArray))
    }

    tables.sortBy { it.position }
    var firstTable = true
    for (entry in tables) {
        visitTable(buf, entry.table, entry.path, entry.isArray, firstTable)
        firstTable = false
    }

    val trailingStr = document.trailing.toStrWithDefault(document.raw, "")
    buf.append(trailingStr)
}

private data class TableEntry(
    val position: Int,
    val table: Table,
    val path: List<Key>,
    val isArray: Boolean,
)

private fun visitNestedTables(
    table: Table,
    path: MutableList<Key>,
    isArrayOfTables: Boolean,
    callback: (Table, List<Key>, Boolean) -> Unit,
) {
    if (!table.dotted) {
        callback(table, path, isArrayOfTables)
    }

    for ((key, value) in table.items) {
        when {
            value is Item.TableItem -> {
                path.add(key)
                visitNestedTables(value.table, path, false, callback)
                path.removeAt(path.size - 1)
            }
            value is Item.ArrayOfTablesItem -> {
                for (t in value.arrayOfTables.iter()) {
                    path.add(key)
                    visitNestedTables(t, path, true, callback)
                    path.removeAt(path.size - 1)
                }
            }
        }
    }
}

private fun visitTable(
    buf: StringBuilder,
    table: Table,
    path: List<Key>,
    isArrayOfTables: Boolean,
    firstTable: Boolean,
) {
    val children = table.getValues()
    val isVisibleStdTable = !(table.implicit && children.isEmpty())

    if (path.isEmpty()) {
        // Root node: no header
    } else if (isArrayOfTables) {
        if (!firstTable) {
            buf.append("\n")
        }
        buf.append("[[")
        encodeKeyPath(path, buf)
        buf.append("]]\n")
    } else if (isVisibleStdTable) {
        if (!firstTable) {
            buf.append("\n")
        }
        buf.append("[")
        encodeKeyPath(path, buf)
        buf.append("]\n")
    }

    for ((keyPath, value) in children) {
        encodeKeyPath(keyPath, buf)
        buf.append(" = ")
        encodeValue(value, buf)
        buf.append("\n")
    }
}

public fun encodeKeyPath(path: List<Key>, buf: StringBuilder) {
    for ((index, key) in path.withIndex()) {
        if (index > 0) {
            buf.append(".")
        }
        encodeKey(key, buf)
    }
}

public fun encodeKey(key: Key, buf: StringBuilder) {
    val repr = key.asRepr()
    if (repr != null) {
        buf.append(repr.asRaw().toStr())
    } else {
        val raw = key.get()
        if (raw.all { it.isLetterOrDigit() || it == '-' || it == '_' } && raw.isNotEmpty()) {
            buf.append(raw)
        } else {
            buf.append("\"")
            buf.append(escapeString(raw))
            buf.append("\"")
        }
    }
}

public fun encodeValue(value: Value, buf: StringBuilder) {
    when (value) {
        is Value.StringValue -> {
            val repr = value.formatted.repr
            if (repr != null) {
                buf.append(repr.asRaw().toStr())
            } else {
                buf.append("\"")
                buf.append(escapeString(value.formatted.value))
                buf.append("\"")
            }
        }
        is Value.IntegerValue -> {
            val repr = value.formatted.repr
            if (repr != null) {
                buf.append(repr.asRaw().toStr())
            } else {
                buf.append(value.formatted.value.toString())
            }
        }
        is Value.FloatValue -> {
            val repr = value.formatted.repr
            if (repr != null) {
                buf.append(repr.asRaw().toStr())
            } else {
                buf.append(value.formatted.value.toString())
            }
        }
        is Value.BooleanValue -> {
            val repr = value.formatted.repr
            if (repr != null) {
                buf.append(repr.asRaw().toStr())
            } else {
                buf.append(if (value.formatted.value) "true" else "false")
            }
        }
        is Value.DatetimeValue -> {
            buf.append(value.formatted.value)
        }
        is Value.ArrayValue -> {
            encodeArray(value.array, buf)
        }
        is Value.InlineTableValue -> {
            encodeInlineTable(value.inlineTable, buf)
        }
    }
}

public fun encodeArray(array: Array, buf: StringBuilder) {
    buf.append("[")
    val items = array.iter()
    for ((index, item) in items.withIndex()) {
        if (index > 0) {
            buf.append(", ")
        }
        encodeValue(item, buf)
    }
    if (array.trailingComma && items.isNotEmpty()) {
        buf.append(",")
    }
    buf.append("]")
}

public fun encodeInlineTable(inlineTable: InlineTable, buf: StringBuilder) {
    buf.append("{ ")
    val children = inlineTable.getValues()
    for ((index, pair) in children.withIndex()) {
        if (index > 0) {
            buf.append(", ")
        }
        encodeKeyPath(pair.first, buf)
        buf.append(" = ")
        encodeValue(pair.second, buf)
    }
    if (inlineTable.trailingComma && children.isNotEmpty()) {
        buf.append(",")
    }
    buf.append(" }")
}

private fun escapeString(s: String): String {
    val sb = StringBuilder()
    for (c in s) {
        when (c) {
            '\\' -> sb.append("\\\\")
            '"' -> sb.append("\\\"")
            '\b' -> sb.append("\\b")
            '\u000C' -> sb.append("\\f")
            '\n' -> sb.append("\\n")
            '\r' -> sb.append("\\r")
            '\t' -> sb.append("\\t")
            else -> {
                if (c.code < 0x20 || c.code == 0x7F) {
                    sb.append("\\u").append(c.code.toString(16).padStart(4, '0'))
                } else {
                    sb.append(c)
                }
            }
        }
    }
    return sb.toString()
}
