package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.File
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

class ProjectGeneratorTest {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun projectGeneratorDefaultCreates(shape: Shape) {

        ProjectGenerator(
            modules = 51,
            shape = shape,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.RANDOM, 10),
            layers = 5,
            projectRootPath = "$tempDir/awesome_project${shape.name.capitalize()}/project_kts",
            projectName = "awesome_project${shape.name.capitalize()}",
        ).write()
        assert(File("$tempDir/awesome_project${shape.name.capitalize()}/project_kts/build.gradle.kts").exists())
        assert(File("$tempDir/awesome_project${shape.name.capitalize()}/project_kts/settings.gradle.kts").exists())
        assert(
            File("$tempDir/awesome_project${shape.name.capitalize()}/project_kts/settings.gradle.kts").readText()
                .contains("awesome_project${shape.name.capitalize()}")
        )
        assert(File("$tempDir/awesome_project${shape.name.capitalize()}/project_kts/gradle.properties").exists())
    }

    @Test
    fun `projectGenerator writes directly to output path for single language when requested`() {
        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.KTS,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            layers = 2,
            projectRootPath = tempDir.toString(),
            projectName = "awesome_project"
        ).write()

        assert(File("$tempDir/build.gradle.kts").exists())
        assert(File("$tempDir/settings.gradle.kts").exists())
        assert(File("$tempDir/gradle.properties").exists())
        assert(!File("$tempDir/project_kts").exists())
    }

    @Test
    fun `projectGenerator keeps language subdirectories for both language output path`() {
        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.BOTH,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            layers = 2,
            projectRootPath = tempDir.toString(),
            projectName = "awesome_project"
        ).write()

        assert(File("$tempDir/project_kts/build.gradle.kts").exists())
        assert(File("$tempDir/project_groovy/build.gradle").exists())
    }
}
