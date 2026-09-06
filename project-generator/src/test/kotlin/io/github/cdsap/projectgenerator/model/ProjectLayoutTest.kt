package io.github.cdsap.projectgenerator.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProjectLayoutTest {

    @Test
    fun `default root path for kts nests project name and project_kts`() {
        val resolved = ProjectLayout.defaultRootPath(null, Language.KTS, "sample")

        assertEquals("projects_generated/sample/project_kts", resolved)
    }

    @Test
    fun `default root path for groovy nests project name and project_groovy`() {
        val resolved = ProjectLayout.defaultRootPath(null, Language.GROOVY, "sample")

        assertEquals("projects_generated/sample/project_groovy", resolved)
    }

    @Test
    fun `default root path for both languages nests project name only`() {
        val resolved = ProjectLayout.defaultRootPath(null, Language.BOTH, "sample")

        assertEquals("projects_generated/sample", resolved)
    }

    @Test
    fun `output dir is used directly for kts`() {
        val resolved = ProjectLayout.defaultRootPath(".", Language.KTS, "sample")

        assertEquals(".", resolved)
    }

    @Test
    fun `output dir is used directly for groovy`() {
        val resolved = ProjectLayout.defaultRootPath("/tmp/out", Language.GROOVY, "sample")

        assertEquals("/tmp/out", resolved)
    }

    @Test
    fun `output dir is used directly for both languages`() {
        val resolved = ProjectLayout.defaultRootPath("/tmp/out", Language.BOTH, "sample")

        assertEquals("/tmp/out", resolved)
    }

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
