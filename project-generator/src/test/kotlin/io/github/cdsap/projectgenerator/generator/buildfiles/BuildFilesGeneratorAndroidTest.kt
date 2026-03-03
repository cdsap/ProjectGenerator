package io.github.cdsap.projectgenerator.generator.buildfiles

import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.LATEST_GRADLE
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.writer.ProjectWriter
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProject
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.io.TempDir
import java.io.File

class BuildFilesGeneratorAndroidTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `generates build file for LIB type with correct plugin and dependencies`() {
        val node = ProjectGraph(
            id = "module_1_1",
            layer = 1,
            type = TypeProject.ANDROID_LIB,
            nodes = listOf(
                ProjectGraph(
                    id = "module_0_1", layer = 2, type = TypeProject.ANDROID_LIB, nodes = emptyList(), classes = 23
                )
            ),
            classes = 21
        )
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(LanguageAttributes(projectName = "${tempDir.path}/", extension = "gradle.kts")),
            Versions(),
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            ""
        )
        projectWriter.write()
        val buildFile = File("${tempDir.path}/${NameMappings.layerName(1)}/module_1_1/build.gradle.kts")
        val content = buildFile.readText()
        assertTrue(content.contains("id(\"awesome.androidlib.plugin\")"))
        assertTrue(content.contains("implementation(libs.compose.ui)"))
        assertTrue(content.contains("implementation(project(\":${NameMappings.layerName(2)}:${NameMappings.moduleName("module_0_1")}\"))"))
    }

    @Test
    fun `generates build file for APPLICATION type with correct plugin and dependencies`() {

        val node = ProjectGraph(
            id = "module_1_1",
            layer = 1,
            type = TypeProject.ANDROID_APP,
            nodes = listOf(
                ProjectGraph(
                    id = "module_0_1", layer = 2, type = TypeProject.ANDROID_LIB, nodes = emptyList(), classes = 23
                )
            ),
            classes = 21
        )
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(LanguageAttributes(projectName = "${tempDir.path}/", extension = "gradle.kts")),
            Versions(),
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            ""
        )
        projectWriter.write()
        val buildFile = File("${tempDir.path}/${NameMappings.layerName(1)}/module_1_1/build.gradle.kts")

        val content = buildFile.readText()
        assertTrue(content.contains("id(\"awesome.androidapp.plugin\")"))
        assertTrue(content.contains("implementation(libs.compose.ui)"))
        assertTrue(content.contains("implementation(project(\":${NameMappings.layerName(2)}:${NameMappings.moduleName("module_0_1")}\"))"))
        buildFile.delete()
    }

    @Test
    fun `generates kotlin multiplatform android lib build file with androidMain dependencies`() {
        val node = ProjectGraph(
            id = "module_1_1",
            layer = 1,
            type = TypeProject.ANDROID_LIB,
            nodes = listOf(
                ProjectGraph(
                    id = "module_0_1", layer = 2, type = TypeProject.ANDROID_LIB, nodes = emptyList(), classes = 23
                )
            ),
            classes = 21
        )
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(LanguageAttributes(projectName = "${tempDir.path}/kmp", extension = "gradle.kts")),
            Versions(android = Android(kotlinMultiplatformLibrary = true)),
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            true,
            GradleWrapper(Gradle.GRADLE_9_3_0),
            false,
            ""
        )
        projectWriter.write()

        val buildFile = File("${tempDir.path}/kmp/${NameMappings.layerName(1)}/module_1_1/build.gradle.kts")
        val content = buildFile.readText()

        assertTrue(content.contains("kotlin {"))
        assertTrue(content.contains("androidMain.dependencies {"))
        assertTrue(content.contains("androidHostTest.dependencies {"))
        assertTrue(content.contains("implementation(project(\":${NameMappings.layerName(2)}:${NameMappings.moduleName("module_0_1")}\"))"))
        assertTrue(content.contains("add(\"ksp\""))
    }
}
