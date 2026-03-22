package io.github.cdsap.projectgenerator.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GradleTest {

    @Test
    fun `supported gradle list is capped to five entries`() {
        assertEquals(6, Gradle.supported().size)
    }

    @Test
    fun `latest gradle is the first supported entry`() {
        assertEquals(Gradle.supported().first(), Gradle.latest())
    }

    @Test
    fun `fromValue accepts version cli value and legacy enum name`() {
        val gradle = Gradle.supported()[1]

        assertEquals(gradle, Gradle.fromValue(gradle.version))
        assertEquals(gradle, Gradle.fromValue(gradle.cliValue))
        assertEquals(gradle, Gradle.fromValue(gradle.legacyEnumName))
    }

    @Test
    fun `supported gradle list keeps three minor lines for each targeted major`() {
        val grouped = Gradle.supported().groupBy { it.version.substringBefore('.') }

        assertEquals(setOf("8", "9"), grouped.keys)
        assertEquals(3, grouped.getValue("8").size)
        assertEquals(3, grouped.getValue("9").size)
    }
}
