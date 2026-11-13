package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class ValuesStringsTest {

    @Test
    fun `createStrings produces consistently indented xml for normal resources`() {
        val xml = ValuesStrings().createStrings("account", TypeOfStringResources.NORMAL)

        assertEquals(
            """
            |<?xml version="1.0" encoding="utf-8"?>
            |<resources>
            |    <string name="string_account_1">String 1 for module account</string>
            |    <string name="string_account_2">String 2 for module account</string>
            |    <string name="string_account_3">String 3 for module account</string>
            |    <string name="string_account_4">String 4 for module account</string>
            |    <string name="string_account_5">String 5 for module account</string>
            |    <string name="string_account_6">String 6 for module account</string>
            |    <string name="string_account_7">String 7 for module account</string>
            |    <string name="string_account_8">String 8 for module account</string>
            |    <string name="string_account_9">String 9 for module account</string>
            |    <string name="string_account_10">String 10 for module account</string>
            |    <string name="string_account_11">String 11 for module account</string>
            |    <string name="string_account_12">String 12 for module account</string>
            |    <string name="string_account_13">String 13 for module account</string>
            |    <string name="string_account_14">String 14 for module account</string>
            |    <string name="string_account_15">String 15 for module account</string>
            |    <string name="string_account_16">String 16 for module account</string>
            |    <string name="string_account_17">String 17 for module account</string>
            |    <string name="string_account_18">String 18 for module account</string>
            |    <string name="string_account_19">String 19 for module account</string>
            |    <string name="string_account_20">String 20 for module account</string>
            |    <string name="string_account_21">String 21 for module account</string>
            |    <string name="string_account_22">String 22 for module account</string>
            |    <string name="string_account_23">String 23 for module account</string>
            |    <string name="string_account_24">String 24 for module account</string>
            |    <string name="string_account_25">String 25 for module account</string>
            |</resources>
            """.trimMargin(),
            xml
        )
    }

    @Test
    fun `createStrings does not leave mixed indentation on large resources`() {
        val xml = ValuesStrings().createStrings("account", TypeOfStringResources.LARGE)
        val lines = xml.lines()

        assertEquals("<?xml version=\"1.0\" encoding=\"utf-8\"?>", lines.first())
        assertEquals("<resources>", lines[1])
        assertEquals("</resources>", lines.last())
        lines.drop(2).dropLast(1).forEach { line ->
            assertEquals(
                "    <string name=",
                line.take(17),
                "expected 4-space indented string entry, got: $line"
            )
        }
        assertFalse(xml.contains("\n            <resources>"))
        assertEquals(100, lines.count { it.contains("<string name=") })
    }
}
