package io.github.cdsap.projectgenerator.generator.buildfiles

import io.github.cdsap.projectgenerator.ProjectWriter
import io.github.cdsap.projectgenerator.model.Gradle
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
            GradleWrapper(Gradle.GRADLE_8_14_2),
            false
        )
        projectWriter.write()
        val buildFile = File("${tempDir.path}/layer_1/module_1_1/build.gradle.kts")
        val content = buildFile.readText()
        assertTrue(content.contains("id(\"awesome.androidlib.plugin\")"))
        assertTrue(content.contains("implementation(libs.compose.ui)"))
        assertTrue(content.contains("implementation(project(\":layer_2:module_0_1\"))"))
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
            GradleWrapper(Gradle.GRADLE_8_14_2),
            false
        )
        projectWriter.write()
        val buildFile = File("${tempDir.path}/layer_1/module_1_1/build.gradle.kts")
        val content = buildFile.readText()
        assertTrue(content.contains("id(\"awesome.androidapp.plugin\")"))
        assertTrue(content.contains("implementation(libs.compose.ui)"))
        assertTrue(content.contains("implementation(project(\":layer_2:module_0_1\"))"))
        buildFile.delete()
    }
}
