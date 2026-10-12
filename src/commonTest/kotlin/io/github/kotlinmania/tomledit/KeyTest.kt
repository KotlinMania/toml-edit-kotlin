// port-lint: tests key.rs
package io.github.kotlinmania.tomledit

import kotlin.test.Test
import kotlin.test.assertEquals

class KeyTest {
    @Test
    fun parseDotted() {
        val keys = Key.parse("a.b.c")
        assertEquals(3, keys.size)
        assertEquals("a", keys[0].get())
        assertEquals("b", keys[1].get())
        assertEquals("c", keys[2].get())
    }

    @Test
    fun stringRoundtrip() {
        val k = Key.new("hello")
        val parsed = Key.parse(k.toTomlString())
        assertEquals(1, parsed.size)
        assertEquals("hello", parsed[0].get())
    }

    @Test
    fun keyDecorAndBuilders() {
        val k =
            Key
                .new("foo")
                .withLeafDecor(Decor.new(RawString.from(" "), RawString.from(" ")))
                .withDottedDecor(Decor.new(RawString.from("  "), RawString.from("  ")))

        assertEquals("foo", k.get())
        assertEquals(" ", k.leafDecor.prefix()?.asStr())
        assertEquals(" ", k.leafDecor.suffix()?.asStr())
        assertEquals("  ", k.dottedDecor.prefix()?.asStr())
        assertEquals("  ", k.dottedDecor.suffix()?.asStr())

        k.leafDecorMut().setPrefix(RawString.from("\t"))
        assertEquals("\t", k.leafDecor.prefix()?.asStr())

        val cloned = k.clone()
        assertEquals(k, cloned)
        assertEquals(0, k.compareTo(cloned))

        k.fmt()
        assertEquals(null, k.asRepr())
        assertEquals(null, k.leafDecor.prefix())
        assertEquals(null, k.dottedDecor.prefix())
    }

    @Test
    fun keyMutProxy() {
        val k = Key.new("bar")
        val m = k.asMut()
        assertEquals("bar", m.get())
        assertEquals("bar", m.displayRepr())
        assertEquals(k, m.key())
        assertEquals(true, m.equals(Key.new("bar")))

        m.leafDecorMut().setPrefix(RawString.from("/* comment */ "))
        assertEquals("/* comment */ ", k.leafDecor.prefix()?.asStr())

        m.fmt()
        assertEquals(null, k.leafDecor.prefix())
    }

    @Test
    fun keyDespan() {
        val input = "   alpha.beta   "
        val k =
            Key
                .new("alpha")
                .withLeafDecor(Decor.new(RawString.withSpan(Span.of(0, 3)), RawString.EMPTY))
                .withReprUnchecked(Repr.newUnchecked(RawString.withSpan(Span.of(3, 8))))

        k.despan(input)
        assertEquals("   ", k.leafDecor.prefix()?.asStr())
        assertEquals("alpha", k.asRepr()?.asRaw()?.asStr())
    }
}
