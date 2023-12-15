package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.Shape
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
            path = tempDir.toString()
        ).write()
            assert(File("$tempDir/${it.name.lowercase()}_51/project_kts/build.gradle.kts").exists())
            assert(File("$tempDir/${it.name.lowercase()}_51/project_kts/settings.gradle.kts").exists())
            assert(File("$tempDir/${it.name.lowercase()}_51/project_kts/gradle.properties").exists())
        }
    }
}
