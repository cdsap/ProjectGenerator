package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.includedbuild.CompositeBuildBuildGradle
import io.github.cdsap.projectgenerator.generator.includedbuild.CompositeBuildSettingsGradle
import io.github.cdsap.projectgenerator.generator.plugins.android.CompositeBuildPluginAndroidApp
import io.github.cdsap.projectgenerator.generator.plugins.android.CompositeBuildPluginAndroidLib
import io.github.cdsap.projectgenerator.generator.plugins.jvm.CompositeBuildJvmLib
import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


class ConventionPluginWriterTest {

    @TempDir
    lateinit var tempDir: File

    private val versions = Versions()


    @Test
    fun `write should create convention plugins for a single Android project`() {
        val projectName = "androidAppModule"
        val language = LanguageAttributes(projectName = "${tempDir.path}/$projectName", extension = "gradle.kts")
        val writer = ConventionPluginWriter(
            languages = listOf(language),
            versions = versions,
            requested = TypeProjectRequested.ANDROID
        )

        writer.write()

        assertAndroidConventionFilesExist(language.projectName, versions)
    }

    @Test
    fun `write should create convention plugins for a single JVM project`() {
        val projectName = "jvmLibModule"
        val language = LanguageAttributes(projectName = "${tempDir.path}/$projectName", extension = "gradle.kts")
        val writer = ConventionPluginWriter(
            languages = listOf(language),
            versions = versions,
            requested = TypeProjectRequested.JVM
        )

        writer.write()

        assertJvmConventionFilesExist(language.projectName, versions)
    }

    @Test
    fun `write should create convention plugins for multiple Android projects`() {
        val projectNames = listOf("multiAndroid1", "multiAndroid2")
        val languages = projectNames.map {
            LanguageAttributes(projectName = "${tempDir.path}/$it", extension = "gradle.kts")
        }
        val writer = ConventionPluginWriter(
            languages = languages,
            versions = versions,
            requested = TypeProjectRequested.ANDROID
        )

        writer.write()

        languages.forEach { lang ->
            assertAndroidConventionFilesExist(lang.projectName, versions)
        }
    }

    @Test
    fun `write should create convention plugins for multiple JVM projects`() {
        val projectNames = listOf("multiJvm1", "multiJvm2")
        val languages = projectNames.map {
            LanguageAttributes(projectName = "${tempDir.path}/$it", extension = "gradle.kts")
        }
        val writer = ConventionPluginWriter(
            languages = languages,
            versions = versions,
            requested = TypeProjectRequested.JVM
        )

        writer.write()

        languages.forEach { lang ->
            assertJvmConventionFilesExist(lang.projectName, versions)
        }
    }

    @Test
    fun `write should do nothing if languages list is empty`() {
        val writer = ConventionPluginWriter(
            languages = emptyList(),
            versions = versions,
            requested = TypeProjectRequested.ANDROID
        )

        writer.write()

        assertEquals(0, tempDir.listFiles()?.size ?: 0, "Temp directory should be empty")
    }

    @Test
    fun `write should use android kotlin multiplatform library plugin when enabled`() {
        val projectName = "androidKmpLib"
        val language = LanguageAttributes(projectName = "${tempDir.path}/$projectName", extension = "gradle.kts")
        val versions = Versions(android = Android(kotlinMultiplatformLibrary = true))
        val writer = ConventionPluginWriter(
            languages = listOf(language),
            versions = versions,
            requested = TypeProjectRequested.ANDROID
        )

        writer.write()

        val libPluginFile = File("${language.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidLib.kt")
        assertTrue(libPluginFile.exists(), "Lib plugin missing for ${language.projectName}")
        assertTrue(
            libPluginFile.readText().contains("apply(\"com.android.kotlin.multiplatform.library\")"),
            "Expected KMP Android library plugin application in lib convention plugin"
        )
    }

    private fun assertAndroidConventionFilesExist(projectBasePath: String, versions: Versions) {
        val conventionSrcDir = File("$projectBasePath/build-logic/convention/src/main/kotlin/com/logic")
        assertTrue(conventionSrcDir.exists() && conventionSrcDir.isDirectory, "Convention src dir missing for $projectBasePath")

        val buildGradleFile = File("$projectBasePath/build-logic/convention/build.gradle.kts")
        assertTrue(buildGradleFile.exists(), "build.gradle.kts missing for $projectBasePath")
        assertEquals(
            CompositeBuildBuildGradle().get(versions, TypeProjectRequested.ANDROID, versions.di).trim(),
            buildGradleFile.readText().trim(),
            "build.gradle.kts content mismatch for $projectBasePath"
        )

        val settingsGradleFile = File("$projectBasePath/build-logic/settings.gradle.kts")
        assertTrue(settingsGradleFile.exists(), "settings.gradle.kts missing for $projectBasePath")
        assertEquals(
            CompositeBuildSettingsGradle().get().trim(),
            settingsGradleFile.readText().trim(),
            "settings.gradle.kts content mismatch for $projectBasePath"
        )

        val appPluginFile = File("$projectBasePath/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidApp.kt")
        assertTrue(appPluginFile.exists(), "App plugin missing for $projectBasePath")
        assertEquals(
            CompositeBuildPluginAndroidApp().get(versions, versions.di).trim(),
            appPluginFile.readText().trim(),
            "App plugin content mismatch for $projectBasePath"
        )

        val libPluginFile = File("$projectBasePath/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidLib.kt")
        assertTrue(libPluginFile.exists(), "Lib plugin missing for $projectBasePath")
        assertEquals(
            CompositeBuildPluginAndroidLib().get(versions, versions.di).trim(),
            libPluginFile.readText().trim(),
            "Lib plugin content mismatch for $projectBasePath"
        )
    }

    private fun assertJvmConventionFilesExist(projectBasePath: String, versions: Versions) {
        val conventionSrcDir = File("$projectBasePath/build-logic/convention/src/main/kotlin/com/logic")
        assertTrue(conventionSrcDir.exists() && conventionSrcDir.isDirectory, "Convention src dir missing for $projectBasePath")

        val buildGradleFile = File("$projectBasePath/build-logic/convention/build.gradle.kts")
        assertTrue(buildGradleFile.exists(), "build.gradle.kts missing for $projectBasePath")
        assertEquals(
            CompositeBuildBuildGradle().get(versions, TypeProjectRequested.JVM, versions.di).trim(),
            buildGradleFile.readText().trim(),
            "build.gradle.kts content mismatch for $projectBasePath"
        )

        val settingsGradleFile = File("$projectBasePath/build-logic/settings.gradle.kts")
        assertTrue(settingsGradleFile.exists(), "settings.gradle.kts missing for $projectBasePath")
        assertEquals(
            CompositeBuildSettingsGradle().get().trim(),
            settingsGradleFile.readText().trim(),
            "settings.gradle.kts content mismatch for $projectBasePath"
        )

        val jvmLibPluginFile = File("$projectBasePath/build-logic/convention/src/main/kotlin/com/logic/PluginJvmLib.kt")
        assertTrue(jvmLibPluginFile.exists(), "JVM Lib plugin missing for $projectBasePath")
        assertEquals(
            CompositeBuildJvmLib().get(versions).trim(),
            jvmLibPluginFile.readText().trim(),
            "JVM Lib plugin content mismatch for $projectBasePath"
        )
    }
}
