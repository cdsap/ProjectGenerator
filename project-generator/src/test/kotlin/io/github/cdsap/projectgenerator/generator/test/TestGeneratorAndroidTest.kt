package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.LATEST_GRADLE
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.ProjectWriter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

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
            GradleWrapper(LATEST_GRADLE),
            false,
            ""
        )
        projectWriter.write()
        val testFile = File(tempDir, "${NameMappings.layerName(1)}/module_1_1/src/test/kotlin/com/awesomeapp/module_1_1/Viewmodel1_1Test.kt")
        assertTrue(testFile.exists(), "Test file should be generated")
        val content = testFile.readText()
        assertTrue(content.contains("class Viewmodel1_1Test"), "Test class should be present")
    }

    @Test
    fun `viewmodel test references its declared repository and repository dependencies`() {
        val moduleDefinition = ModuleClassDefinitionAndroid(
            moduleId = "module_23_1",
            layer = 23,
            moduleNumber = 23,
            classes = listOf(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.VIEWMODEL,
                    index = 1,
                    dependencies = mutableListOf(
                        ClassDependencyAndroid(ClassTypeAndroid.REPOSITORY, "module_12_1")
                    )
                )
            )
        )
        val classesDictionary = mutableMapOf(
            "module_12_1" to CopyOnWriteArrayList(
                listOf(
                    GenerateDictionaryAndroid("UnrelatedApi12_4", ClassTypeAndroid.API, 4, emptyList()),
                    GenerateDictionaryAndroid(
                        "Repository12_5",
                        ClassTypeAndroid.REPOSITORY,
                        5,
                        listOf(ClassDependencyAndroid(ClassTypeAndroid.MODEL, "module_8_1"))
                    )
                )
            ),
            "module_8_1" to CopyOnWriteArrayList(
                listOf(GenerateDictionaryAndroid("Model8_6", ClassTypeAndroid.MODEL, 6, emptyList()))
            )
        )

        TestGeneratorAndroidLegacy().generate(moduleDefinition, tempDir.path, classesDictionary)

        val testFile = File(
            tempDir,
            "${NameMappings.layerName(23)}/module_23_1/src/test/kotlin/com/awesomeapp/module_23_1/Viewmodel23_1Test.kt"
        )
        val content = testFile.readText()
        assertTrue(content.contains("private lateinit var repository0: Repository12_5"))
        assertTrue(content.contains("repository0 = Repository12_5(com.awesomeapp.module_8_1.Model8_6())"))
        assertTrue(content.contains("viewModel = Viewmodel23_1("))
    }
}
