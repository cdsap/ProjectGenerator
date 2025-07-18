package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class ProjectGeneratorTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun projectGeneratorDefaultCreates() {
        listOf(
            Shape.RHOMBUS,
            Shape.TRIANGLE,
            Shape.INVERSE_TRIANGLE,
            Shape.FLAT,
            Shape.RECTANGLE,
            Shape.MIDDLE_BOTTLENECK
        ).forEach {
            ProjectGenerator(
                modules = 51,
                shape = it,
                classesPerModule = ClassesPerModule(ClassesPerModuleType.RANDOM, 8),
                layers = 5,
                path = tempDir.toString(),
                projectName = "awesome_project"
            ).write()
            assert(File("$tempDir/awesome_project/project_kts/build.gradle.kts").exists())
            assert(File("$tempDir/awesome_project/project_kts/settings.gradle.kts").exists())
            assert(
                File("$tempDir/awesome_project/project_kts/settings.gradle.kts").readText()
                    .contains("android${it.name.lowercase().replaceFirstChar { it.uppercase() }}51modules")
            )
            assert(File("$tempDir/awesome_project/project_kts/gradle.properties").exists())
        }
    }
}
