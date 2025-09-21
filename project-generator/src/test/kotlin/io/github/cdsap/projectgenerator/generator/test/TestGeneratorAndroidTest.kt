package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.writer.ProjectWriter
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File

class TestGeneratorAndroidTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `generates Android test file for a module with expected content`() {
        val node = ProjectGraph(
            id = "module_1_1",
            layer = 1,
            type = TypeProject.ANDROID_LIB,
            nodes = emptyList(),
            classes = 1
        )
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(LanguageAttributes(projectName = tempDir.path + "/", extension = "gradle.kts")),
            Versions(),
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            true, // generateUnitTest
            GradleWrapper(Gradle.GRADLE_9_1_0),
            false,
            ""
        )
        projectWriter.write()
        val testFile = File(tempDir, "${NameMappings.layerName(1)}/module_1_1/src/test/kotlin/com/awesomeapp/module_1_1/Viewmodel1_1Test.kt")
        assertTrue(testFile.exists(), "Test file should be generated")
        val content = testFile.readText()
        assertTrue(content.contains("class Viewmodel1_1Test"), "Test class should be present")
    }
}
