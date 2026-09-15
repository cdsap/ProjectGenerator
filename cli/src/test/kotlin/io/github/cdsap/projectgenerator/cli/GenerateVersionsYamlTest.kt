package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class GenerateVersionsYamlTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `render includes key defaults and plugin sections`() {
        val versions = Versions()
        val gradle = Gradle.latest()
        val yaml = GenerateVersionsYaml().render(versions, gradle)

        assertTrue(yaml.contains("gradle: ${gradle.version}"))
        assertTrue(yaml.contains("develocity: ${versions.project.develocity}"))
        assertTrue(yaml.contains("develocityUrl: ${versions.project.develocityUrl}"))
        assertTrue(yaml.contains("jdk: ${versions.project.jdk}"))
        assertTrue(yaml.contains("di: ${versions.di}"))
        assertTrue(yaml.contains("kgp: ${versions.kotlin.kgp}"))
        assertTrue(yaml.contains("ksp: ${versions.kotlin.ksp}"))
        assertTrue(yaml.contains("processor: ${versions.kotlin.kotlinProcessor.processor}"))
        assertTrue(yaml.contains("agp: ${versions.android.agp}"))
        assertTrue(yaml.contains("composeBom: ${versions.android.composeBom}"))
        assertTrue(yaml.contains("junit4: ${versions.testing.junit4}"))
        assertTrue(yaml.contains("junit5: ${versions.testing.junit5}"))
        val settingsPlugin = versions.additionalSettingsPlugins.single()
        val rootPlugin = versions.additionalBuildGradleRootPlugins.single()
        assertTrue(yaml.contains("additionalSettingsPlugins:"))
        assertTrue(yaml.contains("id: ${settingsPlugin.id}"))
        assertTrue(yaml.contains("version: ${settingsPlugin.version}"))
        assertTrue(yaml.contains("additionalBuildGradleRootPlugins:"))
        assertTrue(yaml.contains("id: ${rootPlugin.id}"))
        assertTrue(yaml.contains("version: ${rootPlugin.version}"))
    }

    @Test
    fun `generate writes rendered yaml to explicit output file`() {
        val outputFile = File(tempDir.toFile(), "versions.yaml")
        val generator = GenerateVersionsYaml()

        generator.generate(outputFile)

        assertTrue(outputFile.exists())
        assertEquals(generator.render(), outputFile.readText())
    }
}
