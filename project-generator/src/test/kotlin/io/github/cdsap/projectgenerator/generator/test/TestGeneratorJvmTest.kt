package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.writer.ProjectWriter
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File

class TestGeneratorJvmTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `generates JVM test file for a module with expected content`() {
        val node = ProjectGraph(
            id = "module_1_1",
            layer = 1,
            type = TypeProject.LIB,
            nodes = emptyList(),
            classes = 1
        )
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(LanguageAttributes(projectName = tempDir.path + "/", extension = "gradle.kts")),
            Versions(),
            TypeProjectRequested.JVM,
            TypeOfStringResources.NORMAL,
            true, // generateUnitTest
            GradleWrapper(Gradle.GRADLE_8_14_3),
            false,
            ""
        )
        projectWriter.write()
        val testFile = File(tempDir, "${NameMappings.layerName(1)}/module_1_1/src/test/kotlin/com/awesomeapp/module_1_1/Usecase1_1Test.kt")
        assertTrue(testFile.exists(), "Test file should be generated")
        val content = testFile.readText()
        assertTrue(content.contains("class Usecase1_1Test"), "Test class should be present")
        assertTrue(content.contains("@Test"), "@Test annotation should be present")
    }
}
