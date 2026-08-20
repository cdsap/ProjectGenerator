package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.AdditionalPlugin
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Versions
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
    fun `rendered default versions YAML is parseable including plugin list sections`() {
        val versions = Versions()
        val gradle = Gradle.latest()
        val yaml = GenerateVersionsYaml().render(versions, gradle)
        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }

        val versionsFile = VersionsParser.fromFile(file)
        val parsed = versionsFile.resolve()

        assertEquals(gradle, versionsFile.gradle)
        assertEquals(versions.project.develocity, parsed.project.develocity)
        assertEquals(versions.project.develocityUrl, parsed.project.develocityUrl)
        assertEquals(versions.project.jdk, parsed.project.jdk)
        assertEquals(DependencyInjection.HILT, parsed.di)
        assertEquals(versions.kotlin.kgp, parsed.kotlin.kgp)
        assertEquals(versions.kotlin.ksp, parsed.kotlin.ksp)
        assertEquals(versions.kotlin.coroutines, parsed.kotlin.coroutines)
        assertEquals(versions.kotlin.kotlinProcessor.processor, parsed.kotlin.kotlinProcessor.processor)
        assertEquals(versions.android.agp, parsed.android.agp)
        assertEquals(versions.android.composeBom, parsed.android.composeBom)
        assertEquals(versions.testing.junit4, parsed.testing.junit4)
        assertEquals(versions.additionalSettingsPlugins, parsed.additionalSettingsPlugins)
        assertEquals(versions.additionalBuildGradleRootPlugins, parsed.additionalBuildGradleRootPlugins)
    }

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
    fun `parses YAML with empty develocityUrl as blank string`() {
        val yaml = """
            project:
              develocity: "4.1"
              develocityUrl:
              jdk: "23"
            kotlin:
              kgp: "2.2.10"
              ksp: "2.2.10-2.0.2"
              coroutines: "1.10.2"
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versions = VersionsParser.fromFile(file).resolve()

        assertEquals("4.1", versions.project.develocity)
        assertEquals("", versions.project.develocityUrl)
        assertEquals("23", versions.project.jdk)
    }

    @Test
    fun `parses gradle from YAML case insensitively`() {
        val yaml = """
            gradle: ${Gradle.supported()[1].version}
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versionsFile = VersionsParser.fromFile(file)

        assertEquals(Gradle.supported()[1], versionsFile.gradle)
    }

    @Test
    fun `parses legacy gradle enum name from YAML for backwards compatibility`() {
        val yaml = """
            gradle: ${Gradle.supported()[1].legacyEnumName}
        """.trimIndent()

        val file = File(tempDir.toFile(), "versions.yaml").apply { writeText(yaml) }
        val versionsFile = VersionsParser.fromFile(file)

        assertEquals(Gradle.supported()[1], versionsFile.gradle)
    }
}
