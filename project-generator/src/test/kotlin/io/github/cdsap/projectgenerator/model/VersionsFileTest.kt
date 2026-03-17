package io.github.cdsap.projectgenerator.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VersionsFileTest {

    @Test
    fun `resolve uses empty plugin lists when plugin overrides are omitted`() {
        val versions = VersionsFile(project = Project(jdk = "21")).resolve()

        assertEquals("21", versions.project.jdk)
        assertTrue(versions.additionalSettingsPlugins.isEmpty())
        assertTrue(versions.additionalBuildGradleRootPlugins.isEmpty())
    }

    @Test
    fun `resolve keeps explicit plugin overrides`() {
        val versions = VersionsFile(
            additionalSettingsPlugins = listOf(
                AdditionalPlugin("com.fueledbycaffeine.spotlight", "1.4.1")
            ),
            additionalBuildGradleRootPlugins = listOf(
                AdditionalPlugin("com.autonomousapps.dependency-analysis", "2.19.0", apply = false)
            )
        ).resolve()

        assertEquals(
            listOf(AdditionalPlugin("com.fueledbycaffeine.spotlight", "1.4.1")),
            versions.additionalSettingsPlugins
        )
        assertEquals(
            listOf(AdditionalPlugin("com.autonomousapps.dependency-analysis", "2.19.0", apply = false)),
            versions.additionalBuildGradleRootPlugins
        )
    }

    @Test
    fun `gradle override is preserved in versions file`() {
        val versionsFile = VersionsFile(gradle = Gradle.GRADLE_9_3_1)

        assertEquals(Gradle.GRADLE_9_3_1, versionsFile.gradle)
    }
}
