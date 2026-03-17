package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.AdditionalPlugin
import io.github.cdsap.projectgenerator.model.Gradle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class VersionsParserTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `parses YAML without additionalSettingsPlugins and additionalBuildGradleRootPlugins as empty lists`() {
        val yaml = """
            project:
              jdk: "17"
            kotlin:
              kgp: "2.0.0"
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertTrue(versions.additionalSettingsPlugins.isEmpty())
        assertTrue(versions.additionalBuildGradleRootPlugins.isEmpty())
        assertEquals("17", versions.project.jdk)
        assertEquals("2.0.0", versions.kotlin.kgp)
    }

    @Test
    fun `parses YAML with additionalSettingsPlugins and additionalBuildGradleRootPlugins when present`() {
        val yaml = """
            project:
              jdk: "21"
            additionalSettingsPlugins:
              - id: com.fueledbycaffeine.spotlight
                version: 1.4.1
                apply: true
            additionalBuildGradleRootPlugins:
              - id: com.autonomousapps.dependency-analysis
                version: 2.19.0
                apply: true
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertEquals(1, versions.additionalSettingsPlugins.size)
        assertEquals(AdditionalPlugin("com.fueledbycaffeine.spotlight", "1.4.1", true), versions.additionalSettingsPlugins.first())

        assertEquals(1, versions.additionalBuildGradleRootPlugins.size)
        assertEquals(AdditionalPlugin("com.autonomousapps.dependency-analysis", "2.19.0", true), versions.additionalBuildGradleRootPlugins.first())
    }

    @Test
    fun `parses YAML with empty additionalSettingsPlugins and additionalBuildGradleRootPlugins arrays`() {
        val yaml = """
            project:
              jdk: "17"
            additionalSettingsPlugins: []
            additionalBuildGradleRootPlugins: []
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertTrue(versions.additionalSettingsPlugins.isEmpty())
        assertTrue(versions.additionalBuildGradleRootPlugins.isEmpty())
    }

    @Test
    fun `parses YAML with only one plugin key present uses empty for the absent one`() {
        val yaml = """
            project:
              jdk: "17"
            additionalSettingsPlugins:
              - id: com.fueledbycaffeine.spotlight
                version: 1.4.1
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertEquals(1, versions.additionalSettingsPlugins.size)
        assertTrue(versions.additionalBuildGradleRootPlugins.isEmpty())
    }

    @Test
    fun `parses YAML with additionalBuildGradleRootPlugins present and additionalSettingsPlugins absent`() {
        val yaml = """
            project:
              jdk: "17"
            additionalBuildGradleRootPlugins:
              - id: com.autonomousapps.dependency-analysis
                version: 2.19.0
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertTrue(versions.additionalSettingsPlugins.isEmpty())
        assertEquals(1, versions.additionalBuildGradleRootPlugins.size)
        assertEquals(AdditionalPlugin("com.autonomousapps.dependency-analysis", "2.19.0", true), versions.additionalBuildGradleRootPlugins.first())
    }

    @Test
    fun `parses YAML with null plugin lists as empty lists`() {
        val yaml = """
            project:
              jdk: "17"
            additionalSettingsPlugins:
            additionalBuildGradleRootPlugins:
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertTrue(versions.additionalSettingsPlugins.isEmpty())
        assertTrue(versions.additionalBuildGradleRootPlugins.isEmpty())
    }

    @Test
    fun `parses gradle from YAML case insensitively`() {
        val yaml = """
            gradle: gradle_9_3_1
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versionsFile = VersionsParser.fromFile(file)

        assertEquals(Gradle.GRADLE_9_3_1, versionsFile.gradle)
    }
}
