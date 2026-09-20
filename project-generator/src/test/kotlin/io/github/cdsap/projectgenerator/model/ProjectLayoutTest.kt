package io.github.cdsap.projectgenerator.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProjectLayoutTest {

    @Test
    fun `language attributes for kts use root path directly`() {
        val attributes = ProjectLayout.languageAttributes("/tmp/project", Language.KTS)

        assertEquals(listOf(LanguageAttributes("gradle.kts", "/tmp/project")), attributes)
    }

    @Test
    fun `language attributes for groovy use root path directly`() {
        val attributes = ProjectLayout.languageAttributes("/tmp/project", Language.GROOVY)

        assertEquals(listOf(LanguageAttributes("gradle", "/tmp/project")), attributes)
    }

    @Test
    fun `language attributes for both languages use language subdirectories`() {
        val attributes = ProjectLayout.languageAttributes("/tmp/project", Language.BOTH)

        assertEquals(
            listOf(
                LanguageAttributes("gradle", "/tmp/project/project_groovy"),
                LanguageAttributes("gradle.kts", "/tmp/project/project_kts")
            ),
            attributes
        )
    }
}
