// port-lint: tests error.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ErrorTest {
    @Test
    fun customError() {
        val err = TomlError.custom("invalid syntax", Span(0, 5))
        assertEquals("invalid syntax", err.message)
        assertEquals(Span(0, 5), err.span)
    }

    @Test
    fun emptySpan() {
        val err = TomlError.custom("unexpected eof")
        assertEquals("unexpected eof", err.message)
        assertNull(err.span)
    }

    @Test
    fun empty() {
        val input = byteArrayOf()
        val index = 0
        val position = translatePosition(input, index)
        assertEquals(Pair(0, 0), position)
    }

    @Test
    fun start() {
        val input = "Hello".encodeToByteArray()
        val index = 0
        val position = translatePosition(input, index)
        assertEquals(Pair(0, 0), position)
    }

    @Test
    fun end() {
        val input = "Hello".encodeToByteArray()
        val index = input.size - 1
        val position = translatePosition(input, index)
        assertEquals(Pair(0, input.size - 1), position)
    }

    @Test
    fun after() {
        val input = "Hello".encodeToByteArray()
        val index = input.size
        val position = translatePosition(input, index)
        assertEquals(Pair(0, input.size), position)
    }

    @Test
    fun firstLine() {
        val input = "Hello\nWorld\n".encodeToByteArray()
        val index = 2
        val position = translatePosition(input, index)
        assertEquals(Pair(0, 2), position)
    }

    @Test
    fun endOfLine() {
        val input = "Hello\nWorld\n".encodeToByteArray()
        val index = 5
        val position = translatePosition(input, index)
        assertEquals(Pair(0, 5), position)
    }

    @Test
    fun startOfSecondLine() {
        val input = "Hello\nWorld\n".encodeToByteArray()
        val index = 6
        val position = translatePosition(input, index)
        assertEquals(Pair(1, 0), position)
    }

    @Test
    fun secondLine() {
        val input = "Hello\nWorld\n".encodeToByteArray()
        val index = 8
        val position = translatePosition(input, index)
        assertEquals(Pair(1, 2), position)
    }
}
