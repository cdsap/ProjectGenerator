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
        assert(File("$tempDir/project_kts/graph.dot").exists())
        assert(File("$tempDir/project_groovy/graph.dot").exists())
    }

    @Test
    fun `projectGenerator configures NameMappings before writing project files`() {
        val layers = 2
        ProjectGenerator(
            modules = 6,
            shape = Shape.RECTANGLE,
            language = Language.KTS,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            layers = layers,
            layerNames = listOf("platform"),
            moduleNameParts = listOf("alpha", "beta"),
            projectRootPath = tempDir.toString(),
            projectName = "named_project"
        ).write()

        assert(NameMappings.layerName(0) == "platform")
        assert(NameMappings.layerName(1) == "layer_1")
        assert(NameMappings.layerName(layers) == "app")
        assert(NameMappings.moduleNames.values.contains("app"))
        assert(NameMappings.moduleNames.values.any { it == "alpha" || it == "beta" || it.contains("-") })
        assert(File("$tempDir/settings.gradle.kts").readText().contains(":app:app"))
    }
}
